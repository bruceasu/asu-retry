
import me.asu.retry.annotation.RetryTemplate;
import org.junit.Test;

/**
 * Created by Administrator on 2019/11/14.
 *
 * @author Victor
 * @date 2019-11-14
 */
public class TestServiceImplTest
{

	@Test(expected = RuntimeException.class)
	public void testSay() {
		String s = "hahaha.";
		TestServiceImpl ts = new TestServiceImpl();
		TestService proxy = RetryTemplate.createProxy(ts, TestService.class);
		proxy.say(s);
	}
}