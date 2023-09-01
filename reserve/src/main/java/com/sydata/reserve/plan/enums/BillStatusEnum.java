package com.sydata.reserve.plan.enums;

import com.sydata.framework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 计划单据状态
 *
 * @author fuql
 * @date 2023/05/23
 */
public enum BillStatusEnum {
    /**
     * 保存
     */
    SAVE("save", "拟稿"),

    /**
     * 待下发
     */
    TO_BE_ISSUED("to_be_issued", "待下发"),

    /**
     * 调整待下发
     */
    ADJUST_BE_ISSUED("adjust_be_issued", "调整待下发"),

    /**
     * 已下发
     */
    DISTRIBUTION("distribution", "已下发"),

    /**
     * 下发失败
     */
    DISTRIBUTION_FAILED("distribution_failed", "下发失败"),

    /**
     * 完成
     */
    COMPLETE("complete", "完成"),

    /**
     * 废止
     */
    ABOLISH("abolish", "废止"),


    ;
    private final String statusCode;
    private final String statusName;

    BillStatusEnum(String statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusCode() {
        return statusCode;
    }


    public static BillStatusEnum valueOfByStatusCode(String statusCode) {
        if (StringUtils.isBlank(statusCode)) {
            return null;
        }
        Optional<BillStatusEnum> findAny = Arrays.stream(BillStatusEnum.values())
                .filter(v -> v.getStatusCode().equals(statusCode)).findAny();
        if (!findAny.isPresent()) {
            return null;
        }
        return findAny.get();
    }
}
