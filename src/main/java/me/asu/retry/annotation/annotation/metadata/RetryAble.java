package me.asu.retry.annotation.annotation.metadata;

import java.lang.annotation.*;
import me.asu.retry.annotation.handler.RetryAbleHandler;

/**
 * 可重试的注解
 * 1. 用于注解之上用于指定重试信息
 *
 * @see {@link me.asu.retry.RetryContext} 重试上下文
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
public @interface RetryAble
{

	/**
	 * 对应的注解处理器
	 *
	 * @return class 信息
	 */
	Class<? extends RetryAbleHandler> value();

}
