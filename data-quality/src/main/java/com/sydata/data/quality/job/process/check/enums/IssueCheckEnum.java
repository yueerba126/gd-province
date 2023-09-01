package com.sydata.data.quality.job.process.check.enums;

/**
 * @author lzq
 * @description 数据质量问题检查处理枚举
 * @date 2023/4/22 18:51
 */
public enum IssueCheckEnum {

    /**
     * 数据校验
     */
    DATA_CHECK(1, "数据校验"),

    /**
     * 数据自定义校验
     */
    DATA_CUSTOM_CHECK(2, "数据自定义校验"),

    /**
     * 数据问题持久化
     */
    ISSUE_DATA_PERSISTENCE(3, "数据问题持久化"),

    /**
     * 数据质量报告统计
     */
    QUALITY_REPORT_STATISTICS(4, "数据质量报告统计"),

    ;

    private int order;

    private String msg;

    IssueCheckEnum(int order, String msg) {
        this.order = order;
        this.msg = msg;
    }

    public int getOrder() {
        return order;
    }

    public String getMsg() {
        return msg;
    }
}
