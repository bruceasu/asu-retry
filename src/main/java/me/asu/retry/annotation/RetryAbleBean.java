package me.asu.retry.annotation;

import java.lang.annotation.Annotation;
import me.asu.retry.annotation.annotation.metadata.RetryAble;

/**
 * 可重试注解对象
 */
public class RetryAbleBean {

    /**
     * 注解信息
     */
    private RetryAble retryAble;

    /**
     * 原始注解信息
     */
    private Annotation annotation;

    public RetryAble retryAble() {
        return retryAble;
    }

    public RetryAbleBean retryAble(RetryAble retryAble) {
        this.retryAble = retryAble;
        return this;
    }

    public Annotation annotation() {
        return annotation;
    }

    public RetryAbleBean annotation(Annotation annotation) {
        this.annotation = annotation;
        return this;
    }
}
