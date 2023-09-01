package com.sydata.collect.api.validated.valid;

import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.framework.util.StringUtils;

import javax.validation.ConstraintValidatorContext;

import static java.lang.Boolean.TRUE;

/**
 * @author lzq
 * @description string长度校验，中文占用两个字节
 * @date 2022/11/30 20:01
 */
public class StringSizeValid extends BaseValid<StringSize, String> {

    private int min;

    private int max;

    @Override
    public void initialize(StringSize stringSize) {
        min = stringSize.min();
        max = stringSize.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return TRUE;
        }

        int size = StringUtils.stringLength(value);
        return size <= max && size >= min;
    }
}
