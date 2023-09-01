package com.sydata.warn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 库存数量-粮食库存异常告警dto
 *
 * @author fuql
 * @date 2022-08-19
 */
@ApiModel(description = "库存数量-粮食库存库存数据dto")
@Data
@ToString
@Accessors(chain = true)
public class WarnFoodstuffMessageDto implements Serializable {

    @ApiModelProperty(value = "粮食品种类别")
    private String lspzlb;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "实际数量(公斤)")
    private BigDecimal totalQty;


}
