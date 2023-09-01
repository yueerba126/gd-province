package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 操作状态
 * @date 2023/5/18 15:25
 */
public enum GradeOpertionEnum {

    /**
     * 审批
     */
    S("1"),
    /**
     * 实地
     */
    SD("2"),
    /**
     * 授牌
     */
    SP("3")
    ;

    private String state;


    GradeOpertionEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
