package com.sydata.dashboard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.dashboard.constants.WarehouseTypeConstants;
import com.sydata.dashboard.domain.DashboardWarehouseCapacity;
import com.sydata.dashboard.domain.StorePlanCompleteDetail;
import com.sydata.dashboard.job.IDashboardStatistics;
import com.sydata.dashboard.mapper.StoreCapacityMapper;
import com.sydata.dashboard.param.AreaQueryParam;
import com.sydata.dashboard.service.IStoreCapacityOverviewService;
import com.sydata.dashboard.vo.*;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 库容分析总览业务实现类
 *
 * @author zhangcy
 * @since 2023/5/5 14:37
 */
@Service
public class StoreCapacityOverviewServiceImpl extends ServiceImpl<StoreCapacityMapper, DashboardWarehouseCapacity> implements IStoreCapacityOverviewService, IDashboardStatistics {

    @Resource
    private StoreCapacityMapper storeCapacityMapper;

    @DataBindFieldConvert
    @Override
    public List<DashboardWarehouseCapacityVo> storeCapacityAreaAnalysis(String provinceId, String cityId, String areaId) {
        List<DashboardWarehouseCapacity> dashboardWarehouseCapacities = storeCapacityMapper.groupByCflxdmForRegion(provinceId, cityId, areaId);

        // 总实际数量
        BigDecimal[] totalJjsl = {BigDecimal.ZERO};
        // 总设计仓容
        BigDecimal[] totalSjcr = {BigDecimal.ZERO};
        // 构建VO
        List<DashboardWarehouseCapacityVo> voList = dashboardWarehouseCapacities.stream()
                .peek(entity -> {
                    totalJjsl[0] = totalJjsl[0].add(entity.getJjsl());
                    totalSjcr[0] = totalSjcr[0].add(entity.getSjcr());
                })
                .map(DashboardWarehouseCapacityVo::new).collect(Collectors.toList());
        // 填充空的库容仓房
        paddingEmptyStoreCapacityWarehouse(voList);

        DashboardWarehouseCapacityVo totalVo = new DashboardWarehouseCapacityVo();
        totalVo.setCflxmc("总库容");
        totalVo.setJjsl(totalJjsl[0]);
        totalVo.setSjcr(totalSjcr[0]);
        totalVo.calculateUseRatio();

        voList.add(totalVo);

        return voList;

    }

    private void paddingEmptyStoreCapacityWarehouse(List<DashboardWarehouseCapacityVo> voList) {
        List<String> cflxdmSet = voList.stream()
                .map(DashboardWarehouseCapacityVo::getCflxdm)
                .collect(Collectors.toList());

        List<String> filterCflxdmSet = WarehouseTypeConstants.MAIN_WAREHOUSE_CODE.stream()
                .filter(cflxdm -> !cflxdmSet.contains(cflxdm))
                .collect(Collectors.toList());

        filterCflxdmSet.forEach(cflxdm -> {
            DashboardWarehouseCapacityVo vo = new DashboardWarehouseCapacityVo();
            vo.setCflxdm(cflxdm);
            vo.setJjsl(BigDecimal.ZERO);
            vo.setSjcr(BigDecimal.ZERO);
            vo.setUseRatio(BigDecimal.ZERO);
            voList.add(vo);
        });
    }

    @DataBindFieldConvert
    @Override
    public List<StoreCapacityTypePieVo> storeCapacityTypePieChartAnalysis(String kqdm) {

        List<DashboardWarehouseCapacity> dashboardWarehouseCapacities = storeCapacityMapper.groupByCflxdmForKqdm(kqdm);
        // 总库容
        BigDecimal totalSjcr = dashboardWarehouseCapacities.stream()
                .map(DashboardWarehouseCapacity::getSjcr).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        return dashboardWarehouseCapacities.stream()
                .map(entity -> new StoreCapacityTypePieVo(entity).calculateProportion(totalSjcr)).collect(Collectors.toList());
    }

    @DataBindFieldConvert
    @Override
    public List<StoreCapacityTypeYearLineVo> storeCapacityYearLineChartAnalysis(String kqdm) {
        return storeCapacityMapper.yearLineChartAnalysis(kqdm);
    }

    @DataBindFieldConvert
    @Override
    public List<StoreCapacityTypeBarVo> storeCapacityBarChartAnalysis(String kqdm) {
        List<StoreCapacityTypeBarVo> provinceUseRatioVos = storeCapacityMapper.selectUseRatioProvinceAll();
        provinceUseRatioVos.forEach(StoreCapacityTypeBarVo::calculateUseRatioForStoreHouse);

        Map<String, BigDecimal> provinceUseRatioMap = provinceUseRatioVos.stream()
                .collect(Collectors.toMap(StoreCapacityTypeBarVo::getCflxdm,
                        StoreCapacityTypeBarVo::getUseRatioForStoreHouse, (preVal, postVal) -> preVal));

        List<StoreCapacityTypeBarVo> storeCapacityTypeBarVos = storeCapacityMapper.selectUseRatioByKqdm(kqdm);
        storeCapacityTypeBarVos.forEach(vo -> {
            vo.calculateUseRatioForStoreHouse();
            vo.setUseRatioForProvince(Optional.ofNullable(provinceUseRatioMap.get(vo.getCflxdm())).orElse(BigDecimal.ZERO));
        });

        return storeCapacityTypeBarVos;
    }

    @DataBindFieldConvert
    @Override
    public List<DashboardWarehouseCapacityVo> storeCapacityDetail(String kqdm) {
        return lambdaQuery()
                .eq(DashboardWarehouseCapacity::getStockHouseId, kqdm)
                .list()
                .stream()
                .map(DashboardWarehouseCapacityVo::new)
                .collect(Collectors.toList());
    }

    @DataBindFieldConvert
    @Override
    public List<StorePlanExecuteStatisticsVo> storePlanStatistics(AreaQueryParam queryParam) {
        List<StorePlanExecuteStatisticsVo> voList = storeCapacityMapper.storePlanStatistics(queryParam);

        BigDecimal[] totalSl = {BigDecimal.ZERO};
        BigDecimal[] totalJjsl = {BigDecimal.ZERO};

        voList.forEach(vo -> {
            totalSl[0] = totalSl[0].add(Optional.ofNullable(vo.getSl()).orElse(BigDecimal.ZERO));
            totalJjsl[0] = totalJjsl[0].add(Optional.ofNullable(vo.getJjsl()).orElse(BigDecimal.ZERO));
            vo.setSl(vo.getSl().setScale(3, RoundingMode.HALF_UP));
            vo.setJjsl(vo.getJjsl().setScale(3, RoundingMode.HALF_UP));
        });

        StorePlanExecuteStatisticsVo totalVo = new StorePlanExecuteStatisticsVo();
        totalVo.setLsxzmc("总计");
        totalVo.setSl(totalSl[0].setScale(3, RoundingMode.HALF_UP));
        totalVo.setJjsl(totalJjsl[0].setScale(3, RoundingMode.HALF_UP));
        voList.add(totalVo);

        return voList;
    }

    @DataBindFieldConvert
    @Override
    public List<StoreRotationStatisticsVo> storeRotationStatistics(AreaQueryParam queryParam) {
        List<StoreRotationStatisticsVo> voList = storeCapacityMapper.storeRotationStatistics(queryParam);

        BigDecimal[] totalLrsl = {BigDecimal.ZERO};
        BigDecimal[] totalLcsl = {BigDecimal.ZERO};

        voList.forEach(vo -> {
            totalLrsl[0] = totalLrsl[0].add(Optional.ofNullable(vo.getLrsl()).orElse(BigDecimal.ZERO));
            totalLcsl[0] = totalLcsl[0].add(Optional.ofNullable(vo.getLcsl()).orElse(BigDecimal.ZERO));
            vo.setLrsl(vo.getLrsl().setScale(3, RoundingMode.HALF_UP));
            vo.setLcsl(vo.getLcsl().setScale(3, RoundingMode.HALF_UP));
        });

        StoreRotationStatisticsVo totalVo = new StoreRotationStatisticsVo();
        totalVo.setLsxzmc("总计");
        totalVo.setLrsl(totalLrsl[0].setScale(3, RoundingMode.HALF_UP));
        totalVo.setLcsl(totalLcsl[0].setScale(3, RoundingMode.HALF_UP));
        voList.add(totalVo);

        return voList;
    }


    @Override
    public List<StoreRotationStatisticsVo> storeGrainRotationStatistics(AreaQueryParam queryParam) {
        List<StoreRotationStatisticsVo> voList = storeCapacityMapper.storeGrainRotationStatistics(queryParam);

        BigDecimal[] totalLrsl = {BigDecimal.ZERO};
        BigDecimal[] totalLcsl = {BigDecimal.ZERO};

        voList.forEach(vo -> {
            totalLrsl[0] = totalLrsl[0].add(Optional.ofNullable(vo.getLrsl()).orElse(BigDecimal.ZERO));
            totalLcsl[0] = totalLcsl[0].add(Optional.ofNullable(vo.getLcsl()).orElse(BigDecimal.ZERO));
            vo.setLrsl(vo.getLrsl().setScale(3, RoundingMode.HALF_UP));
            vo.setLcsl(vo.getLcsl().setScale(3, RoundingMode.HALF_UP));
        });

        StoreRotationStatisticsVo totalVo = new StoreRotationStatisticsVo();
        totalVo.setLsxzmc("总计");
        totalVo.setLrsl(totalLrsl[0].setScale(3, RoundingMode.HALF_UP));
        totalVo.setLcsl(totalLcsl[0].setScale(3, RoundingMode.HALF_UP));
        voList.add(totalVo);

        return voList;
    }

    @DataBindFieldConvert
    @Override
    public List<StorePlanCompleteVo> planCompleteDetail(AreaQueryParam queryParam) {
        List<StorePlanCompleteDetail> completeDetails = storeCapacityMapper.planCompleteDetail(queryParam);

        return completeDetails.stream().parallel()
                .collect(Collectors.groupingBy(StorePlanCompleteDetail::positionKey))
                .entrySet().stream()
                .map(positionEntry -> {
                    StorePlanCompleteDetail.PositionKey positionKey = positionEntry.getKey();
                    List<StorePlanCompleteDetail> planCompleteDetails = positionEntry.getValue();
                    return new StorePlanCompleteVo(positionKey, planCompleteDetails);
                })
                .collect(Collectors.toList());

    }

    @DataBindFieldConvert
    @Override
    public List<CountCityWarehouseNumVo> countCityWarehouseNum() {
        return storeCapacityMapper.countCityWarehouseNum();
    }

    @Override
    public void dashboardStatistics() {
        storeCapacityMapper.sync();
    }
}
