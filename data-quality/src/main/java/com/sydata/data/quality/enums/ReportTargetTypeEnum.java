package com.sydata.data.quality.enums;

/**
 * @author lzq
 * @description 数据质量报告类型枚举
 * @date 2023/4/25 11:29
 */
public enum ReportTargetTypeEnum {

    /**
     * 库区
     */
    STOCK_HOUSE(1, "库区"),

    /**
     * 单位
     */
    ENTERPRISE(2, "单位"),

    /**
     * 区县
     */
    AREA(3, "区县"),

    /**
     * 地市
     */
    CITY(4, "地市"),

    /**
     * 省份
     */
    PROVINCE(5, "省份"),

    ;

    private int type;

    private String msg;

    ReportTargetTypeEnum(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
