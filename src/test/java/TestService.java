import me.asu.retry.annotation.annotation.Retry;

/**
 * Created by Administrator on 2019/11/14.
 *
 * @author Victor
 * @date 2019-11-14
 */
public interface TestService
{
	@Retry
	void say(String string);

}
