package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * @author:LiPing
 * @description：Autowired注解
 * @date:Created in 15:55 2020/1/14 0014
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LagouAutowired {
    String value() default "";
}
