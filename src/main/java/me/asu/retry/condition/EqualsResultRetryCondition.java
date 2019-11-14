package me.asu.retry.condition;


public class EqualsResultRetryCondition<R> extends AbstractResultRetryCondition<R>
{
	private R excepted;

	public EqualsResultRetryCondition(R excepted)
	{
		this.excepted = excepted;
	}

	@Override
	protected boolean resultCondition(R result)
	{
		return result != null && result.equals(excepted);
	}
}
