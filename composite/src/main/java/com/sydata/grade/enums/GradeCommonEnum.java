package com.sydata.grade.enums;

/**
 * @program: gd-province-platform
 * @description: 公用状态（实地状态和授牌状态）
 * @author: lzq
 * @create: 2023-06-09 15:29
 */
public enum GradeCommonEnum {

    /**
     * 待评分
     */
    DPF("1"),
    /**
     * 待授牌
     */
    DSP("2"),
    ;

    private String state;


    GradeCommonEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
