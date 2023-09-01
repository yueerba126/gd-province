package com.sydata.collect.api.validated.valid;

import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.framework.util.StringUtils;
import org.apache.commons.collections4.SetUtils;

import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.regex.Pattern;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.BACK_SLASH;
import static com.sydata.common.constant.ValidConstant.CHINA;
import static com.sydata.common.constant.ValidConstant.ILLEGAL_NAME;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static jodd.util.StringPool.PIPE;

/**
 * @author czx
 * @description 依据国家粮食平台规则校验人名信息，规则是只允许是中文，但不能是暂无，无，空，测试等
 * @date 2022/10/19 11:03
 */
public class PersonNameRuleValid extends BaseValid<PersonNameRule, String> {

    private boolean split;

    private String splitSign;


    @Override
    public void initialize(PersonNameRule personNameRule) {
        split = personNameRule.split();
        splitSign = personNameRule.splitSign();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(value)) {
            return TRUE;
        }

        // 字符集中含有非指定分隔符和中文外的字符
        if (!Pattern.compile(CHINA.replace(PIPE, splitSign)).matcher(value).matches()) {
            return FALSE;
        }

        // 校验非法人名
        if (split) {
            return Collections.disjoint(SetUtils.hashSet(value.split(BACK_SLASH + splitSign)), ILLEGAL_NAME);
        } else {
            return !ILLEGAL_NAME.contains(value);
        }
    }
}
