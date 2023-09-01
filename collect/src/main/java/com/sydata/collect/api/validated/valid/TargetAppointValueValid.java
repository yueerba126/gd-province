package com.sydata.collect.api.validated.valid;

import com.sydata.collect.api.validated.annotation.TargetAppointValue;

import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @author lzq
 * @description 校验引用目标字段不为空且是否包含于指定值中
 * @date 2022/10/20 14:41
 */
public class TargetAppointValueValid extends BaseValid<TargetAppointValue, Object> {

    private String target;

    private String[] targetValue;

    @Override
    public void initialize(TargetAppointValue targetAppointValue) {
        target = targetAppointValue.target();
        targetValue = targetAppointValue.targetValue();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object targetPresentValue = super.getValByParam(target);
        if (isNotEmpty(targetPresentValue) && Arrays.asList(targetValue).contains(targetPresentValue.toString())) {
            return isNotEmpty(value);
        }
        return Boolean.TRUE;
    }
}
