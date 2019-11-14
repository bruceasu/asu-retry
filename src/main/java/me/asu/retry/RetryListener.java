package me.asu.retry;


/**
 * 重试监听接口
 *
 * 注意：实现类应该有无参构造函数
 */
public interface RetryListener
{

    /**
     * 执行重试监听
     * @param attempt 重试
     * @param <R> 泛型
     */
    <R> void listen(final RetryAttempt<R> attempt);

	RetryListener NO_RETRY_LISTENER = NoRetryListener.getInstance();

    /**
	 * 不进行任何监听动作
	 */
    class NoRetryListener implements RetryListener
	{

		static class Holder
		{
			static NoRetryListener singleton = new NoRetryListener();
		}

		public static NoRetryListener getInstance()
		{
			return Holder.singleton;
		}

		public NoRetryListener() {}

		@Override
		public <R> void listen(RetryAttempt<R> attempt) {}

	}
}
