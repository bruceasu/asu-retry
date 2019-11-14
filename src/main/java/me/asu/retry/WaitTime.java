package me.asu.retry;


import java.util.concurrent.TimeUnit;
import lombok.Data;

@Data
public class WaitTime
{

	/**
	 * 等待时间
	 */
	private final long time;

	/**
	 * 时间单位
	 */
	private final TimeUnit unit;

	public WaitTime(long time)
	{
		this.time = time;
		this.unit = TimeUnit.MILLISECONDS;
	}

	public WaitTime(long time, TimeUnit unit)
	{
		this.time = time;
		this.unit = unit;
	}

}
