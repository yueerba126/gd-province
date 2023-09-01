package com.sydata.chart.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.trade.domain.Loss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * <p>
 * 损溢单信息卡片
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@ApiModel(description = "损溢单信息卡片Vo")
@Data
@ToString
@Accessors(chain = true)
public class LossChartVo extends Loss implements Serializable {

    @ApiModelProperty("仓房代码")
    private String cfdm;

    @DataBindWarehouse
    @ApiModelProperty("仓房名称")
    private String cfmc;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

}
