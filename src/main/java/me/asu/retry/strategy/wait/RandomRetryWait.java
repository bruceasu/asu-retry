package me.asu.retry.strategy.wait;

import java.util.concurrent.ThreadLocalRandom;
import me.asu.retry.RetryWaitContext;
import me.asu.retry.WaitTime;

/**
 * 随机等待策略
 */
public class RandomRetryWait implements RetryWait {

    @Override
    public WaitTime waitTime(RetryWaitContext retryWaitContext) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long time = random.nextLong(retryWaitContext.getMin(), retryWaitContext.getMax()-retryWaitContext.getMin());
        return rangeCorrect(time, retryWaitContext.getMin(), retryWaitContext.getMax());
    }

}
