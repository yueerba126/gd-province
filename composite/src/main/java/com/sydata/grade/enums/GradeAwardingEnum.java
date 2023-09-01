package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 授牌状态
 * @date 2023/5/18 15:25
 */
public enum GradeAwardingEnum {

    /**
     * 待授牌
     */
    WAIT("0"),
    /**
     * 授牌通过
     */
    PASS("1"),
    /**
     * 授牌不通过
     */
    NO_PASS("2"),
    ;

    private String state;


    GradeAwardingEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
