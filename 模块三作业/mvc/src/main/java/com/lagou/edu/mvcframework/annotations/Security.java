package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * @author:LiPing
 * @description：@Security
 * @date:Created in 10:45 2020/1/18 0018
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Security {

    String[] value() default {};
}
