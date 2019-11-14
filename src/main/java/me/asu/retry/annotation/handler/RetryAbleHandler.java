package me.asu.retry.annotation.handler;

import static me.asu.retry.ClassUtils.newInstance;

import java.util.*;
import java.util.concurrent.Callable;
import me.asu.retry.*;
import me.asu.retry.annotation.annotation.Retry;
import me.asu.retry.annotation.annotation.RetryWait;
import me.asu.retry.strategy.wait.NoRetryWait;

/**
 * 重试处理器
 */
public class RetryAbleHandler<R>
{

	@SuppressWarnings("unchecked")
	public RetryContext<R> build(final Retry annotation,
								 final Callable<R> callable)
	{
		RetryExecutor<R> executor = RetryExecutor.create();
		executor.setCallable(callable);
		executor.setRetry(newInstance(annotation.retry()));
		executor.setCondition(newInstance(annotation.condition()));
		executor.setMaxAttempt(annotation.maxAttempt());
		Recover recover = annotation.recover() == Recover.NoRecover.class ? Recover.NO_RECOVER
																		  : newInstance(annotation.recover());
		executor.setRecover(recover);
		RetryListener listener =
				annotation.listener() == RetryListener.NoRetryListener.class ? RetryListener.NO_RETRY_LISTENER
																			 : newInstance(annotation.listener());
		executor.setListener(listener);
		executor.setRetryWaitContext(buildRetryWaitContext(annotation).toArray(new RetryWaitContext[0]));
		return executor.getContext();

	}

	/**
	 * 构建重试等待上下文
	 *
	 * @param retry 重试信息
	 * @return 上下文列表
	 */
	@SuppressWarnings("unchecked")
	private List<RetryWaitContext<R>> buildRetryWaitContext(final Retry retry)
	{
		List<RetryWaitContext<R>> resultList =
				Collections.singletonList(RetryWaiter.<R>retryWait(NoRetryWait.class).getContext());
		if (retry == null) {
			return resultList;
		}

		RetryWait[] retryWaits = retry.waits();
		if (retryWaits.length == 0) {
			return resultList;
		}

		resultList = new ArrayList<>();
		final RetryWaitAbleHandler<R> waitAbleHandler = newInstance(RetryWaitAbleHandler.class);
		for (RetryWait retryWait : retryWaits) {
			RetryWaitContext<R> context = waitAbleHandler.build(retryWait);
			resultList.add(context);
		}

		return resultList;
	}

}
