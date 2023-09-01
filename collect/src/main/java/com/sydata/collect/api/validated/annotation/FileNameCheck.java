package com.sydata.collect.api.validated.annotation;

import com.sydata.collect.api.validated.valid.FileNameCheckValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lzq
 * @description 文件名校验注解
 * @date 2022/10/21 10:14
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileNameCheckValid.class})
@Documented
public @interface FileNameCheck {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 文件类型
     */
    String wjlx();
}
