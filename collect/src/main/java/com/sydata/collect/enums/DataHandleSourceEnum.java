package com.sydata.collect.enums;

/**
 * @author lzq
 * @description 数据处理来源枚举
 * @date 2022/11/2 9:21
 */
public enum DataHandleSourceEnum {


    /**
     * 数据收集
     */
    COLLECT("数据收集"),


    /**
     * 审批通过
     */
    APPROVAL("审批通过"),


    /**
     * 上报失败投递
     */
    REPORT_DELIVERY("上报失败投递"),


    ;


    private String source;


    DataHandleSourceEnum(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
