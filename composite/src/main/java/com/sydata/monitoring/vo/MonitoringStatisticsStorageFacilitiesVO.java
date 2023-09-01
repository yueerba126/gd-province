package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsStorageFacilities;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-仓储设施 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsStorageFacilities对象", description="流通检测-仓储设施")
public class MonitoringStatisticsStorageFacilitiesVO extends MonitoringStatisticsStorageFacilities implements Serializable {

    public MonitoringStatisticsStorageFacilitiesVO(MonitoringStatisticsStorageFacilities monitoringStatisticsStorageFacilities) {
        if (monitoringStatisticsStorageFacilities != null) {
            BeanUtils.copyProperties(monitoringStatisticsStorageFacilities, this);
        }
    }
}
