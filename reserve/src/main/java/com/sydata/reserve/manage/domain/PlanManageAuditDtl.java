package com.sydata.reserve.manage.domain;

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
 * 轮换计划审核详情对象 plan_manage_audit_dtl
 *
 * @author fuql
 * @date 2023-05-31
 */
@ApiModel(description = "轮换计划审核详情")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("plan_manage_audit_dtl")
public class PlanManageAuditDtl extends BaseFiledEntity implements Serializable {

    @Excel(name = "轮换计划审核详情Id")
    @ApiModelProperty(value = "轮换计划审核详情Id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @Excel(name = "轮换计划审核id")
    @ApiModelProperty(value = "轮换计划审核id")
    private String mainId;

    @Excel(name = "库软件轮换计划审核id")
    @ApiModelProperty(value = "库软件轮换计划审核id")
    private String planMainId;

    @Excel(name = "库软件轮换计划详情id")
    @ApiModelProperty(value = "库软件轮换计划详情id")
    private String detailId;

    @Excel(name = "轮换计划单号")
    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @Excel(name = "轮换货位代码")
    @ApiModelProperty(value = "轮换货位代码")
    private String lhhwdm;

    @Excel(name = "粮食品种代码")
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @Excel(name = "粮食等级代码：字典food_grade")
    @ApiModelProperty(value = "粮食等级代码：字典food_grade")
    private String lsdjdm;

    @Excel(name = "粮食等级代码名称：字典food_grade")
    @ApiModelProperty(value = "粮食等级代码名称：字典food_grade")
    private String lsdjdmName;

    @Excel(name = "收货年度")
    @ApiModelProperty(value = "收货年度")
    private String shnd;

    @Excel(name = "轮换类型")
    @ApiModelProperty(value = "轮换类型")
    private String lhlx;

    @Excel(name = "轮换类型名称")
    @ApiModelProperty(value = "轮换类型名称")
    private String lhlxName;

    @Excel(name = "轮换数量")
    @ApiModelProperty(value = "轮换数量")
    private BigDecimal lhsl;

    @Excel(name = "执行数量（t）")
    @ApiModelProperty(value = "执行数量（t）")
    private BigDecimal carryQty;

    @Excel(name = "基准价(元/公斤)")
    @ApiModelProperty(value = "基准价(元/公斤)")
    private BigDecimal price;
}