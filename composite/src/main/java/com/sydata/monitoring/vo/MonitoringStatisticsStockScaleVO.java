package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsStockScale;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-库存规模 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsStockScale对象", description="流通检测-库存规模")
public class MonitoringStatisticsStockScaleVO extends MonitoringStatisticsStockScale implements Serializable {

    public MonitoringStatisticsStockScaleVO(MonitoringStatisticsStockScale monitoringStatisticsStockScale) {
        if (monitoringStatisticsStockScale != null) {
            BeanUtils.copyProperties(monitoringStatisticsStockScale, this);
        }
    }
}
