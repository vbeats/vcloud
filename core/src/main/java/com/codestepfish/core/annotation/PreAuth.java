package com.codestepfish.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreAuth {
    boolean superOnly() default true; // 只有运营平台用户可以访问
}
