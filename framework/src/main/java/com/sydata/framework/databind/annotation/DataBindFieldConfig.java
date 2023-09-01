package com.sydata.framework.databind.annotation;


import com.sydata.framework.databind.domain.Empty;
import com.sydata.framework.databind.handle.value.bind.ValueBindStrategy;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;

/**
 * 基础数据转换字段配置
 *
 * @author zhoucl
 * @date 2021/5/12 19:17
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataBindFieldConfig {

    /**
     * 数据来源字段  el 表达式
     */
    String sourceField() default StringUtils.EMPTY;

    /**
     * 数据来源字段基本类型
     */
    Class<?> sourceDataType() default Empty.class;

    /**
     * 获取到远端数据使用哪个字段值来赋值字段上 el 表达式
     */
    String dataValue() default StringUtils.EMPTY;

    /**
     * 使用远端哪个字段作为唯一值查询数据
     */
    String queryColumn() default StringUtils.EMPTY;

    /**
     * 如果远端查询需要其他条件辅助确定唯一值时使用该字段拼接sql，支持el表达式
     */
    String queryAndSql() default StringUtils.EMPTY;

    /**
     * 拼装sql提供字段枚举，该枚举必须提供getDataBindFields无参数方法并且返回List<DataBindField>类型数据
     */
    Class<? extends Enum<?>> queryAndSqlColumns() default Empty.class;

    /**
     * 数据来源字段组合
     */
    String sourceFieldCombination() default StringUtils.EMPTY;

    /**
     * 数据值绑定方案
     */
    ValueBindStrategy valueBindStrategy() default ValueBindStrategy.DEFAULT;

    /**
     * 绑定分隔符(默认逗号)
     */
    String bindSeparated() default COMMA;
}
