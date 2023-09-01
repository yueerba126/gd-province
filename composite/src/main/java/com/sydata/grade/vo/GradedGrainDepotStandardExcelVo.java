package com.sydata.grade.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.sydata.common.basis.annotation.DataBindDict;
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
 * @program: gd-province-platform
 * @description:
 * @author: lzq
 * @create: 2023-05-17 19:41
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "等级粮库评定标准import")
public class GradedGrainDepotStandardExcelVo implements Serializable {

    @ApiModelProperty(name = "scoringMethodId", value = "模板树叶子节点ID")
    private String scoringMethodId;

    @ApiModelProperty(name = "projectSort", value = "字段顺序")
    private int projectSort;

    @Excel(name = "项目", needMerge = true)
    private String project;

    @Excel(name = "大项题目", needMerge = true)
    private String subject;

    @Excel(name = "大项分值", type = 10, needMerge = true)
    private BigDecimal maxScore;

    @Excel(name = "评定内容", needMerge = true)
    private String content;

    // @Excel(name = "加分指标")
    private String jfzb;

    @DataBindDict(sourceField = "#jfzb", sourceFieldCombination = "grade_jfzb")
    private String jfzbName;

    @Excel(name = "小项分值", type = 10)
    private BigDecimal minScore;

    @Excel(name = "计分方法")
    private String method;

    @Excel(name = "得分")
    private BigDecimal score;
}
