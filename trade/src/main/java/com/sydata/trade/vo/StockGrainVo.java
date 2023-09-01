package com.sydata.trade.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.trade.domain.StockGrain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 粮油购销-粮食库存VO
 * @date 2023/4/21 10:28
 */
@ApiModel(description = "粮油购销-粮食库存VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("trade_stock_grain_newest")
public class StockGrainVo extends StockGrain implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCompany(sourceField = "#lqgsdwdm")
    @ApiModelProperty(value = "粮权归属单位名称")
    private String lqgsdwdmName;

    @DataBindRegion(sourceField = "#lqxzqhdm")
    @ApiModelProperty(value = "粮权行政区划名称")
    private String lqxzqhdmName;

    @DataBindCargo
    @ApiModelProperty("货位代码")
    private String hwmc;

    @DataBindRegion(sourceField = "#cd")
    @ApiModelProperty("产地名称")
    private String cdName;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;

    @DataBindDict(sourceFieldCombination = "hwzt", sourceField = "#hwzt")
    @ApiModelProperty(value = "货位状态名称")
    private String hwztName;

    @DataBindDict(sourceFieldCombination = "glfs", sourceField = "#glfs")
    @ApiModelProperty(value = "管理方式名称")
    private String glfsName;

    @DataBindDict(sourceFieldCombination = "scdd", sourceField = "#scdd")
    @ApiModelProperty(value = "收储地点名称")
    private String scddName;

    @DataBindDict(sourceFieldCombination = "clfs", sourceField = "#clfs")
    @ApiModelProperty(value = "储粮方式名称")
    private String clfsName;

}
