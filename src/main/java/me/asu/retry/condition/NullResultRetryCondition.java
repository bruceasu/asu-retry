package me.asu.retry.condition;

/**
 * 空结果重试条件
 */
public class NullResultRetryCondition<R> extends AbstractResultRetryCondition<R> {
    static class Holder {
        static NullResultRetryCondition singleton = new NullResultRetryCondition();
    }

    public static NullResultRetryCondition getInstance() {
        return Holder.singleton;
    }

    @Override
    protected boolean resultCondition(R result) {
        return result == null;
    }

}
