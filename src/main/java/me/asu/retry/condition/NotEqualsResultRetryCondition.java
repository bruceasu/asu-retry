package me.asu.retry.condition;

public class NotEqualsResultRetryCondition<R> extends AbstractResultRetryCondition<R>
{
	private R excepted;

	public NotEqualsResultRetryCondition(R excepted)
	{
		this.excepted = excepted;
	}

	@Override
	protected boolean resultCondition(R result)
	{
		return result == null || !result.equals(excepted);
	}
}
