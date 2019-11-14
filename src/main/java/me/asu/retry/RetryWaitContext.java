package me.asu.retry;

import java.util.List;
import lombok.Data;
import me.asu.retry.strategy.wait.RetryWait;

/**
 * 等待上下文
 *
 * @param <R> 泛型
 */
@Data
public class RetryWaitContext<R>
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

	/**
	 * 基础值
	 */
	private long value;

	/**
	 * 最小值
	 */
	private long min;

	/**
	 * 最大值
	 */
	private long max;

	/**
	 * 变化因子
	 */
	private double factor;

	/**
	 * 重试等待类
	 */
	private Class<? extends RetryWait> retryWait;

}
