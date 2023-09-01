package com.sydata.collect.api.validated.annotation;

import com.sydata.collect.api.validated.valid.ImgPositionCheckValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lzq
 * @description 库区平面图相对位置校验
 * @date 2022/10/19 11:03
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImgPositionCheckValid.class})
@Documented
public @interface ImgPositionCheck {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
