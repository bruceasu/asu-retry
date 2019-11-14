import me.asu.retry.annotation.annotation.Retry;

/**
 * Created by Administrator on 2019/11/14.
 *
 * @author Victor
 * @date 2019-11-14
 */
public class TestServiceImpl implements TestService
{

	@Override
	@Retry
	public void say(String string)
	{
		System.out.println("string = " + string);
		throw new RuntimeException();
	}
}
