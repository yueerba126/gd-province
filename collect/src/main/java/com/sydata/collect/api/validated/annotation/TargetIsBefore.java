package com.sydata.collect.api.validated.annotation;

import com.sydata.collect.api.validated.valid.TargetIsBeforeValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lzq
 * @description 校验引用目标字段数值型必须小于目标字段，如果是时间类型，则是早于目标时间,注意比较目标必须是同类型
 * @date 2022/11/18 11:03
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TargetIsBeforeValid.class})
@Documented
public @interface TargetIsBefore {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 引用目标字段
     */
    String target();

    /**
     * 转换比例，由目标字段与rate相乘后再与注解字段比较,只会在引用字段和目标字段都是BigDecimal类型时生效
     */
    double rate() default 1d;
}
