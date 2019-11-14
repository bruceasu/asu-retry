package me.asu.retry.strategy.wait;

import me.asu.retry.RetryWaitContext;
import me.asu.retry.WaitTime;

/**
 * 重试时间等待
 */
public interface RetryWait
{

	/**
	 * 等待时间
	 *
	 * @param retryWaitContext 上下文信息
	 * @return 等待时间的结果信息
	 */
	WaitTime waitTime(final RetryWaitContext retryWaitContext);

	/**
	 * 修正范围
	 *
	 * @param timeMills 结果
	 * @param min 最小值
	 * @param max 最大值
	 * @return 修正范围
	 */
	default WaitTime rangeCorrect(final long timeMills, final long min, final long max)
	{
		long resultMills = timeMills;
		if (timeMills > max) {
			resultMills = max;
		}
		if (timeMills < min) {
			resultMills = min;
		}
		return new WaitTime(resultMills);
	}

}
