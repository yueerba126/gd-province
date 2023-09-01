package com.sydata.collect.api.validated.valid;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.sydata.common.constant.ValidConstant.YYYY_MM_DD;
import static com.sydata.framework.util.StringUtils.isEmpty;

/**
 * @author lzq
 * @description 校验本身字段必须是开始于引用目标字段组合，按给定顺序排序，当前支持LocalDate和String类型
 * @date 2022/10/20 14:41
 */
public class TargetStartsWithValid extends BaseValid<TargetStartsWith, String> {

    private String[] targets;

    @Override
    public void initialize(TargetStartsWith targetStartsWith) {
        targets = targetStartsWith.target();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        StringBuffer targetValues = new StringBuffer();
        for (String target : this.targets) {
            Object object = super.getValByParam(target);
            if (object == null) {
                continue;
            }

            if (LocalDate.class.equals(object.getClass())) {
                targetValues.append(LocalDateTimeUtil.format((LocalDate) object, YYYY_MM_DD));
            } else if (LocalDateTime.class.equals(object.getClass())) {
                targetValues.append(LocalDateTimeUtil.format((LocalDateTime) object, YYYY_MM_DD));
            } else {
                targetValues.append(object);
            }
        }
        return isEmpty(value) || isEmpty(targetValues) || value.startsWith(targetValues.toString());
    }
}
