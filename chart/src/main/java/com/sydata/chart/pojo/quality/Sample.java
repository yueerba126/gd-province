package com.sydata.chart.pojo.quality;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 质检报告卡片-样品信息
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "质检报告卡片-样品信息")
public class Sample {

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;

    @DataBindWarehouse
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓房(或油罐)名称")
    private String cfmc;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @DataBindCargo(dataValue = "#bgy")
    @ApiModelProperty(value = "保管员")
    private String bgy;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "仓内粮食数量(吨)")
    private BigDecimal cnlssl;

    @ApiModelProperty(value = "产地")
    private String cd;

    @DataBindRegion(sourceField = "#cd")
    @ApiModelProperty("产地名称")
    private String cdName;

    @ApiModelProperty(value = "收货年度")
    private String shnd;

    @ApiModelProperty(value = "样品数量")
    private BigDecimal ypsl;

    @ApiModelProperty(value = "代表数量")
    private BigDecimal dbsl;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @JsonIgnore
    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm", dataValue = "#dictTopKey")
    @ApiModelProperty(value = "粮食品种顶级父类Key")
    private String lspzdmTopkey;
}
