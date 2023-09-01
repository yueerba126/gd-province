package com.sydata.dashboard.param;

import com.sydata.framework.util.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

/**
 * 储备粮实物库存报表参数
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "报表管理-库存归属报表分页参数")
public class PhysicalInventoryExportParam {

    @ApiModelProperty(value = "地市编码列表，用‘,’分割")
    private String city;

    @ApiModelProperty(value = "库区ID，用‘,’分割")
    private String stockHouseId;

    @ApiModelProperty(value = "地市编码列表，用‘,’分割", hidden = true)
    private List<String> cityList;

    @ApiModelProperty(value = "库区ID，用‘,’分割", hidden = true)
    private List<String> stockHouseIdList;

    public List<String> getCityList() {
        if (StringUtils.isNotEmpty(city)) {
            return Arrays.asList(city.split(","));
        }
        return cityList;
    }

    public List<String> getStockHouseIdList() {
        if (StringUtils.isNotEmpty(stockHouseId)) {
            return Arrays.asList(stockHouseId.split(","));
        }
        return stockHouseIdList;
    }
}
