package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsFarmer;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-居民农户信息 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsFarmer对象", description="流通检测-居民农户信息")
public class MonitoringStatisticsFarmerVO extends MonitoringStatisticsFarmer implements Serializable {

    public MonitoringStatisticsFarmerVO(MonitoringStatisticsFarmer monitoringStatisticsFarmer) {
        if (monitoringStatisticsFarmer != null) {
            BeanUtils.copyProperties(monitoringStatisticsFarmer, this);
        }
    }
}
