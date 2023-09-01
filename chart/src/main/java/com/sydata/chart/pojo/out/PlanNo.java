package com.sydata.chart.pojo.out;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 出库质检检斤卡片-计划通知
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "出库质检检斤卡片-计划通知")
public class PlanNo {

    @ApiModelProperty(value = "出库文号合同号")
    private String hth;

    @ApiModelProperty(value = "出库通知单号")
    private String cktzdh;
}
