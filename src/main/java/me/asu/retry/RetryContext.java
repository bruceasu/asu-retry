package me.asu.retry;

import java.util.List;
import java.util.concurrent.Callable;
import lombok.Data;
import me.asu.retry.condition.RetryCondition;
import me.asu.retry.strategy.RetryStop;

/**
 * 重试上下文
 * @param <R> 泛型
 */
@Data
public class RetryContext<R> {

    /**
     * 重试实现类
     * @since 0.0.5
     */
    private Retry<R> retry;

    /**
     * 执行的条件
     */
    private RetryCondition<R> condition;

    /**
     * 重试等待上下文
     */
    private List<RetryWaitContext<R>> waitContext;

    /**
     * 阻塞实现
     */
    private RetryBlock block;

    /**
     * 停止策略
     */
    private RetryStop stop;

    /**
     * 可执行的方法
     */
    private Callable<R> callable;

    /**
     * 监听器
     */
    private RetryListener listener;

    /**
     * 恢复策略
     */
    private Recover recover;

}
