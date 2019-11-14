package me.asu.retry;

import static me.asu.retry.ClassUtils.newInstance;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import me.asu.retry.condition.RetryCondition;
import me.asu.retry.strategy.RetryStop;
import me.asu.retry.strategy.wait.RetryWait;

/**
 * 默认的重试实现
 *
 * @param <R> 泛型
 */
@Slf4j
public class Retry<R>
{
	public static Retry getInstance()
	{
		return Holder.singleton;
	}

	public R retryCall(RetryContext<R> context)
	{
		List<RetryAttempt<R>> history = new ArrayList<>();

		//1. 执行方法
		int               attempts     = 1;
		final Callable<R> callable     = context.getCallable();
		RetryAttempt<R>   retryAttempt = execute(callable, attempts, history);

		//2. 是否进行重试
		//2.1 触发执行的 condition
		//2.2 不触发 stop 策略
		final List<RetryWaitContext<R>> waitContextList = context.getWaitContext();
		final RetryCondition<R>           retryCondition  = context.getCondition();
		final RetryStop                 retryStop       = context.getStop();
		final RetryBlock                retryBlock      = context.getBlock();
		final RetryListener             retryListen     = context.getListener();

		while (retryCondition.condition(retryAttempt) && !retryStop.stop(retryAttempt)) {
			// 线程阻塞
			WaitTime waitTime = calcWaitTime(waitContextList, retryAttempt);
			retryBlock.block(waitTime);

			// 每一次执行会更新 executeResult
			attempts++;
			history.add(retryAttempt);
			retryAttempt = this.execute(callable, attempts, history);

			// 触发 listener
			retryListen.listen(retryAttempt);
		}

		// 如果仍然满足触发条件。但是已经满足停止策略
		// 触发 recover
		if (retryCondition.condition(retryAttempt) && retryStop.stop(retryAttempt)) {
			final Recover recover = context.getRecover();
			if (recover != null) {
				recover.recover(retryAttempt);
			}
		}

		// 如果最后一次有异常，直接抛出异常
		final Throwable throwable = retryAttempt.getCause();
		if (throwable != null) {
			//1. 运行时异常，则直接抛出
			//2. 非运行时异常，则包装成为 RetryException
			if (throwable instanceof RuntimeException) {
				throw (RuntimeException) throwable;
			}
			throw new RetryException(retryAttempt.getCause());
		}
		// 返回最后一次尝试的结果
		return retryAttempt.getResult();
	}

	/**
	 * 构建等待时间
	 *
	 * @param waitContextList 等待上下文列表
	 * @param retryAttempt 重试信息
	 * @return 等待时间毫秒
	 */
	private WaitTime calcWaitTime(final List<RetryWaitContext<R>> waitContextList,
								  final RetryAttempt<R> retryAttempt)
	{
		long totalTimeMills = 0;
		for (RetryWaitContext context : waitContextList) {
			RetryWait retryWait =
					(RetryWait) newInstance(context.getRetryWait());
			final RetryWaitContext retryWaitContext = buildRetryWaitContext(context, retryAttempt);
			WaitTime               waitTime         = retryWait.waitTime(retryWaitContext);
			totalTimeMills += waitTime.getUnit().convert(waitTime.getTime(), TimeUnit.MILLISECONDS);
		}
		return new WaitTime(totalTimeMills);
	}

	/**
	 * 构建重试等待上下文
	 *
	 * @param waitContext 等待上下文
	 * @param retryAttempt 重试信息
	 * @return 构建后的等待信息
	 */
	private RetryWaitContext buildRetryWaitContext(RetryWaitContext waitContext,
												   final RetryAttempt<R> retryAttempt)
	{
		RetryWaitContext<R> context = (RetryWaitContext<R>) waitContext;
		context.setAttempt(retryAttempt.getAttempt());
		context.setResult(retryAttempt.getResult());
		context.setHistory(retryAttempt.getHistory());
		context.setCause(retryAttempt.getCause());
		context.setTime(retryAttempt.getTime());
		return context;
	}

	/**
	 * 执行 callable 方法
	 *
	 * @param callable 待执行的方法
	 * @param attempts 重试次数
	 * @param history 历史记录
	 * @return 相关的额执行信息
	 */
	private RetryAttempt<R> execute(final Callable<R> callable,
									final int attempts,
									final List<RetryAttempt<R>> history)
	{
		final Date startTime = new Date();

		RetryAttempt<R> retryAttempt = new RetryAttempt<>();
		Throwable       throwable    = null;
		R               result       = null;
		try {
			result = callable.call();
		} catch (Exception e) {
			throwable = getActualThrowable(e);
		}
		final Date  endTime         = new Date();
		final long  costTimeInMills = startTime.getTime() - endTime.getTime();
		AttemptTime attemptTime     = new AttemptTime();
		attemptTime.setStartTime(startTime);
		attemptTime.setEndTime(endTime);
		attemptTime.setCostTimeInMills(costTimeInMills);

		retryAttempt.setAttempt(attempts);
		retryAttempt.setTime(attemptTime);
		retryAttempt.setCause(throwable);
		retryAttempt.setResult(result);
		retryAttempt.setHistory(history);

		return retryAttempt;
	}


	private Throwable getActualThrowable(Exception e)
	{
		Throwable cause     = e;
		Throwable nestCause = e;
		while (nestCause != null) {
			cause = nestCause;
			nestCause = nestCause.getCause();
		}
		return cause;
	}

	static class Holder
	{

		static Retry singleton = new Retry();
	}

}
