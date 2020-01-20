package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * @author:LiPing
 * @description：RequestMapping注解
 * @date:Created in 15:57 2020/1/14 0014
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface LagouRequestMapping {
    String value() default "";
}
