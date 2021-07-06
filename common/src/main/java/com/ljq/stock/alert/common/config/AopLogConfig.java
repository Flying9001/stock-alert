package com.ljq.stock.alert.common.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: Aop 日志配置
 * @Author: junqiang.lu
 * @Date: 2020/9/4
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AopLogConfig {

    /**
     * 是否忽略传入参数,默认为 false
     *
     * @return
     */
    boolean ignoreInput() default false;

    /**
     * 是否忽略返回参数,默认为 false
     *
     * @return
     */
    boolean ignoreOutput() default false;

}
