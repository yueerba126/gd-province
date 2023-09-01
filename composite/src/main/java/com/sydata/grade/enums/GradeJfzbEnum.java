package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 加分指标
 * @date 2023/5/18 15:25
 */
public enum GradeJfzbEnum {

    /**
     * 是
     */
    YES("是"),
    /**
     * 否
     */
    NO("否"),
    /**
     * 是
     */
    YES_CODE("1"),
    /**
     * 否
     */
    NO_CODE("2"),
    ;

    private String state;


    GradeJfzbEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
