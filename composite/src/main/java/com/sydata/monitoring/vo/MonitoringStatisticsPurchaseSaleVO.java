package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsPurchaseSale;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流通检测-粮油购销信息 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsPurchaseSale对象", description="流通检测-粮油购销信息")
public class MonitoringStatisticsPurchaseSaleVO extends MonitoringStatisticsPurchaseSale implements Serializable {

    public MonitoringStatisticsPurchaseSaleVO(MonitoringStatisticsPurchaseSale monitoringStatisticsPurchaseSale) {
        if (monitoringStatisticsPurchaseSale != null) {
            BeanUtils.copyProperties(monitoringStatisticsPurchaseSale, this);
        }
    }
}
