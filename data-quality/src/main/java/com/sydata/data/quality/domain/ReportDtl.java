package com.sydata.data.quality.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 数据质量-报告明细对象 data_quality_report_dtl
 *
 * @author system
 * @date 2023-04-18
 */
@ApiModel(description = "数据质量-报告明细")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("data_quality_report_dtl")
public class ReportDtl implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "数据质量报告ID")
    private String reportId;

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "联通情况:1是 0否")
    private Boolean unicom;

    @ApiModelProperty(value = "上报次数")
    private Integer apiRequestTotalCount;

    @ApiModelProperty(value = "上报成功次数")
    private Integer apiRequestSuccessCount;

    @ApiModelProperty(value = "数据总条数")
    private Integer dataTotalCount;

    @ApiModelProperty(value = "数据合格条数")
    private Integer dataGoodCount;

    @ApiModelProperty(value = "数据问题条数")
    private Integer dataIssueCount;

    @ApiModelProperty(value = "问题总数(一条数据可能存在多个问题)")
    private Integer issueTotalCount;

    @ApiModelProperty(value = "字段总数")
    private Integer fieldTotalCount;

    @ApiModelProperty(value = "字段上传数(不为空的字段数)")
    private Integer fieldValidCount;

    @ApiModelProperty(value = "通过率(上报次数/上报成功次数)")
    private BigDecimal passRate;

    @ApiModelProperty(value = "完整率(字段上传数/字段总数)")
    private BigDecimal fullRate;

    @ApiModelProperty(value = "合格率(数据合格条数/数据总条数)")
    private BigDecimal goodRate;
}