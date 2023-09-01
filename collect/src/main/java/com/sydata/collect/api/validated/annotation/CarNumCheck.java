package com.sydata.collect.api.validated.annotation;

import com.sydata.collect.api.validated.valid.CarNumCheckValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author czx
 * @description 校验车船号, 运输工具字典值为1-即汽车时进行正则校验。运输工具:1：汽车,2：火车,3：轮船,9：其他
 * @date 2022/10/19 11:03
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CarNumCheckValid.class})
@Documented
public @interface CarNumCheck {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 引用目标字段,注意目标字段需要是运输工具类型字段
     */
    String target();
}
