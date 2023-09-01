package com.sydata.dashboard.enums;

/**
 * 粮食品种分类-国家平台分类一级分类
 *
 * @author fuql
 * @date 2020/10/24
 */
public enum SortTypeOneEnums {

    /**
     * 1原粮总量
     */
    ONE("1", "原粮总量"),
    /**
     * 2原油总量
     */
    TWO("2", "原油总量"),
    /**
     * 3成品粮总量
     */
    THREE("3", "成品粮总量"),
    /**
     * 4成品油总量
     */
    FORE("4", "成品油总量"),
    /**
     * 5其他类总量
     */
    FIVE("5", "其他类总量"),
;

    private String code;
    private String name;

    SortTypeOneEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
