package com.sydata.basis.enums;

import cn.hutool.core.date.TemporalAccessorUtil;
import one.util.streamex.StreamEx;

import java.time.temporal.TemporalAccessor;
import java.util.Map;

import static cn.hutool.core.date.DatePattern.*;
import static java.util.function.Function.identity;

/**
 * @author lzq
 * @description 财务报表类型枚举
 * @date 2023/1/10 9:40
 */
public enum FinanceTypeEnum {


    /**
     * 年报
     */
    YEAR(4, "年报", NORM_YEAR_PATTERN),

    /**
     * 月报
     */
    MONTH(6, "月报", SIMPLE_MONTH_PATTERN),

    /**
     * 日报
     */
    DAY(8, "日报", PURE_DATE_PATTERN),

    ;

    private static Map<Integer, FinanceTypeEnum> MAP = StreamEx.of(values()).toMap(FinanceTypeEnum::getLength, identity());

    private final int length;
    private final String type;
    private final String pattern;

    FinanceTypeEnum(int length, String type, String pattern) {
        this.length = length;
        this.type = type;
        this.pattern = pattern;
    }

    public int getLength() {
        return length;
    }

    public String getType() {
        return type;
    }

    public String getPattern() {
        return pattern;
    }

    /**
     * 时间校验
     *
     * @param date 报表时间
     * @return 是否正确时间
     */
    public boolean checkDate(String date) {
        try {
            TemporalAccessor temporalAccessor = createFormatter(pattern).parse(date);
            TemporalAccessorUtil.toEpochMilli(temporalAccessor);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 根据长度获取报表类型
     *
     * @param length 长度
     * @return 报表类型
     */
    public static FinanceTypeEnum getByLength(int length) {
        return MAP.get(length);
    }
}
