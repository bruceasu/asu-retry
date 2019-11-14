package me.asu.retry.condition;

public class ExceptionCauseTypeRetryCondition extends CauseRetryCondition {
	Class<? extends Throwable> exClass;
	public ExceptionCauseTypeRetryCondition(final Class<? extends Throwable> exClass) {
		this.exClass = exClass;
	}
	@Override
	protected boolean hasException(Throwable throwable)
	{
		return exClass.isAssignableFrom(throwable.getClass());
	}
}
