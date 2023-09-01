package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 授牌校验
 * @date 2023/5/18 15:25
 */
public enum GradeSpCheck {

    /**
     * 校验
     */
    YES("1"),
    /**
     * 不校验
     */
    NO("2"),
    ;

    private String state;


    GradeSpCheck(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}