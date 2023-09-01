package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsIncomeExpenses;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * <p>
 * 流通检测-粮油收支平衡数据 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsIncomeExpenses对象", description="流通检测-粮油收支平衡数据")
public class MonitoringStatisticsIncomeExpensesVO extends MonitoringStatisticsIncomeExpenses implements Serializable {

    public MonitoringStatisticsIncomeExpensesVO(MonitoringStatisticsIncomeExpenses monitoringStatisticsIncomeExpenses) {
        if (monitoringStatisticsIncomeExpenses != null) {
            BeanUtils.copyProperties(monitoringStatisticsIncomeExpenses, this);
        }
    }
}
