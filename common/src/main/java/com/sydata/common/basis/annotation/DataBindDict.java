package com.sydata.common.basis.annotation;

import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.handle.value.bind.ValueBindStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;

/**
 * @author lzq
 * @describe 字典数据绑定
 * @date 2022-06-29 19:44
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@DataBindFieldConfig
public @interface DataBindDict {
    String DICT_KEY = "dict_key";
    String DICT_VALUE = "dict_value";

    /**
     * 说明请看 {@link DataBindFieldConfig#sourceField}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String sourceField() default "#dictKey";

    /**
     * 说明请看 {@link DataBindFieldConfig#queryColumn}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String queryColumn() default DICT_KEY;

    /**
     * 说明请看 {@link DataBindFieldConfig#dataValue}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String dataValue() default "#dictValue";

    /**
     * 字典类型
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String sourceFieldCombination();

    /**
     * 说明请看 {@link DataBindFieldConfig#valueBindStrategy}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    ValueBindStrategy valueBindStrategy() default ValueBindStrategy.DEFAULT;

    /**
     * 绑定分隔符(默认逗号)
     * 说明请看 {@link DataBindFieldConfig#valueBindStrategy}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String bindSeparated() default COMMA;
}
