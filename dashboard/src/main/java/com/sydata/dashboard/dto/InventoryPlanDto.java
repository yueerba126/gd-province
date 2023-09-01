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
public class InventoryPlanDto implements Serializable {

    @ApiModelProperty(value = "粮食品种类别")
    private String lspzlb;

    @ApiModelProperty(value = "粮食品种类别")
    private String lspzlbmc;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市Id")
    private String cityId;

    @ApiModelProperty(value = "计划储备数量")
    private BigDecimal sjsl;

}
