package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 流程类型
 * @date 2023/5/18 15:25
 */
public enum GradeFlowNodeEnum {

    /**
     * 库点申报
     */
    A("1"),
    /**
     * 企业内审
     */
    B("2"),
    /**
     * 县粮食局审核
     */
    C("3"),
    /**
     * 市粮食局审核
     */
    D("4"),
    /**
     * 市级实地核查
     */
    E("5"),
    /**
     * 省粮食局审核
     */
    F("6"),
    /**
     * 省级实地核查
     */
    G("7"),
    /**
     * 通过授牌
     */
    H("8"),
    ;

    private String state;


    GradeFlowNodeEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
