package com.sydata.collect.api.enums;

/**
 * @author lzq
 * @description 文件分类枚举
 * @date 2022/10/21 10:56
 */
public enum FileClassificationEnum {

    /**
     * 库区鸟瞰图
     */
    AERIAL_VIEW_IMG("1", "库区鸟瞰图"),

    /**
     * 出入库图片
     */
    OUT_IN_IMG("2", "出入库图片"),

    /**
     * 入库检斤视频
     */
    IN_VIDEO("3", "入库检斤视频"),

    /**
     * 出库检斤视频
     */
    OUT_VIDEO("4", "出库检斤视频"),

    ;

    private final String classification;
    private final String msg;

    FileClassificationEnum(String classification, String msg) {
        this.classification = classification;
        this.msg = msg;
    }

    public String getClassification() {
        return classification;
    }

    public String getMsg() {
        return msg;
    }
}
