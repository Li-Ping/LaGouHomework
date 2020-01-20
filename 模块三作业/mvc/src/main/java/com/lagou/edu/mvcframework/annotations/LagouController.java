package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * @author:LiPing
 * @description：controller注解
 * @date:Created in 15:55 2020/1/14 0014
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LagouController {
    String value() default "";
}
