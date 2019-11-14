package me.asu.retry.strategy.wait;

import me.asu.retry.RetryWaitContext;
import me.asu.retry.WaitTime;

/**
 * 无时间等待
 */
public class NoRetryWait implements RetryWait
{

	public static NoRetryWait getInstance()
	{
		return Holder.singleton;
	}

	@Override
	public WaitTime waitTime(RetryWaitContext retryWaitContext)
	{
		return rangeCorrect(0, retryWaitContext.getMin(), retryWaitContext.getMax());
	}

	static class Holder
	{
		static NoRetryWait singleton = new NoRetryWait();
	}

}
