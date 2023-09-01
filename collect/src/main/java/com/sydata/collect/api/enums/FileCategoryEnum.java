
package com.sydata.collect.api.enums;

import one.util.streamex.StreamEx;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sydata.collect.api.enums.FileClassificationEnum.*;
import static java.util.function.Function.identity;

/**
 * @author lzq
 * @description 文件分类码+扩展名枚举
 * @date 2022/10/21 10:08
 */
public enum FileCategoryEnum {

    /**
     * 库区鸟瞰图
     */
    PMT("PMT.jpg", "库区鸟瞰图", AERIAL_VIEW_IMG),

    /**
     * 称毛重时前摄像头照片
     */
    GL("GL.jpg", "称毛重时前摄像头照片", OUT_IN_IMG),

    /**
     * 称毛重时后摄像头照片
     */
    GR("GR.jpg", "称毛重时后摄像头照片", OUT_IN_IMG),

    /**
     * 称毛重时顶摄像头照片
     */
    GT("GT.jpg", "称毛重时顶摄像头照片", OUT_IN_IMG),

    /**
     * 称皮重时前摄像头照片
     */
    TL("TL.jpg", "称皮重时前摄像头照片", OUT_IN_IMG),

    /**
     * 称皮重时后摄像头照片
     */
    TR("TR.jpg", "称皮重时后摄像头照片", OUT_IN_IMG),

    /**
     * 称皮重时顶摄像头照片
     */
    TT("TT.jpg", "称皮重时顶摄像头照片", OUT_IN_IMG),

    /**
     * 结算时摄像头拍取的售粮人照片
     */
    SL("SL.jpg", "结算时摄像头拍取的售粮人照片", OUT_IN_IMG),

    /**
     * 售粮人身份证照片
     */
    SF("SF.jpg", "售粮人身份证照片", OUT_IN_IMG),

    /**
     * 入库称毛重视频
     */
    IVG("IVG.mp4", "入库称毛重视频", IN_VIDEO),

    /**
     * 入库称皮重视频
     */
    IVT("IVT.mp4", "入库称皮重视频", IN_VIDEO),

    /**
     * 出库称毛重视频
     */
    OVG("OVG.mp4", "出库称毛重视频", OUT_VIDEO),

    /**
     * 出库称皮重视频
     */
    OVT("OVT.mp4", "出库称皮重视频", OUT_VIDEO),

    ;

    private static final Map<String, FileCategoryEnum> CATEGORY_MAP;
    private static final Map<FileClassificationEnum, Set<FileCategoryEnum>> CLASSIFICATION_MAP;

    static {
        CATEGORY_MAP = StreamEx.of(values()).toMap(FileCategoryEnum::getCategory, identity());
        CLASSIFICATION_MAP = StreamEx.of(values()).groupingBy(FileCategoryEnum::getClassification, Collectors.toSet());
    }


    private final String category;
    private final String msg;
    private final FileClassificationEnum classification;

    FileCategoryEnum(String category, String msg, FileClassificationEnum classification) {
        this.category = category;
        this.msg = msg;
        this.classification = classification;
    }

    public String getCategory() {
        return category;
    }

    public String getMsg() {
        return msg;
    }

    public FileClassificationEnum getClassification() {
        return classification;
    }

    /**
     * 根据文件分类码获取枚举
     *
     * @param category 文件分类码
     * @return 文件分类码枚举
     */
    public static FileCategoryEnum getByCategory(String category) {
        return CATEGORY_MAP.get(category);
    }

    /**
     * 根据文件分类枚举获取枚举集合
     *
     * @param classification 文件分类枚举
     * @return 文件分类码枚举集合
     */
    public static Set<FileCategoryEnum> getByClassification(FileClassificationEnum classification) {
        return CLASSIFICATION_MAP.get(classification);
    }
}
