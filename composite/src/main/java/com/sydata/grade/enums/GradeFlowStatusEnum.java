package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 流程节点状态类型
 * @date 2023/5/18 15:25
 */
public enum GradeFlowStatusEnum {

    /**
     * 保存
     */
    SAVE("1"),
    /**
     * 待审核
     */
    WAIT("2"),
    /**
     * 审核通过
     */
    PASS("3"),
    /**
     * 审核不通过
     */
    NO_PASS("4"),
    ;

    private String state;


    GradeFlowStatusEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
