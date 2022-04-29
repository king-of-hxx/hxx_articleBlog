package com.hxx.common.aop;


import java.lang.annotation.*;

/**
 * @author shlcm
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;

    String name() default "";

}