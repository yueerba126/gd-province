package com.sydata.collect.api.annotation;

import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.framework.databind.annotation.DataBindFieldConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lzq
 * @describe 数据API标识注解
 * @date 2022-07-16 16:45
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@DataBindFieldConfig
public @interface DataApi {

    /**
     * 数据API枚举
     */
    DataApiEnum value();

    /**
     * 是否需要记录数据处理
     */
    boolean isDataHandle() default true;
}
