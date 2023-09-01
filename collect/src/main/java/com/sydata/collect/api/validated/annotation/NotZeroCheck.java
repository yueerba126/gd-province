package com.sydata.collect.api.validated.annotation;

import com.sydata.collect.api.validated.valid.NotZeroCheckValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lzq
 * @description 校验数字不能为0
 * @date 2022/10/19 11:03
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotZeroCheckValid.class})
@Documented
public @interface NotZeroCheck {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
