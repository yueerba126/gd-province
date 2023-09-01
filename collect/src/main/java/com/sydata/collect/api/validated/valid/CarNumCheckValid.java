package com.sydata.collect.api.validated.valid;

import com.sydata.collect.api.validated.annotation.CarNumCheck;
import com.sydata.framework.util.StringUtils;

import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static cn.hutool.core.lang.RegexPool.PLATE_NUMBER;
import static com.sydata.common.constant.ValidConstant.QC;
import static java.lang.Boolean.TRUE;

/**
 * @author czx
 * @description 校验车船号, 运输工具字典值为1-即汽车时进行正则校验。运输工具:1：汽车,2：火车,3：轮船,9：其他
 * @date 2022/10/19 11:03
 */
public class CarNumCheckValid extends BaseValid<CarNumCheck, String> {

    private String target;

    @Override
    public void initialize(CarNumCheck carNumCheck) {
        target = carNumCheck.target();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(value)) {
            return TRUE;
        }

        Object ysgjValue = super.getValByParam(target);
        //如果注解指定的值是空或者不是汽车的字典值1，直接返回
        if (ysgjValue == null || !QC.equals(ysgjValue.toString())) {
            return TRUE;
        }
        return Pattern.compile(PLATE_NUMBER).matcher(value).matches();
    }
}
