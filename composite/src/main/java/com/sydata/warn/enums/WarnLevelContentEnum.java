package com.sydata.warn.enums;

/**
 * @author fuql
 * @description 预警等级
 * @date 2022/10/21 10:56
 */
public enum WarnLevelContentEnum {

    /**
     * 紧急
     */
    ONE("1", "紧急"),
    TWO("2", "重要"),
    THREE("3", "次要"),
    FORE("4", "一般"),
    FIVE("5", "提示"),


    ;

    private final String key;
    private final String msg;

    WarnLevelContentEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }
}
