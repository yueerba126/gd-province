package com.sydata.collect.api.enums;

import one.util.streamex.StreamEx;

import java.util.Map;

import static java.util.function.Function.identity;

/**
 * @author lzq
 * @description 文件类型枚举
 * @date 2022/10/21 10:08
 */
public enum FileTypeEnum {

    /**
     * 库区鸟瞰图
     */
    AERIAL_VIEW_IMG("1", "库区鸟瞰图", "jpg"),

    /**
     * 入库图片
     */
    IN_IMG("2", "入库图片", "jpg"),

    /**
     * 出库图片
     */
    OUT_IMG("3", "出库图片", "jpg"),

    /**
     * 入库检斤视频
     */
    IN_MP4("4", "入库检斤视频", "mp4"),

    /**
     * 出库检斤视频
     */
    OUT_MP4("5", "出库检斤视频", "mp4"),

    ;

    private static Map<String, FileTypeEnum> TYPE_MAP = StreamEx.of(values()).toMap(FileTypeEnum::getType, identity());

    private final String type;
    private final String msg;
    private final String fileType;

    FileTypeEnum(String type, String msg, String fileType) {
        this.type = type;
        this.msg = msg;
        this.fileType = fileType;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public String getFileType() {
        return fileType;
    }

    /**
     * 根据文件类型获取枚举
     *
     * @param type 文件类型
     * @return 文件类型枚举
     */
    public static FileTypeEnum getByType(String type) {
        return TYPE_MAP.get(type);
    }
}
