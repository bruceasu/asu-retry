package me.asu.retry.condition;


import me.asu.retry.RetryAttempt;

/**
 * 根据结果进行重试的抽象类
 */
public class CauseRetryCondition implements RetryCondition {
    static class Holder {
        static CauseRetryCondition singleton = new CauseRetryCondition();
    }

    public static CauseRetryCondition getInstance() {
        return Holder.singleton;
    }

    @Override
    public boolean condition(RetryAttempt retryAttempt) {
        return hasException(retryAttempt.getCause());
    }

    /**
     * 判断是否有异常信息
     * 1. 有，返回 true
     * 2. 无，返回 false
     * @param throwable 异常信息
     * @return 是否有异常信息
     */
    protected boolean hasException(final Throwable throwable) {
        return throwable != null;
    }

}
