package me.asu.retry.annotation.handler;


import me.asu.retry.RetryWaitContext;
import me.asu.retry.RetryWaiter;
import me.asu.retry.annotation.annotation.RetryWait;

/**
 * 重试等待处理器
 */
public class RetryWaitAbleHandler<R>
{

	public RetryWaitContext<R> build(RetryWait retryWait)
	{
		RetryWaiter<R> waiter = RetryWaiter.retryWait(retryWait.retryWait());
		waiter.setMin(retryWait.min());
		waiter.setMax(retryWait.max());
		waiter.setFactor(retryWait.factor());
		waiter.setValue(retryWait.value());
		return waiter.getContext();
	}

}
