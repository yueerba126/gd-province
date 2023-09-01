package com.sydata.framework.databind.annotation;


import com.sydata.framework.databind.domain.Empty;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * 组合注解明细
 *
 * @author zhoucl
 * @date 2021/5/25 18:05
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataBindFieldGroupItemConfig {

    /**
     * 对应数据类型
     */
    String dataType();

    /**
     * 说明请看 {@link DataBindFieldConfig#queryColumn}.
     */
    String queryColumn();

    /**
     * 说明请看 {@link DataBindFieldConfig#queryAndSql}.
     */
    String queryAndSql() default StringUtils.EMPTY;

    /**
     * 说明请看 {@link DataBindFieldConfig#queryAndSqlColumns}.
     */
    Class<? extends Enum<?>> queryAndSqlColumns() default Empty.class;

    /**
     * 获取到数据使用哪个字段值 el 表达式
     */
    String dataValue();

    /**
     * 数据来源字段组合
     */
    String sourceFieldCombination() default StringUtils.EMPTY;


    /**
     * 数据处理注解
     */
    Class<? extends Annotation> dataBindStrategy();
}
