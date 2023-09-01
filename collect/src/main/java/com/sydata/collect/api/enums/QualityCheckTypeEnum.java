package com.sydata.collect.api.enums;

import one.util.streamex.StreamEx;

import java.util.Map;

/**
 * @author lzq
 * @description 指标类别类型枚举
 * @date 2022/10/21 10:08
 */
public enum QualityCheckTypeEnum {

    /**
     * 质量指标判定结果1
     */
    Q1("质量指标检验", "达标"),

    /**
     * 质量指标判定结果2
     */

    Q2("质量指标检验", "不达标"),

    /**
     * 质量指标判定结果3
     */
    Q3("质量指标检验", "达标|综合判定"),

    /**
     * 质量指标判定结果4
     */
    Q4("质量指标检验", "不达标|综合判定"),

    /**
     * 储存品质指标判定结果1
     */
    S1("储存品质检验", "宜存"),

    /**
     * 储存品质指标判定结果2
     */
    S2("储存品质检验", "轻度不宜存"),

    /**
     * 储存品质指标判定结果3
     */
    S3("储存品质检验", "重度不宜存"),

    /**
     * 储存品质指标判定结果4
     */
    S4("储存品质检验", "宜存|综合判定"),

    /**
     * 储存品质指标判定结果5
     */
    S5("储存品质检验", "轻度不宜存|综合判定"),

    /**
     * 储存品质指标判定结果6
     */
    S6("储存品质检验", "重度不宜存|综合判定"),

    /**
     * 食品安全指标判定结果1
     */
    F1("食品安全检验", "合格"),

    /**
     * 食品安全指标判定结果2
     */
    F2("食品安全检验", "不合格"),

    /**
     * 食品安全指标判定结果3
     */
    F3("食品安全检验", "合格|综合判定"),

    /**
     * 食品安全指标判定结果4
     */
    F4("食品安全检验", "不合格|综合判定");

    private final String type;

    private final String description;

    private static Map<String, String> ZB_TYPE = StreamEx.of(values()).toMap(QualityCheckTypeEnum::getDescription, v -> v.getType());

    QualityCheckTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据指标结果获取指标类别
     * @param description
     * @return
     */
    public static String getByDescription(String description) {
        return ZB_TYPE.get(description);
    }
}
