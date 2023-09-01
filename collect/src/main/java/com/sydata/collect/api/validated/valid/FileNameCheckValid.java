package com.sydata.collect.api.validated.valid;

import com.sydata.collect.api.enums.FileCategoryEnum;
import com.sydata.collect.api.enums.FileClassificationEnum;
import com.sydata.collect.api.enums.FileTypeEnum;
import com.sydata.collect.api.validated.annotation.FileNameCheck;
import com.sydata.framework.util.StringUtils;

import javax.validation.ConstraintValidatorContext;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.UNDERSCORE;
import static com.sydata.collect.api.enums.FileClassificationEnum.*;
import static com.sydata.collect.api.enums.FileTypeEnum.AERIAL_VIEW_IMG;
import static com.sydata.collect.api.enums.FileTypeEnum.*;

/**
 * @author lzq
 * @description 文件名校验类
 * @date 2022/10/21 10:17
 */
public class FileNameCheckValid extends BaseValid<FileNameCheck, String> {

    private static final int THREE = 3;

    private String wjlx;

    @Override
    public void initialize(FileNameCheck fileNameCheck) {
        wjlx = fileNameCheck.wjlx();
    }

    @Override
    public boolean isValid(String fileName, ConstraintValidatorContext constraintValidatorContext) {
        // 获取文件类型枚举
        FileTypeEnum fileTypeEnum = FileTypeEnum.getByType((String) super.getValByParam(wjlx));
        if (fileTypeEnum == null) {
            return false;
        }

        if (AERIAL_VIEW_IMG.equals(fileTypeEnum)) {
            // 库区鸟瞰图截取库点长度直接判断是不是PMT.jpg结尾
            return fileName.endsWith(FileCategoryEnum.PMT.getCategory());
        } else if (IN_IMG.equals(fileTypeEnum) || OUT_IMG.equals(fileTypeEnum)) {
            // 出入库图片
            return checkBillFile(fileName, OUT_IN_IMG);
        } else if (IN_MP4.equals(fileTypeEnum)) {
            // 入库视频
            return checkBillFile(fileName, IN_VIDEO);
        } else if (OUT_MP4.equals(fileTypeEnum)) {
            // 出库视频
            return checkBillFile(fileName, OUT_VIDEO);
        }

        return false;
    }

    /**
     * 校验单据文件名
     *
     * @param fileName       文件名
     * @param classification 文件分类枚举
     */
    private static boolean checkBillFile(String fileName, FileClassificationEnum classification) {
        // 校验必须为3段
        String[] split = fileName.split(UNDERSCORE);
        if (split.length != THREE) {
            return false;
        }

        // 校验业务单号不能为空
        String billCode = split[1];
        if (StringUtils.isBlank(billCode)) {
            return false;
        }

        // 校验文件分类码+扩展名是否符合规则
        FileCategoryEnum categoryEnum = FileCategoryEnum.getByCategory(split[2]);
        return FileCategoryEnum.getByClassification(classification).contains(categoryEnum);
    }
}
