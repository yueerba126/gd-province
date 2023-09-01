package com.sydata.reserve.manage.enums;

import com.sydata.framework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 计划单据审核状态
 *
 * @author fuql
 * @date 2023/05/23
 */
public enum ManageBillStatusEnum {

    /**
     * 待审
     */
    AUDIT("audit", "待审"),
    /**
     * 审核通过
     */
    AUDIT_PASS("audit_pass", "审核通过"),

    /**
     * 审核不通过
     */
    AUDIT_FAILED("audit_failed", "审核不通过"),

    ;
    private final String statusCode;
    private final String statusName;

    ManageBillStatusEnum(String statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusCode() {
        return statusCode;
    }


    public static ManageBillStatusEnum valueOfByStatusCode(String statusCode) {
        if (StringUtils.isBlank(statusCode)) {
            return null;
        }
        Optional<ManageBillStatusEnum> findAny = Arrays.stream(ManageBillStatusEnum.values())
                .filter(v -> v.getStatusCode().equals(statusCode)).findAny();
        if (!findAny.isPresent()) {
            return null;
        }
        return findAny.get();
    }
}
