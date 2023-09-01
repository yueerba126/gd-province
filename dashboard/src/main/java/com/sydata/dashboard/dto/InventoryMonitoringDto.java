package com.sydata.dashboard.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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
public class InventoryMonitoringDto implements Serializable {

    @ApiModelProperty(value = "粮食品种类别")
    private String lspzlb;

    @ApiModelProperty(value = "市Id")
    private String cityId;

    @ApiModelProperty(value = "实际数量")
    private BigDecimal sjsl;

}
