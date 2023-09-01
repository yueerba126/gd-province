package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsInfrastructureConstruction;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-基础设施建设信息 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsInfrastructureConstruction对象", description="流通检测-基础设施建设信息")
public class MonitoringStatisticsInfrastructureConstructionVO extends MonitoringStatisticsInfrastructureConstruction implements Serializable {

    public MonitoringStatisticsInfrastructureConstructionVO(MonitoringStatisticsInfrastructureConstruction monitoringStatisticsInfrastructureConstruction) {
        if (monitoringStatisticsInfrastructureConstruction != null) {
            BeanUtils.copyProperties(monitoringStatisticsInfrastructureConstruction, this);
        }
    }
}
