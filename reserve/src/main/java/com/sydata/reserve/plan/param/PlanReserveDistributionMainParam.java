package com.sydata.reserve.plan.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 储备计划管理-储备计划主表下发参数
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划主表下发参数")
public class PlanReserveDistributionMainParam implements Serializable {

    @ApiModelProperty(value = "省平台计划ID")
    private String id;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "计划文号")
    @NotBlank(message = "计划文号必填")
    @ApiModelProperty(value = "计划文号")
    private String planCode;

    @Excel(name = "计划名称")
    @ApiModelProperty(value = "计划名称")
    private String planName;

    @Excel(name = "粮食性质")
    @ApiModelProperty(value = "粮食性质")
    private String ylxz;

    @Excel(name = "承储总数量（吨）")
    @ApiModelProperty(value = "承储总数量（吨）")
    private BigDecimal amount;

    @Excel(name = "计划开始日期")
    @ApiModelProperty(value = "计划开始日期")
    private String beginDate;

    @Excel(name = "计划结束日期")
    @ApiModelProperty(value = "计划结束日期")
    private String endDate;

    @Excel(name = "承储企业")
    @ApiModelProperty(value = "承储企业")
    private String ccqy;

    @Excel(name = "状态")
    @ApiModelProperty(value = "状态")
    private String billStatus;

    @Excel(name = "是否发生调整")
    @ApiModelProperty(value = "是否发生调整")
    private String isChange;

    @ApiModelProperty(value = "计划下达时间")
    private String jhxdsj;

    @ApiModelProperty(value = "年份如2022")
    private String jhnd;

    @ApiModelProperty(value = "制定计划单位")
    private String jhzddw;

    @ApiModelProperty(value = "计划单号")
    @NotBlank(message = "计划单号必填")
    private String jhdh;

    @ApiModelProperty("轮换类型")
    private String mainRotateType;
}
