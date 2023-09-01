package com.sydata.dostrict.reserve.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * 粮食储备-储备计划对象
 *
 * @author lzq
 * @date 2022-04-26
 */
@ApiModel(description = "粮食储备-储备计划")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "apparitor_reserve_plan")
public class ApparitorReservePlan extends BaseFiledEntity implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id(计划单号)")
    private String id;

    @ApiModelProperty(value = "计划单号")
    private String jhdh;

    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @ApiModelProperty(value = "制定计划单位")
    private String jhzddw;

    @ApiModelProperty(value = "计划下达时间")
    private LocalDateTime jhxdsj;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "数量（吨）")
    private BigDecimal sl;

    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}