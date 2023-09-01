package com.sydata.dashboard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lzq
 * @description 报表管理-库存归属报表统计VO
 * @date 2023/4/28 13:40
 */
@ApiModel(description = "报表管理-库存归属报表统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockAffiliationStatisticsVo implements Serializable {

    @ApiModelProperty(value = "储备总库存统计")
    private NatureStockVo provinceTotalStock;

    @ApiModelProperty(value = "市储库存统计")
    private List<NatureStockVo> cityStocks;

    @ApiModelProperty(value = "县储库存统计")
    private List<NatureStockVo> areaStocks;

    @ApiModelProperty(value = "库区库存统计")
    private List<StockAffiliationVo> stockHouseStocks;

    @ApiModelProperty(value = "粮食品种类别库存统计")
    private List<VarietyStockVo> varietyStocks;

    @ApiModelProperty(value = "性质库存统计")
    private List<NatureStockVo> natureStocks;

    @ApiModelProperty(value = "油品种类别库存统计")
    private List<VarietyStockVo> oilVarietyStocks;
}
