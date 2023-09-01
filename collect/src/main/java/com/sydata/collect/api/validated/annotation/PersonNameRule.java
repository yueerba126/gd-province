package com.sydata.collect.api.validated.annotation;

import com.sydata.collect.api.validated.valid.PersonNameRuleValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static jodd.util.StringPool.PIPE;

/**
 * @author czx
 * @description 依据国家粮食平台规则校验人名信息，规则是只允许是中文，但不能是暂无，无，空，测试等
 * @date 2022/10/19 11:03
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PersonNameRuleValid.class})
@Documented
public @interface PersonNameRule {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 是否允许有分隔符组成
     *
     * @return
     */
    boolean split() default false;


    /**
     * 默认分隔符 |
     *
     * @return
     */
    String splitSign() default PIPE;

}
