package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.*;
import com.sydata.organize.security.UserSecurity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 流通检测-粮油流通统计 保存DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "MonitoringStatistics明细对象保存参数", description = "流通检测-粮油流通统计")
public class MonitoringStatisticsDetailEditDto extends MonitoringStatistics implements Serializable {

    @Valid
    @ApiModelProperty("仓储设施")
    private MonitoringStatisticsStorageFacilities storageFacilitiesVO;

    @Valid
    @ApiModelProperty("加工轮换")
    private List<MonitoringStatisticsProcessingRotation> processingRotation;

    @Valid
    @ApiModelProperty("粮油科技信息")
    private List<MonitoringStatisticsScienceInfo> scienceInfo;

    @Valid
    @ApiModelProperty("粮油收支平衡月度数据")
    private List<MonitoringStatisticsIncomeExpenses> incomeExpenses;

    @Valid
    @ApiModelProperty("粮食市场监测点信息")
    private List<MonitoringStatisticsCheckPoint> checkPoint;

    @Valid
    @ApiModelProperty("居民农户信息")
    private List<MonitoringStatisticsFarmer> farmer;

    @Valid
    @ApiModelProperty("基础设施建设信息")
    private List<MonitoringStatisticsInfrastructureConstruction> infrastructureConstruction;

    @Valid
    @ApiModelProperty("价格监测信息")
    private List<MonitoringStatisticsPriceCheck> priceCheck;

    @Valid
    @ApiModelProperty("加工信息")
    private List<MonitoringStatisticsProcess> process;

    @Valid
    @ApiModelProperty("粮油购销信息")
    private List<MonitoringStatisticsPurchaseSale> purchaseSale;

    @Valid
    @ApiModelProperty("库存规模信息")
    private List<MonitoringStatisticsStockScale> stockScale;

    @Valid
    @ApiModelProperty("库点信息")
    private List<MonitoringStatisticsStorehouse> storehouse;

    private LocalDateTime now;

    private String countryId;

    private String userName;

    public MonitoringStatisticsDetailEditDto() {

        now = LocalDateTime.now();
        countryId = UserSecurity.loginUser().getCountryId();
        userName = UserSecurity.userName();
    }

    public MonitoringStatisticsStorageFacilities getStorageFacilitiesVO() {

        if (storageFacilitiesVO != null) {
            if (storageFacilitiesVO.getId() == null) {
                storageFacilitiesVO.setCreateBy(userName);
                storageFacilitiesVO.setCreateTime(now);
                storageFacilitiesVO.setCountryId(countryId);
                storageFacilitiesVO.setStatisticId(getId());
            }
            storageFacilitiesVO.setUpdateBy(userName);
            storageFacilitiesVO.setUpdateTime(now);
        }

        return storageFacilitiesVO;
    }

    public List<MonitoringStatisticsProcessingRotation> getProcessingRotation() {
        if (processingRotation != null) {
            processingRotation.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return processingRotation;
    }

    public List<MonitoringStatisticsScienceInfo> getScienceInfo() {
        if (scienceInfo != null) {
            scienceInfo.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return scienceInfo;
    }

    public List<MonitoringStatisticsIncomeExpenses> getIncomeExpenses() {
        if (incomeExpenses != null) {
            incomeExpenses.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return incomeExpenses;
    }

    public List<MonitoringStatisticsCheckPoint> getCheckPoint() {
        if (checkPoint != null) {
            checkPoint.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                }
                entity.setStatisticId(getId());
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return checkPoint;
    }

    public List<MonitoringStatisticsFarmer> getFarmer() {
        if (farmer != null) {
            farmer.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return farmer;
    }

    public List<MonitoringStatisticsInfrastructureConstruction> getInfrastructureConstruction() {
        if (infrastructureConstruction != null) {
            infrastructureConstruction.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return infrastructureConstruction;
    }

    public List<MonitoringStatisticsPriceCheck> getPriceCheck() {
        if (priceCheck != null) {
            priceCheck.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return priceCheck;
    }

    public List<MonitoringStatisticsProcess> getProcess() {
        if (process != null) {
            process.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return process;
    }

    public List<MonitoringStatisticsPurchaseSale> getPurchaseSale() {
        if (purchaseSale != null) {
            purchaseSale.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return purchaseSale;
    }

    public List<MonitoringStatisticsStockScale> getStockScale() {
        if (stockScale != null) {
            stockScale.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return stockScale;
    }

    public List<MonitoringStatisticsStorehouse> getStorehouse() {
        if (storehouse != null) {
            storehouse.forEach(entity -> {
                if (entity.getId() == null) {
                    entity.setCreateBy(userName);
                    entity.setCreateTime(now);
                    entity.setCountryId(countryId);
                    entity.setStatisticId(getId());
                }
                entity.setUpdateBy(userName);
                entity.setUpdateTime(now);
            });

        }
        return storehouse;
    }
}
