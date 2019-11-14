package me.asu.retry.annotation.proxy;

import static me.asu.retry.ClassUtils.newInstance;

import java.lang.reflect.*;
import me.asu.retry.annotation.handler.RetryMethodHandler;

/**
 * 动态代理
 */
public class DynamicProxy implements InvocationHandler, ProxyGetter {
    private final Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return newInstance(RetryMethodHandler.class).handle(target, method, args);
        } catch (InvocationTargetException ex) {
            // 程序内部没有处理的异常
            throw ex.getTargetException();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Override
    public Object proxy() {
        // 我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandler handler = new DynamicProxy(target);

        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                                            target.getClass().getInterfaces(), handler);
    }
}
