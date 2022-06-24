package com.lc.compiler.util.log;

import java.lang.annotation.*;

/**
 * 注解在requestMapping上, 记录接口调用过程
 *
 * @author Aaron Yeung
 * @date 4/8/2022 10:09 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiLog {

    /**
     * 设置接口描述
     *
     * @return str
     */
    String value() default "";

    String description() default "";
}
