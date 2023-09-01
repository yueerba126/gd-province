package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lzq
 * @description 粮食安全考核-考核评审分组VO
 * @date 2023/4/3 16:54
 */
@ApiModel(description = "粮食安全考核-考核评审分组VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReviewGroupVo implements Serializable {

    @ApiModelProperty(value = "考核评审ID")
    private String id;

    @ApiModelProperty(value = "分配日期")
    private LocalDate allotDate;

    @ApiModelProperty(value = "自动分配：1是 0否")
    private Boolean autoAllot;

    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @ApiModelProperty(value = "考核模板名称")
    private String templateName;

    @ApiModelProperty(value = "考核模板年份")
    private String templateYear;

    @ApiModelProperty(value = "状态：待分配、待发布、待提交、待评分、待考核、已考核")
    private String state;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "牵头指标总数")
    private Integer leadTotalCount;

    @ApiModelProperty(value = "牵头指标分配数")
    private Integer leadAllotCount;

    @ApiModelProperty(value = "配合指标总数")
    private Integer cooperateTotalCount;

    @ApiModelProperty(value = "配合指标分配数")
    private Integer cooperateAllotCount;

    @ApiModelProperty(value = "明细总行数")
    private Integer count;

    @ApiModelProperty(value = "待提交数量")
    private Integer awaitSubmitCount;

    @ApiModelProperty(value = "待评分数量")
    private Integer awaitScoreCount;

    @ApiModelProperty(value = "待考核数量")
    private Integer awaitAssessCount;

    @ApiModelProperty(value = "已考核数量")
    private Integer assessCount;

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @DataBindOrganize(sourceField = "#deptId")
    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
