package com.sydata.monitoring.enums;

/**
 * 流通监测审核状态
 *
 * @author zhangcy
 * @date 2023/4/25 17:34
 */
public enum MonitoringAuditStatusEnum {
    /**
     * 暂定这三个
     */
    PENDING_AUDIT("待审"),
    PASS("审核通过"),
    NOT_PASS("审核不通过"),
    ;

    private final String name;

    MonitoringAuditStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
