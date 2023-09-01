package com.sydata.safe.asess.enums;

/**
 * @author lzq
 * @description 安全考核状态枚举
 * @date 2023/2/13 15:25
 */
public enum SafeAssessStateEnum {
    /**
     * 保存
     */
    SAVE("保存"),

    /**
     * 待分配
     */
    AWAIT_ALLOT("待分配"),

    /**
     * 已分配
     */
    ALLOT("已分配"),

    /**
     * 待发布
     */
    AWAIT_PUSH("待发布"),

    /**
     * 已发布
     */
    PUSH("已发布"),

    /**
     * 待自评
     */
    AWAIT_SELF_ASSESSMENT("待自评"),

    /**
     * 已自评
     */
    SELF_ASSESSMENT("已自评"),

    /**
     * 待提交
     */
    AWAIT_SUBMIT("待提交"),

    /**
     * 待评分
     */
    AWAIT_SCORE("待评分"),

    /**
     * 已评分
     */
    SCORE("已评分"),

    /**
     * 待考核
     */
    AWAIT_ASSESS("待考核"),

    /**
     * 已考核
     */
    ASSESS("已考核"),

    /**
     * 已退回
     */
    RESET("已退回"),

    ;

    private String state;


    SafeAssessStateEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
