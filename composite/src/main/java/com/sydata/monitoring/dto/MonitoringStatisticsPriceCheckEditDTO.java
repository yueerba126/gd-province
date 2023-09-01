package com.sydata.monitoring.dto;

import com.sydata.monitoring.entity.MonitoringStatisticsPriceCheck;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 流通检测-价格检测信息 编辑DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="流通检测-价格检测信息编辑参数", description="流通检测-价格检测信息编辑参数")
public class MonitoringStatisticsPriceCheckEditDTO extends MonitoringStatisticsPriceCheck {


}
