package com.sydata.dashboard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhangcy
 * @since 2023/5/6 16:17
 */
@Data
@ApiModel("储备轮换总览VO")
public class StoreRotationStatisticsVo implements Serializable {

    @ApiModelProperty("粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty("粮食性质名称")
    private String lsxzmc;

    @ApiModelProperty("粮食品种代码")
    private String lspzdm;

    @ApiModelProperty("粮食品种名称")
    private String lspzmc;

    @ApiModelProperty("轮入量（万吨）")
    private BigDecimal lrsl;

    @ApiModelProperty("轮出量（万吨）")
    private BigDecimal lcsl;

}
