package com.sydata.reserve.plan.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 储备计划管理-储备计划调整记录下发参数
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划调整记录下发参数")
public class PlanReserveDistributionLogParam implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @Excel(name = "主表ID")
    @ApiModelProperty(value = "主表ID")
    private String mainId;

    @Excel(name = "详情表id")
    @ApiModelProperty(value = "详情表id")
    private String detailId;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "粮食品种")
    @ApiModelProperty(value = "粮食品种")
    private String ylpz;

    @Excel(name = "粮食等级代码")
    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @Excel(name = "承储数量（吨）")
    @ApiModelProperty(value = "承储数量（吨）")
    private BigDecimal amount;

    @Excel(name = "承储质量要求")
    @ApiModelProperty(value = "承储质量要求")
    private String remark;


}
