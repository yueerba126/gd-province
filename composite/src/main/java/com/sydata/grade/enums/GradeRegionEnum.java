package com.sydata.grade.enums;

/**
 * @program: gd-province-platform
 * @description:
 * @author: lzq
 * @create: 2023-06-09 15:58
 */
public enum GradeRegionEnum {
    /**
     * 广东省
     */
    GD("440000"),
    ;

    private String state;


    GradeRegionEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
