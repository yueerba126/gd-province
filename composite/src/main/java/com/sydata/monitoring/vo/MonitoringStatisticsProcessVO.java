package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsProcess;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-粮油加工信息 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsProcess对象", description="流通检测-粮油加工信息")
public class MonitoringStatisticsProcessVO extends MonitoringStatisticsProcess implements Serializable {

    public MonitoringStatisticsProcessVO(MonitoringStatisticsProcess monitoringStatisticsProcess) {
        if (monitoringStatisticsProcess != null) {
            BeanUtils.copyProperties(monitoringStatisticsProcess, this);
        }
    }
}
