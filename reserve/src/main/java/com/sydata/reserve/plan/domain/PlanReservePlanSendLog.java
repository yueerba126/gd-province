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
import java.time.LocalDateTime;

/**
 * 储备计划管理-储备计划下发记录对象 plan_reserve_plan_send_log
 *
 * @author fuql
 * @date 2023-05-23
 */
@ApiModel(description = "储备计划管理-储备计划下发记录")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("plan_reserve_plan_send_log")
public class PlanReservePlanSendLog extends BaseFiledEntity implements Serializable {

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

    @Excel(name = "下发时间")
    @ApiModelProperty(value = "下发时间")
    private LocalDateTime sendTime;

    @Excel(name = "下发返回值")
    @ApiModelProperty(value = "下发返回值")
    private String sendLog;

    @Excel(name = "下发状态")
    @ApiModelProperty(value = "下发状态 字典：send_status")
    private String sendStatus;
}