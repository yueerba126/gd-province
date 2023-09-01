package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 流程类型
 * @date 2023/5/18 15:25
 */
public enum GradeFlowEnum {

    /**
     * 市级
     */
    CITY("grade_city_flow"),
    /**
     * 省级GradeFlowEnum
     */
    PROVINCE("grade_province_flow"),
    ;

    private String state;


    GradeFlowEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
