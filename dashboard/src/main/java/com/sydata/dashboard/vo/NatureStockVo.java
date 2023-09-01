package com.sydata.dashboard.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sydata.common.basis.annotation.DataBindDict;
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
 * 品种类别库存统计VO
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "品种类别库存统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NatureStockVo implements Serializable {

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "计价数量万吨")
    private BigDecimal jjsl;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "计价数量万吨（油）")
    private BigDecimal jjslOil;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "计价数量万吨（粮）")
    private BigDecimal jjslGrain;

    @ApiModelProperty(value = "粮食性质")
    private String lsxzdm;

    @ApiModelProperty(value = "粮食性质备注")
    @DataBindDict(sourceField = "#lsxzdm", sourceFieldCombination = "food_nature", dataValue = "#remark")
    private String lsxzRemark;

    @Excel(name = "市ID")
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @DataBindRegion(sourceField = "#cityId")
    @ApiModelProperty(value = "市名称")
    private String cityName;

    @Excel(name = "区ID")
    @ApiModelProperty(value = "区ID")
    private String areaId;

    @DataBindRegion(sourceField = "#areaId")
    @ApiModelProperty(value = "区名称")
    private String areaName;


}
