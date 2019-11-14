package me.asu.retry.strategy.wait;

import me.asu.retry.RetryWaitContext;
import me.asu.retry.WaitTime;

/**
 * 固定时间间隔等待
 */
public class FixedRetryWait implements RetryWait {

    @Override
    public WaitTime waitTime(RetryWaitContext retryWaitContext) {
        return rangeCorrect(retryWaitContext.getValue(), retryWaitContext.getMin(), retryWaitContext.getMax());
    }

}
