package me.asu.retry.condition;

/**
 * 非空结果重试条件
 */
public class NotNullResultRetryCondition<R> extends AbstractResultRetryCondition<R> {
    static class Holder {
        static NotNullResultRetryCondition singleton = new NotNullResultRetryCondition();
    }

    public static NotNullResultRetryCondition getInstance() {
        return Holder.singleton;
    }

    @Override
    protected boolean resultCondition(R result) {
        return result != null;
    }
}
