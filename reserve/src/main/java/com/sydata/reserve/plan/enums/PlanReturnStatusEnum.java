package com.sydata.reserve.plan.enums;

/**
 * 计划单据状态
 *
 * @author fuql
 * @date 2023/05/23
 */
public enum PlanReturnStatusEnum {
    /**
     * 下发成功
     */
    ONE("1", "下发成功！"),

    /**
     * 下发失败
     */
    TWO("2", "下发失败！"),



    ;
    private final String statusCode;
    private final String statusName;

    PlanReturnStatusEnum(String statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
