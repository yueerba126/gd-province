package com.sydata.chart.pojo.out;

import com.sydata.chart.pojo.Weighing;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 出库质检检斤卡片-检斤
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "出库质检检斤卡片-检斤")
public class WeighingOut extends Weighing {

    @ApiModelProperty(value = "扣增量")
    private BigDecimal kzl;

    @ApiModelProperty(value = "计价数量")
    private BigDecimal jjsl;
}
