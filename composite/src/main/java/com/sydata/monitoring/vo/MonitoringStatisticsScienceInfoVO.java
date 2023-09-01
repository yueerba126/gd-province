package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsScienceInfo;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-粮油科技信息 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsScienceInfo对象", description="流通检测-粮油科技信息")
public class MonitoringStatisticsScienceInfoVO extends MonitoringStatisticsScienceInfo implements Serializable {

    public MonitoringStatisticsScienceInfoVO(MonitoringStatisticsScienceInfo monitoringStatisticsScienceInfo) {
        if (monitoringStatisticsScienceInfo != null) {
            BeanUtils.copyProperties(monitoringStatisticsScienceInfo, this);
        }
    }
}
