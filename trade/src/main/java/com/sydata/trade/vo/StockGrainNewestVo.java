package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.trade.domain.StockGrainNewest;
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
 * @description 粮油购销-粮食库存最新数据VO
 * @date 2023/4/21 10:28
 */
@ApiModel(description = "粮油购销-粮食库存最新数据VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockGrainNewestVo extends StockGrainNewest implements Serializable {

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
    @ApiModelProperty("货位名称")
    private String hwmc;

    @DataBindRegion(sourceField = "#cd")
    @ApiModelProperty("产地名称")
    private String cdName;

    @DataBindRegion(sourceField = "#cityId")
    @ApiModelProperty("地市")
    private String cityName;

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

    @DataBindCargo(dataValue = "#ajdm")
    @ApiModelProperty("廒间代码")
    private String ajdm;

    @DataBindGranary(dataValue = "#cfbh")
    @ApiModelProperty("仓房编号")
    private String cfdm;

    @DataBindWarehouse()
    @ApiModelProperty("仓房名称")
    private String cfmc;

}
