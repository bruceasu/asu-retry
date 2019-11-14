package me.asu.retry.strategy;


import me.asu.retry.RetryAttempt;

/**
 * 最大尝试次数终止策略
 */
public class RetryStop
{
	/**
	 * 最大重试次数
	 */
	private final int maxAttempt;

	public RetryStop(final int maxAttempt)
	{
		if (maxAttempt < 1) { this.maxAttempt = 1; } else { this.maxAttempt = maxAttempt; }
	}

	public boolean stop(RetryAttempt attempt)
	{
		return attempt.getAttempt() >= maxAttempt;
	}

}
