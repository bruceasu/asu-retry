package me.asu.retry.annotation.proxy;

import static me.asu.retry.ClassUtils.newInstance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import me.asu.retry.annotation.handler.RetryMethodHandler;
import net.sf.cglib.proxy.*;

/**
 * CGLIB 代理
 */
public class CglibProxy implements MethodInterceptor, ProxyGetter {

    private final Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        try {
            return newInstance(RetryMethodHandler.class).handle(target, method, objects);
        } catch (InvocationTargetException ex) {
            // 程序内部没有处理的异常
            throw ex.getTargetException();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Override
	public Object proxy() {
        Enhancer enhancer = new Enhancer();
        //目标对象类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        //通过字节码技术创建目标对象类的子类实例作为代理
        return enhancer.create();
    }

}
