package me.asu.retry;

import java.util.List;
import lombok.Data;

/**
 * 默认重试信息
 *
 * @param <R> 泛型
 */
@Data
public class RetryAttempt<R>
{

	/**
	 * 执行结果
	 */
	private R result;

	/**
	 * 尝试次数
	 */
	private int attempt;

	/**
	 * 尝试次数
	 */
	private Throwable cause;

	/**
	 * 消耗时间
	 */
	private AttemptTime time;

	/**
	 * 历史信息
	 */
	private List<RetryAttempt<R>> history;

}
