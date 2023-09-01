package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsProcessingRotation;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-加工轮换 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsProcessingRotation对象", description="流通检测-加工轮换")
public class MonitoringStatisticsProcessingRotationVO extends MonitoringStatisticsProcessingRotation implements Serializable {

    public MonitoringStatisticsProcessingRotationVO(MonitoringStatisticsProcessingRotation monitoringStatisticsProcessingRotation) {
        if (monitoringStatisticsProcessingRotation != null) {
            BeanUtils.copyProperties(monitoringStatisticsProcessingRotation, this);
        }
    }
}
