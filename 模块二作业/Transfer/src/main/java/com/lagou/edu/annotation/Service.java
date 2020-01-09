package com.lagou.edu.annotation;

import com.alibaba.druid.proxy.jdbc.JdbcParameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:LiPing
 * @description：实现Service注解
 * @date:Created in 17:44 2020/1/7 0007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
    String value() default "";
}
