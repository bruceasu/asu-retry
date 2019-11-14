package me.asu.retry;

import lombok.Data;
import me.asu.retry.strategy.wait.*;

/**
 * 重试等待类构造器
 * @param <R> 泛型
 */
@Data
public class RetryWaiter<R> {

    /**
     * 重试等待类的类型
     * 必须要有无参构造函数
     */
    private Class<? extends RetryWait> retryWait = NoRetryWait.class;

    /**
     * 默认的时间
     */
    private long value = RetryConst.VALUE_MILLS;

    /**
     * 最小值
     */
    private long min = RetryConst.MIN_MILLS;

    /**
     * 最小值
     */
    private long max = RetryConst.MAX_MILLS;

    /**
     * 变化因子
     * 1. 如果是 {@link ExponentialRetryWait} 则为 {@link RetryConst#MULTIPLY_FACTOR}
     * 2. 如果是 {@link IncreaseRetryWait} 则为 {@link RetryConst#INCREASE_MILLS_FACTOR}
     */
    private double factor = Double.MIN_VALUE;

    /**
     * 构造器私有化
     */
    private RetryWaiter(){}

    /**
     * 设置重试等待的对象类型
     * @param retryWait 重试等待类
     * @param <R> 泛型
     * @return 重试等待类
     */
    public static <R> RetryWaiter<R> retryWait(Class<? extends RetryWait> retryWait) {
        RetryWaiter<R> retryWaiter = new RetryWaiter<>();
        retryWaiter.setRetryWait(retryWait);

        //设置 factor 的默认值
        if(IncreaseRetryWait.class.equals(retryWait)) {
            retryWaiter.setFactor(RetryConst.INCREASE_MILLS_FACTOR);
        }
        if(ExponentialRetryWait.class.equals(retryWait)) {
            retryWaiter.setFactor(RetryConst.MULTIPLY_FACTOR);
        }
        return retryWaiter;
    }


    /**
     * 构建重试等待时间上下文
     * @return 重试等待时间上下文
     */
    public RetryWaitContext<R> getContext() {
        RetryWaitContext<R> waitContext = new RetryWaitContext<>();
        waitContext.setFactor(factor);
        waitContext.setMax(max);
        waitContext.setMin(min);
        waitContext.setRetryWait(retryWait);
        waitContext.setValue(value);
        return waitContext;
    }

}
