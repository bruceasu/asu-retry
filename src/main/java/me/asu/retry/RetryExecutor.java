package me.asu.retry;


import java.util.*;
import java.util.concurrent.Callable;
import lombok.Data;
import me.asu.retry.condition.RetryCondition;
import me.asu.retry.condition.RetryConditions;
import me.asu.retry.strategy.RetryStop;
import me.asu.retry.strategy.wait.NoRetryWait;

/**
 * 引导类入口
 *
 * @param <R> 泛型
 */
@Data
public class RetryExecutor<R> extends Retry<R>
{

	/**
	 * 待执行的方法
	 *
	 * @since 0.0.5
	 */
	private Callable<R> callable;

	/**
	 * 重试实现类
	 * 1. 不推荐用户自定义，但是暴露出来。
	 *
	 * @since 0.0.5
	 */
	private Retry<R> retry = Retry.getInstance();

	/**
	 * 执行重试的条件
	 * 1. 默认在遇到异常的时候进行重试
	 * 2. 支持多个条件，任意一个满足则满足。如果用户有更特殊的需求，应该自己定义。
	 */
	private RetryCondition condition = RetryConditions.hasExceptionCause();

	/**
	 * 阻塞的方式
	 * 1. 默认采用线程沉睡的方式
	 */
	private RetryBlock block = RetryBlock.getInstance();

	/**
	 * 停止的策略
	 * 1. 默认重试3次
	 * 2. 暂时不进行暴露自定义。因为实际生产中重试次数是最实用的一个策略。
	 */
	private RetryStop stop = new RetryStop(3);

	/**
	 * 监听器
	 * 1. 默认不进行任何操作
	 */
	private RetryListener listener = RetryListener.NO_RETRY_LISTENER;

	/**
	 * 恢复策略
	 * 1. 默认不进行任何操作
	 */
	private Recover recover = Recover.NO_RECOVER;

	/**
	 * 重试等待上下文
	 */
	private List<RetryWaitContext<R>>
			waitContexts = Collections.singletonList(RetryWaiter.<R>retryWait(NoRetryWait.class).getContext());

	/**
	 * 创建一个对象实例
	 *
	 * @param <R> 泛型
	 * @return 实例
	 */
	public static <R> RetryExecutor<R> create()
	{
		return new RetryExecutor<>();
	}

	/**
	 * 1. 设置待处理的方法类
	 * 2. 返回引导类 instance
	 *
	 * @param callable callable
	 */
	public void setCallable(final Callable<R> callable)
	{
		Objects.requireNonNull(callable);

		this.callable = callable;
	}


	/**
	 * 重试生效条件
	 *
	 * @param condition 生效条件
	 */
	public void setCondition(RetryCondition condition)
	{
		Objects.requireNonNull(condition);
		this.condition = condition;
	}


	/**
	 * 重试等待上下文
	 *
	 * @param retryWaitContexts 重试等待上下文数组
	 */
	public void setRetryWaitContext(RetryWaitContext<R>... retryWaitContexts)
	{
		Objects.requireNonNull(retryWaitContexts);
		this.waitContexts = Arrays.asList(retryWaitContexts);
	}

	/**
	 * 最大尝试次数
	 *
	 * @param maxAttempt 最大尝试次数
	 */
	public void setMaxAttempt(final int maxAttempt)
	{
		this.stop = new RetryStop(maxAttempt);
	}

	/**
	 * 设置阻塞策略
	 *
	 * @param block 阻塞策略
	 */
	private void setBslock(RetryBlock block)
	{
		Objects.requireNonNull(block);
		this.block = block;
	}

	/**
	 * 设置停止策略
	 *
	 * @param stop 停止策略
	 * @return this
	 */
	private void setStop(RetryStop stop)
	{
		Objects.requireNonNull(stop);

		this.stop = stop;
	}

	/**
	 * 设置监听
	 *
	 * @param listener 监听
	 */
	public void setListen(RetryListener listener)
	{
		Objects.requireNonNull(listener);

		this.listener = listener;
	}

	/**
	 * 设置恢复策略
	 *
	 * @param recover 恢复策略
	 */
	public void setRecover(Recover recover)
	{
		Objects.requireNonNull(recover);

		this.recover = recover;
	}

	/**
	 * 构建重试上下文
	 *
	 * @return 重试上下文
	 */
	public RetryContext<R> getContext()
	{
		RetryContext<R> context = new RetryContext<>();
		context.setCallable(callable);
		context.setWaitContext(waitContexts);
		context.setBlock(block);
		context.setStop(stop);
		context.setCondition(condition);
		context.setListener(listener);
		context.setRecover(recover);
		context.setRetry(retry);
		return context;
	}

	/**
	 * 重试执行
	 *
	 * @return 执行的结果
	 */
	public R retryCall()
	{
		// 初始化
		RetryContext<R> context = getContext();
		// 调用执行结果
		return getContext().getRetry().retryCall(context);
	}

	/**
	 * 重试执行
	 *
	 * @param context 执行上下文
	 * @return 执行的结果
	 */
	@Override
	public R retryCall(final RetryContext<R> context)
	{
		// 调用执行结果
		return getContext().getRetry().retryCall(context);
	}

}
