package com.sydata.monitoring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 流通检测-价格检测信息查询DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="流通检测-价格检测信息查询分析参数", description="流通检测-价格检测信息查询参数")
public class MonitoringStatisticsPriceCheckStatisticsDTO {
    @ApiModelProperty(value = "监测点ID")
    private String id;
}
