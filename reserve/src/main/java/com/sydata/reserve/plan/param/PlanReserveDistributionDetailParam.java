package com.sydata.reserve.plan.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 储备计划管理-储备计划新增参数
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划新增详情参数")
public class PlanReserveDistributionDetailParam implements Serializable {

    @ApiModelProperty(value = "省平台计划详情ID")
    private String id;

    @Excel(name = "主表ID")
    @ApiModelProperty(value = "主表ID")
    private String mainId;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "粮食品种")
    @ApiModelProperty(value = "粮食品种")
    private String ylpz;

    @Excel(name = "承储数量（吨）")
    @ApiModelProperty(value = "承储数量（吨）")
    private BigDecimal amount;

    @Excel(name = "承储质量要求")
    @ApiModelProperty(value = "承储质量要求")
    private String remark;

    @Excel(name = "状态")
    @ApiModelProperty(value = "状态")
    private String billStatus;
}
