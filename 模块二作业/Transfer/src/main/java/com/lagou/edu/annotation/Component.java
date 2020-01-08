package com.lagou.edu.annotation;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 9:39 2020/1/8 0008
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    String value() default "";
}
