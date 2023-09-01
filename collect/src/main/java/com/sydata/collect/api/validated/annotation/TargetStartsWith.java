package com.sydata.collect.api.validated.annotation;

import com.sydata.collect.api.validated.valid.TargetStartsWithValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lzq
 * @description 校验本身字段必须是开始于引用目标字段组合，按给定顺序排序，当前支持LocalDate,LocalDateTime和String类型
 * @date 2022/10/19 11:03
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TargetStartsWithValid.class})
@Documented
public @interface TargetStartsWith {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 引用目标字段
     */
    String[] target();
}
