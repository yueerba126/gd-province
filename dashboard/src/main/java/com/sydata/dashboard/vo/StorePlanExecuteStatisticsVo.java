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
@ApiModel("储备计划总览VO")
public class StorePlanExecuteStatisticsVo implements Serializable {

    @ApiModelProperty("粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty("粮食性质名称")
    private String lsxzmc;

    @ApiModelProperty("计划数量")
    private BigDecimal sl;

    @ApiModelProperty("实际数量")
    private BigDecimal jjsl;

}
