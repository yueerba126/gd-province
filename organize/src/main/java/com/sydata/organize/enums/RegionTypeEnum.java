package com.sydata.organize.enums;

/**
 * @author lzq
 * @description 行政区划类型枚举
 * @date 2023/5/8 11:17
 */
public enum RegionTypeEnum {

    /**
     * 国
     */
    COUNTRY("国"),


    /**
     * 省
     */
    PROVINCE("省"),

    /**
     * 市
     */
    CITY("市"),

    /**
     * 区
     */
    AREA("县"),

    ;

    private String type;

    RegionTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
