package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatistics;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 流通检测-粮油流通统计 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "MonitoringStatistics对象", description = "流通检测-粮油流通统计")
public class MonitoringStatisticsVO extends MonitoringStatistics implements Serializable {

    @ApiModelProperty("库点名称")
    private String storehouseNames;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("库存总数（万吨）")
    private BigDecimal stockTotal;

    public MonitoringStatisticsVO(MonitoringStatistics monitoringStatistics) {
        if (monitoringStatistics != null) {
            BeanUtils.copyProperties(monitoringStatistics, this);
        }
    }
}
