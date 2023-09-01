package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 流程类型
 * @date 2023/5/18 15:25
 */
public enum GradeSbztEnum {

    /**
     * 保存
     */
    SAVE("1"),
    /**
     * 待审核
     */
    WAIT("2"),
    /**
     * 审核不通过
     */
    NO_PASS("3"),
    /**
     * 已申报
     */
    REPORT("4"),
    /**
     * 授牌
     */
    PASS("5"),
    ;

    private String state;


    GradeSbztEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
