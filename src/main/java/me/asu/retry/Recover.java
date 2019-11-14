package me.asu.retry;


/**
 * 恢复现场接口
 * 注意：实现类应该有无参构造函数
 */
public interface Recover
{

    /**
     * 执行恢复
     * @param retryAttempt 重试信息
     * @param <R> 泛型
     */
    <R> void recover(final RetryAttempt<R> retryAttempt);

    Recover NO_RECOVER = NoRecover.getInstance();

    /**
	 * 不指定任何动作
	 */
    class NoRecover implements Recover {
        public NoRecover() {}

		static class Holder
		{
			static NoRecover singleton = new NoRecover();
		}

		public static NoRecover getInstance()
		{
			return Holder.singleton;
		}

		@Override
		public <R> void recover(RetryAttempt<R> retryAttempt) {

		}
	}
}
