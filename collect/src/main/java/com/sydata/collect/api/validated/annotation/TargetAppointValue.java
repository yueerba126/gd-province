package com.sydata.collect.api.validated.annotation;

import com.sydata.collect.api.validated.valid.TargetAppointValueValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lzq
 * @description 校验引用目标字段为指定值数组时, 引用该注解的字段也不能为空
 * @date 2022/10/19 11:03
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TargetAppointValueValid.class})
@Documented
public @interface TargetAppointValue {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 引用目标字段
     */
    String target();

    /**
     * 检定目标值
     */
    String[] targetValue();
}
