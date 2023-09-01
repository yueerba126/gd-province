package com.sydata.common.file.enums;

/**
 * @author lzq
 * @description 文件状态枚举
 * @date 2022/10/24 10:58
 */
public enum FileStateEnum {

    /**
     * 未使用
     */
    NOT_USE("1", "未使用"),


    /**
     * 使用中
     */
    USE("2", "使用中"),


    /**
     * 已弃用
     */
    DISCARD("3", "已弃用"),


    ;
    private final String state;
    private final String msg;

    FileStateEnum(String state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
