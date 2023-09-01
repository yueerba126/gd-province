package com.sydata.safe.asess.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @description 单位考核导出明细VO
 * @date 2023/4/7 19:05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "单位考核导出明细VO")
public class OrgAssessExportDtlVo implements Serializable {

    @ApiModelProperty(value = "考核模板指标ID")
    private String templateIndexId;

    @Excel(name = "指标分类", width = 20, needMerge = true)
    private String parentNames;

    @Excel(name = "指标名称", width = 20, needMerge = true)
    private String name;

    @Excel(name = "指标总分", width = 20, needMerge = true)
    private BigDecimal score;

    @Excel(name = "是否缺项", width = 20, needMerge = true)
    private String enable;

    @Excel(name = "自评说明", width = 20, needMerge = true)
    private String orgIllustrate;

    @Excel(name = "考核评分", width = 20, needMerge = true)
    private BigDecimal assessScore;

    @Excel(name = "考核减分原因", width = 20, needMerge = true)
    private String assessMinusCause;

    @Excel(name = "抽查评分", width = 20, needMerge = true)
    private BigDecimal checkScore;

    @Excel(name = "抽查说明", width = 20, needMerge = true)
    private String checkRemark;
}
