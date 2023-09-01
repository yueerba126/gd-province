package com.sydata.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dashboard.domain.DashboardWarehouseCapacity;
import com.sydata.dashboard.param.AreaQueryParam;
import com.sydata.dashboard.vo.*;

import java.util.List;

/**
 * 库存分析总览业务接口
 *
 * @author zhangcy
 * @date 2023/5/5 14:37
 */
public interface IStoreCapacityOverviewService extends IService<DashboardWarehouseCapacity> {
    /**
     * 根据区域分析
     *
     * @param provinceId 省级区域ID
     * @param cityId     市级区域ID
     * @param areaId     区/县区域ID
     * @return 结果
     */
    List<DashboardWarehouseCapacityVo> storeCapacityAreaAnalysis(String provinceId, String cityId, String areaId);

    /**
     * 库区饼图数据分析
     *
     * @param kqdm 库区代码
     * @return 结果
     */
    List<StoreCapacityTypePieVo> storeCapacityTypePieChartAnalysis(String kqdm);

    /**
     * 库区库容变化年代折线图数据分析
     *
     * @param kqdm 库区代码
     * @return 结果
     */
    List<StoreCapacityTypeYearLineVo> storeCapacityYearLineChartAnalysis(String kqdm);

    /**
     * 库区库容利用率柱状图数据分析
     *
     * @param kqdm 库区代码
     * @return 结果
     */
    List<StoreCapacityTypeBarVo> storeCapacityBarChartAnalysis(String kqdm);

    /**
     * 库区库容明细
     *
     * @param kqdm 库区代码
     * @return 结果
     */
    List<DashboardWarehouseCapacityVo> storeCapacityDetail(String kqdm);

    /**
     * 储备计划总览统计
     *
     * @param queryParam 查询参数
     * @return 结果
     */
    List<StorePlanExecuteStatisticsVo> storePlanStatistics(AreaQueryParam queryParam);

    /**
     * 储备计划总览统计
     *
     * @param queryParam 查询参数
     * @return 结果
     */
    List<StoreRotationStatisticsVo> storeRotationStatistics(AreaQueryParam queryParam);

    /**
     * 储备粮计划执行总览-完成情况
     *
     * @param queryParam 查询参数
     * @return 结果
     */
    List<StorePlanCompleteVo> planCompleteDetail(AreaQueryParam queryParam);

    /**
     * 统计各地市仓房个数
     *
     * @return 结果
     */
    List<CountCityWarehouseNumVo> countCityWarehouseNum();

    /**
     * 粮食品种轮换总览
     *
     * @param queryParam 查询参数
     * @return 结果
     */
    List<StoreRotationStatisticsVo> storeGrainRotationStatistics(AreaQueryParam queryParam);
}
