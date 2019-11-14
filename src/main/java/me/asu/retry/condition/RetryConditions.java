package me.asu.retry.condition;

/**
 * 重试条件工具类
 */
public final class RetryConditions
{

	private RetryConditions() {}

	/**
	 * 结果为空
	 *
	 * @param <R> 单例
	 * @return 结果为空
	 */
	public static <R> RetryCondition<R> isNullResult()
	{
		return NullResultRetryCondition.getInstance();
	}

	/**
	 * 结果不为空
	 *
	 * @param <R> 单例
	 * @return 结果为空
	 */
	public static <R> RetryCondition<R> isNotNullResult()
	{
		return NotNullResultRetryCondition.getInstance();
	}

	/**
	 * 结果等于预期值
	 *
	 * @param excepted 预期值
	 * @param <R> 单例
	 * @return 结果为空
	 */
	public static <R> RetryCondition<R> isEqualsResult(final R excepted)
	{
		return new EqualsResultRetryCondition<>(excepted);
	}

	/**
	 * 结果不等于预期值
	 *
	 * @param excepted 预期值
	 * @param <R> 单例
	 * @return 结果为空
	 */
	public static <R> RetryCondition<R> isNotEqualsResult(final R excepted)
	{
		return new NotEqualsResultRetryCondition<>(excepted);
	}

	/**
	 * 程序执行过程中遇到异常
	 *
	 * @return 重试条件
	 */
	public static RetryCondition hasExceptionCause()
	{
		return CauseRetryCondition.getInstance();
	}

	/**
	 * 是预期的异常类型
	 *
	 * @param exClass 异常类型
	 * @return 异常重试条件
	 */
	public static RetryCondition isExceptionCauseType(final Class<? extends Throwable> exClass)
	{
		return new ExceptionCauseTypeRetryCondition(exClass);
	}
}
