package com.sydata.trade.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @description 库区库存统计VO
 * @date 2023/4/24 19:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockGrainStatisticsVo implements Serializable {

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "实际数量(公斤)")
    private BigDecimal sjsl;

    @ApiModelProperty(value = "计价数量（公斤）")
    private BigDecimal jjsl;
}
