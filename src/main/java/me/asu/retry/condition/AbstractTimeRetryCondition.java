package me.asu.retry.condition;


import me.asu.retry.AttemptTime;
import me.asu.retry.RetryAttempt;

/**
 * 根据时间进行重试的抽象类
 */
public abstract class AbstractTimeRetryCondition implements RetryCondition {

    @Override
    public boolean condition(RetryAttempt retryAttempt) {
        return timeCondition(retryAttempt.getTime());
    }

    /**
     * 对消耗时间信息进行判断
     * 1. 用户可以判定是执行重试
     * 2. 比如任务执行的时间过长，过者任务执行的时间不在预期的时间范围内
     * @param attemptTime 时间信息
     * @return 对消耗时间信息进行判断
     */
    protected abstract boolean timeCondition(final AttemptTime attemptTime);

}
