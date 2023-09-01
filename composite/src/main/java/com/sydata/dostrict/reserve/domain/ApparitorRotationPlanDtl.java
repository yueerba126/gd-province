package com.sydata.dostrict.reserve.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * 粮食储备-轮换计划明细表
 *
 * @author lzq
 * @date 2022-04-26
 */
@ApiModel(value = "ApparitorRotationPlanDtl对象", description = "粮食储备-轮换计划明细表")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("apparitor_rotation_plan_dtl")
public class ApparitorRotationPlanDtl extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID(计划明细单号)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "计划明细单号")
    private String jhmxdh;

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "收获年度")
    private String shnd;

    @ApiModelProperty(value = "轮换货位代码")
    private String lhhwdm;

    @ApiModelProperty(value = "轮换数量")
    private BigDecimal lhsl;

    @ApiModelProperty(value = "轮换类型(1.轮出 2.轮入)")
    private String lhlx;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
