package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatistics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(value = "MonitoringStatistics明细对象", description = "流通检测-粮油流通统计")
public class MonitoringStatisticsDetailVO extends MonitoringStatistics implements Serializable {

    @ApiModelProperty("仓储设施")
    private MonitoringStatisticsStorageFacilitiesVO storageFacilitiesVO;

    @ApiModelProperty("个体工商户")
    private GoodCompanyVO goodCompanyVO;

    @ApiModelProperty("加工轮换")
    private List<MonitoringStatisticsProcessingRotationVO> processingRotationVos;

    @ApiModelProperty("粮油科技信息")
    private List<MonitoringStatisticsScienceInfoVO> scienceInfoVos;

    @ApiModelProperty("粮油收支平衡月度数据")
    private List<MonitoringStatisticsIncomeExpensesVO> incomeExpensesVos;

    @ApiModelProperty("粮食市场监测点信息")
    private List<MonitoringStatisticsCheckPointVO> checkPointVos;

    @ApiModelProperty("居民农户信息")
    private List<MonitoringStatisticsFarmerVO> farmerVos;

    @ApiModelProperty("基础设施建设信息")
    private List<MonitoringStatisticsInfrastructureConstructionVO> infrastructureConstructionVos;

    @ApiModelProperty("价格监测信息")
    private List<MonitoringStatisticsPriceCheckVO> priceCheckVos;

    @ApiModelProperty("加工信息")
    private List<MonitoringStatisticsProcessVO> processVos;

    @ApiModelProperty("粮油购销信息")
    private List<MonitoringStatisticsPurchaseSaleVO> purchaseSaleVos;

    @ApiModelProperty("库存规模信息")
    private List<MonitoringStatisticsStockScaleVO> stockScaleVos;

    @ApiModelProperty("库点信息")
    private List<MonitoringStatisticsStorehouseVO> storehouseVos;

    public MonitoringStatisticsDetailVO(MonitoringStatistics monitoringStatistics) {
        if (monitoringStatistics != null) {
            BeanUtils.copyProperties(monitoringStatistics, this);
        }
    }
}
