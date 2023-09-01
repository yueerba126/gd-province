package com.sydata.monitoring.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 流通检测-粮油购销信息
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("monitoring_statistics_purchase_sale")
@ApiModel(value="MonitoringStatisticsPurchaseSale对象", description="流通检测-粮油购销信息")
public class MonitoringStatisticsPurchaseSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "粮油流通统计ID")
    private String statisticId;

    @ApiModelProperty(value = "单据类型")
    private String purchaseSaleType;

    @ApiModelProperty(value = "单据编号")
    private String billCode;

    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "数量")
    private BigDecimal qty;

    @ApiModelProperty(value = "物料品种")
    private String materialType;

    @ApiModelProperty(value = "含税金额")
    private BigDecimal taxAmount;

    @ApiModelProperty(value = "基本单位")
    private String baseUnit;

    @ApiModelProperty(value = "基本数量")
    private BigDecimal baseQty;

    @ApiModelProperty(value = "规格型号")
    private String spec;

    @ApiModelProperty(value = "添加人员")
    private String createBy;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @ApiModelProperty(value = "国ID")
    private String countryId;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;


}
