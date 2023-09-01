package com.sydata.trade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 损益单信息
 *
 * @author lzq
 * @date 2022/8/19 9:37
 */
@ApiModel(description = "粮油购销-损溢单信息")
@Data
@TableName(value = "trade_loss")
public class Loss extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "损溢单号(由货位代码+业务日期 （yyyyMMdd）+3 位顺序号组成)")
    private String sydh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "业务日期")
    private LocalDate ywrq;

    @ApiModelProperty(value = "入库净重")
    private BigDecimal rkjz;

    @ApiModelProperty(value = "入库时间")
    private LocalDateTime rksj;

    @ApiModelProperty(value = "入库水分")
    private BigDecimal rksf;

    @ApiModelProperty(value = "入库杂质")
    private BigDecimal rkzz;

    @ApiModelProperty(value = "出库时间")
    private LocalDateTime cksj;

    @ApiModelProperty(value = "出库净重")
    private BigDecimal ckjz;

    @ApiModelProperty(value = "出库水分")
    private BigDecimal cksf;

    @ApiModelProperty(value = "出库杂质")
    private BigDecimal ckzz;

    @ApiModelProperty(value = "净重损溢数量")
    private BigDecimal jzsysl;

    @ApiModelProperty(value = "其中水杂减量")
    private BigDecimal qzszkl;

    @ApiModelProperty(value = "其中：自然损耗定额")
    private BigDecimal qzzrshde;

    @ApiModelProperty(value = "超耗数量")
    private BigDecimal chsl;

    @ApiModelProperty(value = "损益是否正常:0：正常 1：不正常")
    private String sysfzc;

    @ApiModelProperty(value = "损溢原因")
    private String syyy;

    @ApiModelProperty(value = "仓储审核人")
    private String ccshr;

    @ApiModelProperty(value = "质检审核人")
    private String zjshr;

    @ApiModelProperty(value = "统计审核人")
    private String tjshr;

    @ApiModelProperty(value = "会计审核人")
    private String kjshr;

    @ApiModelProperty(value = "领导审核人")
    private String ldshr;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}