package com.sydata.warn.enums;

/**
 * @author fuql
 * @description 预警状态
 * @date 2022/10/21 10:56
 */
public enum HandleStatusContentEnum {

    /**
     * 未处理
     */
    ONE("1", "未处理"),
    TWO("2", "处理中"),
    THREE("3", "已处理"),


    ;

    private final String key;
    private final String msg;

    HandleStatusContentEnum(String key, String msg) {
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
