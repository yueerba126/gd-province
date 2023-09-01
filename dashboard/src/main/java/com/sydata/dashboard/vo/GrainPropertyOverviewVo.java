package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.dashboard.domain.StockAffiliation;
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
 * @describe 全省库存量总览（万吨）-粮权VO
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "全省库存量总览（万吨）-粮权VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GrainPropertyOverviewVo  implements Serializable {

    @ApiModelProperty(value = "粮食性质名称")
    @DataBindDict(sourceField = "#lsxzdm", sourceFieldCombination = "food_nature",dataValue = "#remark")
    private String lsxzName;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;



    @ApiModelProperty(value = "实际数量(万吨)")
    private BigDecimal sjsl;

}
