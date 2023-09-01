package com.sydata.collect.api.validated.valid;

import com.sydata.collect.api.validated.annotation.NotZeroCheck;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import static java.lang.Boolean.TRUE;

/**
 * @author lzq
 * @description 校验数字不能为0
 * @date 2022/10/19 11:03
 */
public class NotZeroCheckValid extends BaseValid<NotZeroCheck, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(value)) {
            return TRUE;
        }
        return BigDecimal.ZERO.compareTo(new BigDecimal(value.toString())) != 0;
    }
}
