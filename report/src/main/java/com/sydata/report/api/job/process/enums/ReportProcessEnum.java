package com.sydata.report.api.job.process.enums;

/**
 * @author lzq
 * @description 上报处理枚举
 * @date 2022/11/2 14:36
 */
public enum ReportProcessEnum {

    /**
     * 执行上报
     */
    INVOKER(1, "执行上报"),

    /**
     * 记录上报日志
     */
    RECORD_LOG(2, "记录上报日志"),

    ;


    private int order;

    private String msg;

    ReportProcessEnum(int order, String msg) {
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
