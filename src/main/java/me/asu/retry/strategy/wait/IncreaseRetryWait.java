package me.asu.retry.strategy.wait;

import me.asu.retry.RetryWaitContext;
import me.asu.retry.WaitTime;

/**
 * 递增策略
 */
public class IncreaseRetryWait implements RetryWait {
    @Override
    public WaitTime waitTime(RetryWaitContext retryWaitContext) {
        final int previousAttempt = retryWaitContext.getAttempt()-1;
        long result = Math.round(retryWaitContext.getValue() + previousAttempt*retryWaitContext.getFactor());

        return rangeCorrect(result, retryWaitContext.getMin(), retryWaitContext.getMax());
    }

}
