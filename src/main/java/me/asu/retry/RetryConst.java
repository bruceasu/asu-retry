package me.asu.retry;

import me.asu.retry.strategy.wait.NoRetryWait;

/**
 * 重试等待时间常量类
 */
public final class RetryConst
{

    private RetryConst(){}

    /**
     * 默认等待类
     */
    public static final Class RETRY_WAIT_CLASS = NoRetryWait.class;

    /**
     * 默认基础值
     * 1. 1s
     */
    public static final long VALUE_MILLS = 1000L;

    /**
     * 最小等待时间
     * 30min
     */
    public static final long MIN_MILLS = 0L;

    /**
     * 最大等待时间
     * 30min
     */
    public static final long MAX_MILLS = 30 * 60 * 1000L;

    /**
     * 增加的毫秒数因数
     * 1. 默认为 2S
     */
    public static final double INCREASE_MILLS_FACTOR = 2000;

    /**
     * 因数
     * 1. 默认为黄金分割比
     */
    public static final double MULTIPLY_FACTOR = 1.618;

}
