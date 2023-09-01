package com.sydata.dashboard.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.dashboard.domain.StockAffiliation;
import com.sydata.framework.core.jackson.format.BigDecimalFormat;
import com.sydata.framework.core.jackson.serialize.BigDecimalSerialize;
import com.sydata.organize.annotation.DataBindRegion;
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
 * @author xy
 * @describe 报表管理-库存归属报表VO
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "报表管理-库存归属报表VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockAffiliationVo extends StockAffiliation implements Serializable {

    @ApiModelProperty(value = "粮食性质名称")
    @DataBindDict(sourceField = "#lsxzdm", sourceFieldCombination = "food_nature")
    private String lsxzName;

    @ApiModelProperty(value = "粮食性质备注")
    @DataBindDict(sourceField = "#lsxzdm", sourceFieldCombination = "food_nature", dataValue = "#remark")
    private String lsxzRemark;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String stockHouseName;

    @DataBindRegion(sourceField = "#lqxzqhdm")
    @ApiModelProperty(value = "粮权归属地名称")
    private String lqgsdName;

    @DataBindRegion(sourceField = "#provinceId")
    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @DataBindRegion(sourceField = "#cityId")
    @ApiModelProperty(value = "市名称")
    private String cityName;

    @DataBindRegion(sourceField = "#areaId")
    @ApiModelProperty(value = "区名称")
    private String areaName;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @Override
    public BigDecimal getSjsl() {
        return super.getSjsl();
    }
}
