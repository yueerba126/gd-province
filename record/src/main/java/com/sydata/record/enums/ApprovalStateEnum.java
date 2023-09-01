package com.sydata.record.enums;

/**
 * @author lzq
 * @description 审批状态枚举
 * @date 2022/12/10 9:50
 */
public enum ApprovalStateEnum {

    /**
     * 待审核
     */
    WAIT("1", "待审核"),

    /**
     * 审核通过
     */
    APPROVED("2", "审核通过"),

    /**
     * 退回
     */
    RETURN("3", "退回"),

    ;

    private final String state;
    private final String msg;

    ApprovalStateEnum(String state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
