package me.asu.retry.annotation.annotation;


import java.lang.annotation.*;
import me.asu.retry.Recover;
import me.asu.retry.RetryListener;
import me.asu.retry.annotation.annotation.metadata.RetryAble;
import me.asu.retry.annotation.handler.RetryAbleHandler;
import me.asu.retry.condition.CauseRetryCondition;
import me.asu.retry.condition.RetryCondition;

/**
 * 重试注解
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RetryAble(RetryAbleHandler.class)
public @interface Retry
{

	/**
	 * 重试类实现
	 *
	 * @return 重试
	 */
	Class<? extends me.asu.retry.Retry> retry() default me.asu.retry.Retry.class;

	/**
	 * 最大尝试次数
	 * 1. 包含方法第一次正常执行的次数
	 *
	 * @return 次数
	 */
	int maxAttempt() default 3;

	/**
	 * 重试触发的场景
	 *
	 * @return 重试触发的场景
	 */
	Class<? extends RetryCondition> condition() default CauseRetryCondition.class;

	/**
	 * 监听器
	 * 1. 默认不进行监听
	 *
	 * @return 监听器
	 */
	Class<? extends RetryListener> listener() default RetryListener.NoRetryListener.class;

	/**
	 * 恢复操作
	 * 1. 默认不进行任何恢复操作
	 *
	 * @return 恢复操作对应的类
	 */
	Class<? extends Recover> recover() default Recover.NoRecover.class;

	/**
	 * 等待策略
	 * 1. 支持指定多个，如果不指定，则不进行任何等待，
	 *
	 * @return 等待策略
	 */
	RetryWait[] waits() default {};

}
