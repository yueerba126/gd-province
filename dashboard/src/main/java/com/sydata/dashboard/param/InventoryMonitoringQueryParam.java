package com.sydata.dashboard.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 粮食实时库存监督查询参数
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "粮食实时库存监督查询参数")
public class InventoryMonitoringQueryParam {

    @ApiModelProperty(value = "粮食品种代码 字典：food_big_variety")
    private String ylpz;

}
