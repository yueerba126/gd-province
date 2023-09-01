package com.sydata.dashboard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 粮食实时库存监督（万吨）
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "粮食实时库存监督（万吨）")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InventoryMonitoringVo implements Serializable {

    @ApiModelProperty(value = "粮食品种代码 字典：food_big_variety")
    private String ylpz;

    @ApiModelProperty(value = "粮食品种名称")
    private String ylpzName;

    @ApiModelProperty(value = "计划量")
    private InventoryMonitoringCityVo plannedQuantity;

    @ApiModelProperty(value = "实际量")
    private InventoryMonitoringCityVo realityQuantity;

    @ApiModelProperty(value = "储存完成率(%)")
    private InventoryMonitoringCityVo completionRate;

}
