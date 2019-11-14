package me.asu.retry.strategy.wait;

import me.asu.retry.RetryWaitContext;
import me.asu.retry.WaitTime;

/**
 * 指数增长的等待策略
 * 1. 如果因数大于 1 越来越快。
 * 2. 如果因数等于1 保持不变
 * 3. 如果因数大于0，且小于1 。越来越慢
 *
 * 斐波那契数列就是一种乘数接近于：1.618 的黄金递增。
 * 指数等待函数
 */
public class ExponentialRetryWait implements RetryWait {

    @Override
    public WaitTime waitTime(RetryWaitContext retryWaitContext) {
        final int previousAttempt = retryWaitContext.getAttempt()-1;
        double exp = Math.pow(retryWaitContext.getFactor(), previousAttempt);
        long result = Math.round(retryWaitContext.getValue() * exp);

        return rangeCorrect(result, retryWaitContext.getMin(), retryWaitContext.getMax());
    }

}
