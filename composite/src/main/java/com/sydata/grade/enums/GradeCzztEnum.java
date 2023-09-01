package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 操作状态
 * @date 2023/5/18 15:25
 */
public enum GradeCzztEnum {
    /**
     * 审核通过
     */
    PASS("1"),
    /**
     * 审核不通过
     */
    NO_PASS("2"),
    /**
     * 实地通过
     */
    INDEED("3"),
    /**
     * 实地不通过
     */
    NO_INDEED("4"),
    /**
     * 授牌通过
     */
    AWARDING("5"),
    /**
     * 授牌不通过
     */
    NO_AWARDING("6"),
    /**
     * 被降级
     */
    REDUCE("7"),
    /**
     * 被摘除
     */
    PICK("8"),
    ;

    private String state;


    GradeCzztEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
