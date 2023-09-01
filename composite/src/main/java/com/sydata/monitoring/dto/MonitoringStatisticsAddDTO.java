package com.sydata.monitoring.dto;

import com.sydata.monitoring.entity.MonitoringStatistics;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流通检测-粮油流通统计新增DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="流通检测-粮油流通统计新增参数", description="流通检测-粮油流通统计新增参数")
public class MonitoringStatisticsAddDTO extends MonitoringStatistics {


}
