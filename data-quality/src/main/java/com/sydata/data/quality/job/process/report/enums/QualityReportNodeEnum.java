package com.sydata.data.quality.job.process.report.enums;

/**
 * @author lzq
 * @description 数据质量报告节点枚举
 * @date 2023/5/8 14:40
 */
public enum QualityReportNodeEnum {

    /**
     * 库区
     */
    STOCK_HOUSE(1, "库区"),


    /**
     * 行政区划
     */
    REGION(2, "行政区划"),


    ;

    private int order;

    private String msg;

    QualityReportNodeEnum(int order, String msg) {
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
