package com.sydata.framework.databind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 该注解用在组合数据,两个字段值一对一关系
 * </p>
 * "stockSourceTypeDictValue": "customer,tenant",
 * "stockSourceId": "10564,366",
 * customer对应10564；tenant对应366
 *
 * @author zhoucl
 * @date 2021/5/25 16:58
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBindFieldGroupConfig {

    /**
     * 数据类型来源字段  el 表达式
     */
    String sourceTypeField();

    /**
     * 数据来源字段  el 表达式
     */
    String sourceValueField();

    DataBindFieldGroupItemConfig[] item();

}
