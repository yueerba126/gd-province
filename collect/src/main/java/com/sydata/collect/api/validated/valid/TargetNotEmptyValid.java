package com.sydata.collect.api.validated.valid;

import com.sydata.collect.api.validated.annotation.TargetNotEmpty;

import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @author lzq
 * @description 校验引用目标字段不为空时, 引用该注解本身字段也不能为空
 * @date 2022/10/20 14:41
 */
public class TargetNotEmptyValid extends BaseValid<TargetNotEmpty, Object> {

    private String target;

    @Override
    public void initialize(TargetNotEmpty targetNotEmpty) {
        target = targetNotEmpty.target();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object targetValue = super.getValByParam(target);
        return isEmpty(targetValue) || isNotEmpty(value);
    }
}
