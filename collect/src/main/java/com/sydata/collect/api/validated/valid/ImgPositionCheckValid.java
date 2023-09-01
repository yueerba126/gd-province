package com.sydata.collect.api.validated.valid;

import cn.hutool.extra.spring.SpringUtil;
import com.sydata.collect.api.validated.annotation.ImgPositionCheck;
import com.sydata.framework.util.StringUtils;

import javax.validation.ConstraintValidatorContext;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author lzq
 * @description 库区平面图相对位置校验
 * @date 2022/10/19 11:03
 */
public class ImgPositionCheckValid extends BaseValid<ImgPositionCheck, String> {

    private int width;

    private int height;

    @Override
    public void initialize(ImgPositionCheck imgPositionCheck) {
        this.width = Integer.parseInt(SpringUtil.getProperty("basis.aerialViewImgWidth"));
        this.height = Integer.parseInt(SpringUtil.getProperty("basis.aerialViewImgHeight"));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(value)) {
            return TRUE;
        }

        // 校验格式(x,y)
        String left = value.substring(0, 1);
        String right = value.substring(value.length() - 1);
        if (!LEFT_BRACKET.equals(left) || !RIGHT_BRACKET.equals(right)) {
            return FALSE;
        }

        int index = value.indexOf(COMMA);
        if (index == -1) {
            return FALSE;
        }

        // xy轴必须在指定宽高之内
        try {
            String[] split = value.substring(1, value.length() - 1).split(COMMA);
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            return x <= width && y <= height;
        } catch (NumberFormatException e) {
            return FALSE;
        }
    }
}
