package com.sydata.collect.api.validated.valid;

import com.sydata.collect.api.validated.annotation.TargetIsBefore;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 校验引用目标字段必须小于目标字段，如果是时间类型，则是早于目标时间,注意比较目标必须是同类型
 * @date 2022/11/18 11:03
 */
public class TargetIsBeforeValid extends BaseValid<TargetIsBefore, Object> {

    private String target;

    private double rate;

    @Override
    public void initialize(TargetIsBefore targetIsBefore) {
        target = targetIsBefore.target();
        rate = targetIsBefore.rate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object object = super.getValByParam(target);
        if (object == null || value == null) {
            return true;
        } else if (LocalDate.class.equals(object.getClass())) {
            return ((LocalDate) value).isBefore((LocalDate) object);
        } else if (LocalDateTime.class.equals(object.getClass())) {
            return ((LocalDateTime) value).isBefore((LocalDateTime) object);
        } else if (BigDecimal.class.equals(object.getClass())) {
            BigDecimal realObject = ((BigDecimal) object).multiply(BigDecimal.valueOf(rate));
            return ((BigDecimal) value).compareTo(realObject) < 0;
        }
        return true;
    }
}
