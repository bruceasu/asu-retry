package me.asu.retry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassUtils
{
	public static <T> T newInstance(Class<T> cls)
	{
		if (cls == null) { return null; }
		try {
			return cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("", e);
		}
		return null;
	}
}
