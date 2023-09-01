package com.sydata.framework.core.jackson.format;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

import static java.lang.annotation.ElementType.*;

/**
 * @author lzq
 * @description BigDecimal精度设置注解
 * @date 2023-06-19 11:22
 */
@Target({ANNOTATION_TYPE, FIELD, METHOD, PARAMETER, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface BigDecimalFormat {

    /**
     * 精度
     */
    int scale() default 2;

    /**
     * 舍入模式
     */
    RoundingMode mode() default RoundingMode.HALF_UP;
}