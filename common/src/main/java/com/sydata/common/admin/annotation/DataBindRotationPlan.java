package com.sydata.common.admin.annotation;

import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.handle.value.bind.ValueBindStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lzq
 * @describe 轮换计划数据绑定
 * @date 2022-06-29 19:44
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@DataBindFieldConfig
public @interface DataBindRotationPlan {

    /**
     * 说明请看 {@link DataBindFieldConfig#sourceField}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String sourceField() default "#id";

    /**
     * 说明请看 {@link DataBindFieldConfig#queryColumn}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String queryColumn() default "id";

    /**
     * 说明请看 {@link DataBindFieldConfig#dataValue}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String dataValue() default "#lhjhdh";

    /**
     * 说明请看 {@link DataBindFieldConfig#valueBindStrategy}.
     */
    @AliasFor(annotation = DataBindFieldConfig.class)
    ValueBindStrategy valueBindStrategy() default ValueBindStrategy.DEFAULT;

}
