package com.sydata.framework.databind.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基础数据转换
 *
 * @author zhoucl
 * @date 2021/5/12 19:17
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBindFieldConvert {
    /**
     * 转换次数，适用于数据需要多次拉取
     *
     * @return 转换次数
     */
    int convertNumber() default 1;
}
