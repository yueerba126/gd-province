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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * 粮食储备-轮换计划主表
 *
 * @author lzq
 * @date 2022-04-26
 */
@ApiModel(value = "ApparitorRotationPlan对象", description = "粮食储备-轮换计划主表")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("apparitor_rotation_plan")
public class ApparitorRotationPlan extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID(轮换计划单号)")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @ApiModelProperty(value = "开始执行日期")
    private LocalDate kszxrq;

    @ApiModelProperty(value = "截止执行日期")
    private LocalDate jzzxrq;

    @ApiModelProperty(value = "计划下达单位")
    private String jhxddw;

    @ApiModelProperty(value = "计划下达单位名称")
    private String jhxddwmc;

    @ApiModelProperty(value = "计划下达时间")
    private LocalDateTime jhxdsj;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
