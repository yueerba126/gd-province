package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsStorehouse;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-库点信息 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "MonitoringStatisticsStorehouse对象", description = "流通检测-库点信息")
public class MonitoringStatisticsStorehouseVO extends MonitoringStatisticsStorehouse implements Serializable {

    public MonitoringStatisticsStorehouseVO(MonitoringStatisticsStorehouse monitoringStatisticsStorehouse) {
        if (monitoringStatisticsStorehouse != null) {
            BeanUtils.copyProperties(monitoringStatisticsStorehouse, this);
        }
    }
}
