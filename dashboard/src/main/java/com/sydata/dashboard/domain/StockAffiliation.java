package com.sydata.dashboard.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 报表管理-库存归属报表对象 dashboard_grain_affiliation
 *
 * @author system
 * @date 2023-04-21
 */
@ApiModel(description = "报表管理-库存归属报表")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("dashboard_stock_affiliation")
public class StockAffiliation implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Excel(name = "粮食品种类别")
    @ApiModelProperty(value = "粮食品种类别")
    private String lspzlb;

    @Excel(name = "粮食品种代码")
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @Excel(name = "粮食性质代码")
    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @Excel(name = "实际数量(公斤)")
    @ApiModelProperty(value = "实际数量(公斤)")
    private BigDecimal sjsl;

    @Excel(name = "计价数量（公斤）")
    @ApiModelProperty(value = "计价数量（公斤）")
    private BigDecimal jjsl;

    @Excel(name = "粮权行政区划代码")
    @ApiModelProperty(value = "粮权行政区划代码")
    private String lqxzqhdm;

    @Excel(name = "行政区域ID")
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @Excel(name = "国ID")
    @ApiModelProperty(value = "国ID")
    private String countryId;

    @Excel(name = "省ID")
    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @Excel(name = "市ID")
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @Excel(name = "区ID")
    @ApiModelProperty(value = "区ID")
    private String areaId;

    @Excel(name = "企业ID")
    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @Excel(name = "库区ID")
    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;
}