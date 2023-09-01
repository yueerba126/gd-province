package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 实地状态
 * @date 2023/5/18 15:25
 */
public enum GradeSdztEnum {

    /**
     * 待实地检查
     */
    WAIT("1"),
    /**
     * 实地检查通过
     */
    PASS("2"),
    /**
     * 实地检查不通过
     */
    NO_PASS("3"),
    ;

    private String state;


    GradeSdztEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
