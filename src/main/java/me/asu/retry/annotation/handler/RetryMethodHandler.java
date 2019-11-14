package me.asu.retry.annotation.handler;

import static me.asu.retry.ClassUtils.newInstance;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.Callable;
import me.asu.retry.RetryContext;
import me.asu.retry.RetryExecutor;
import me.asu.retry.annotation.RetryAbleBean;
import me.asu.retry.annotation.annotation.Retry;
import me.asu.retry.annotation.annotation.metadata.RetryAble;

/**
 * 默认的重试方法实现
 */
public class RetryMethodHandler  {
    public Object handle(Object proxy, Method method, Object[] args) throws Throwable {
        //1. 判断注解信息
        Optional<RetryAbleBean> retryAbleAnnotationOpt = retryAbleAnnotation(method);
        if(!retryAbleAnnotationOpt.isPresent()) {
            return method.invoke(proxy, args);
        }

        //2. 包含注解才进行处理
        final RetryAbleBean retryAbleBean = retryAbleAnnotationOpt.get();
        final Callable callable = buildCallable(proxy, method, args);
        final RetryAbleHandler retryAbleHandler = newInstance(retryAbleBean.retryAble().value());


        // 3. 构建执行上下文
        RetryContext retryContext = retryAbleHandler.build((Retry)retryAbleBean.annotation(), callable);
        return RetryExecutor.create().retryCall(retryContext);
    }

    /**
     * 重试调用
     * @param retryAbleBean 重试调用对象
     * @param callable 待重试方法
     * @return 执行结果
     */
    public Object retryCall(final RetryAbleBean retryAbleBean,
                            final Callable callable) {
        final RetryAbleHandler retryAbleHandler = newInstance(retryAbleBean.retryAble().value());

        // 3. 构建执行上下文
        RetryContext retryContext = retryAbleHandler.build((Retry)retryAbleBean.annotation(), callable);
        return RetryExecutor.create().retryCall(retryContext);
    }

    /**
     * 为了处理简单，只匹配第一个符合条件的重试注解
     * @param method 方法体
     * @return optional
     */
    public Optional<RetryAbleBean> retryAbleAnnotation(final Method method) {
        Annotation[] annotations = method.getAnnotations();
        if(annotations == null) {
            return Optional.empty();
        }

        for(Annotation annotation : annotations) {
            RetryAble retryAble = annotation.annotationType().getAnnotation(RetryAble.class);
            if (retryAble != null) {
                RetryAbleBean bean = new RetryAbleBean();
                bean.retryAble(retryAble).annotation(annotation);
                return Optional.of(bean);
            }
        }

        return Optional.empty();
    }

    /**
     * 构建 callable
     * @param proxy 代理
     * @param method 方法
     * @param args 参数
     * @return callable 对象
     */
    private Callable buildCallable(final Object proxy, final Method method, final Object[] args) {
        return () -> method.invoke(proxy, args);
    }

}
