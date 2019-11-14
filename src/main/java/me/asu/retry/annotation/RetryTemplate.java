package me.asu.retry.annotation;

import java.util.Objects;
import me.asu.retry.annotation.proxy.*;

/**
 * 重试模板
 *
 */
public final class RetryTemplate
{

	private RetryTemplate() {}

	/**
	 * 根据对象获取对应的代理
	 *
	 * @param object 原始对象
	 * @return 对应的代理对象
	 */
	public static <R> R createProxy(final Object object, final Class<R> retClazz)
	{
		Objects.requireNonNull(object);
		ProxyGetter         proxy = null;
		if (retClazz != null) {
			Class<?> aClass = object.getClass();
			Class<?>[] interfaces = aClass.getInterfaces();
			if (interfaces != null) {
				for (Class<?> anInterface : interfaces) {
					if (anInterface.equals(retClazz)) {
						proxy = new DynamicProxy(object);
						break;
					}
				}
				if (proxy == null) {
					proxy = new CglibProxy(object);
				}
			} else {
				proxy = new CglibProxy(object);
			}

		} else {
			proxy = new CglibProxy(object);
		}
		return (R) proxy.proxy();
	}

}
