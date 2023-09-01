package com.sydata.monitoring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 流通检测-粮油价格采集明细表
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("monitoring_grain_oil_price_dtl")
@ApiModel(value="GrainOilPriceDtl对象", description="流通检测-粮油价格采集明细表")
public class GrainOilPriceDtl extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "主表ID")
    private Long mainId;

    @ApiModelProperty(value = "单据编号")
    private String billCode;

    @ApiModelProperty(value = "购销类型：销售/采购")
    private String salePurchaseType;

    @ApiModelProperty(value = "监测日期")
    private LocalDate monitorDate;

    @ApiModelProperty(value = "单据日期")
    private LocalDate billDate;

    @ApiModelProperty(value = "波动情况")
    private BigDecimal volatility;

    @ApiModelProperty(value = "数量")
    private BigDecimal qty;

    @ApiModelProperty(value = "基本数量")
    private BigDecimal bastQty;

    @ApiModelProperty(value = "基本单位")
    private BigDecimal bastUnit;

    @ApiModelProperty(value = "含税单价")
    private BigDecimal taxPrice;

    @ApiModelProperty(value = "企业单位代码")
    private String dwdm;

    @ApiModelProperty(value = "仓库代码")
    private String cfdm;

    @ApiModelProperty(value = "廒间代码")
    private String ajdm;

    @ApiModelProperty(value = "堆位代码")
    private String hwdm;

    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "行政区划")
    private String administrativeDivisions;


}
