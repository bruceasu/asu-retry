package me.asu.retry;

/**
 * 线程沉睡的阻塞方式
 */
public class RetryBlock
{

	/**
	 * 获取单例
	 *
	 * @return 获取单例
	 */
	public static RetryBlock getInstance()
	{
		return Holder.singleton;
	}

	public void block(WaitTime waitTime)
	{
		try {
			waitTime.getUnit().sleep(waitTime.getTime());
		} catch (InterruptedException e) {
			//restore status
			Thread.currentThread().interrupt();
			throw new RetryException(e);
		}
	}

	static class Holder
	{

		static final RetryBlock singleton = new RetryBlock();
	}

}
