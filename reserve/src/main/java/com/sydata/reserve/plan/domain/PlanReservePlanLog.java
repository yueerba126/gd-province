package com.sydata.reserve.plan.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
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
 * 储备计划管理-储备计划调整历史对象 plan_reserve_plan_log
 *
 * @author fuql
 * @date 2023-05-19
 */
@ApiModel(description = "储备计划管理-储备计划调整历史")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("plan_reserve_plan_log")
public class PlanReservePlanLog extends BaseFiledEntity implements Serializable {

    @Excel(name = "主键ID")
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
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
