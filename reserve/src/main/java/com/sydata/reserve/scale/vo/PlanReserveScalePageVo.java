package com.sydata.reserve.scale.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 储备计划管理-储备规模Vo
 *
 * @author fuql
 * @date 2022-08-19
 */
@ApiModel(description = "储备计划管理-储备规模Vo")
@Data
@ToString
@Accessors(chain = true)
public class PlanReserveScalePageVo implements Serializable {

    @ApiModelProperty(value = "粮食储备规模总量")
    private BigDecimal grainTotal;

    @ApiModelProperty(value = "油储备规模总量")
    private BigDecimal oilTotal;

    @ApiModelProperty(value = "储备规模总量")
    private Page<PlanReserveScaleVo> scaleVo;
}
