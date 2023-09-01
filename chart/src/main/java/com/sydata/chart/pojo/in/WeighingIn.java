package com.sydata.chart.pojo.in;

import com.sydata.chart.pojo.Weighing;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 入库质检检斤卡片-检斤
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "入库质检检斤卡片-检斤")
public class WeighingIn extends Weighing {

    @ApiModelProperty(value = "水分增扣量")
    private BigDecimal qzsfzkl;

    @ApiModelProperty(value = "其他扣量")
    private BigDecimal qtkl;

    @ApiModelProperty(value = "扣量原因")
    private String klyy;

    @ApiModelProperty(value = "实际数量(公斤)")
    private BigDecimal sjsl;
}
