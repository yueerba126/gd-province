package com.sydata.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.dashboard.domain.DashboardWarehouseCapacity;
import com.sydata.dashboard.domain.StorePlanCompleteDetail;
import com.sydata.dashboard.param.AreaQueryParam;
import com.sydata.dashboard.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangcy
 * @date 2023/5/5 17:38
 */
public interface StoreCapacityMapper extends BaseMapper<DashboardWarehouseCapacity> {

    /**
     * 数据同步
     *
     * @return 结果
     */
    boolean sync();

    /**
     * 库区库容变化年代折线图数据分析
     *
     * @param kqdm 库区代码
     * @return 结果
     */
    List<StoreCapacityTypeYearLineVo> yearLineChartAnalysis(@Param("kqdm") String kqdm);

    /**
     * 根据库区代码查询库容利用率
     *
     * @param kqdm 库区代码
     * @return 结果
     */
    List<StoreCapacityTypeBarVo> selectUseRatioByKqdm(@Param("kqdm") String kqdm);

    /**
     * 查询全省库容利用率
     *
     * @return 结果
     */
    List<StoreCapacityTypeBarVo> selectUseRatioProvinceAll();

    /**
     * 为指定的区域ID，根据仓房类型分组
     *
     * @param provinceId 省区域ID
     * @param cityId     市区域ID
     * @param areaId     区县区域ID
     * @return 结果
     */
    List<DashboardWarehouseCapacity> groupByCflxdmForRegion(@Param("provinceId") String provinceId, @Param("cityId") String cityId, @Param("areaId") String areaId);

    /**
     * 为指定的库区代码，根据仓房类型分组
     *
     * @param kqdm 库区代码
     * @return 结果
     */
    List<DashboardWarehouseCapacity> groupByCflxdmForKqdm(@Param("kqdm") String kqdm);

    /**
     * 为指定的区域ID，根据仓储备粮做分组
     *
     * @param queryParam 查询参数
     * @return 结果
     */
    List<StorePlanExecuteStatisticsVo> storePlanStatistics(AreaQueryParam queryParam);

    /**
     * 为指定的区域ID，根据仓储备粮轮换做分组
     *
     * @param queryParam 查询参数
     * @return 结果
     */
    List<StoreRotationStatisticsVo> storeRotationStatistics(AreaQueryParam queryParam);

    /**
     * 为指定的区域ID，根据仓储备粮品种轮换做分组
     *
     * @param queryParam 查询参数
     * @return 结果
     */
    List<StoreRotationStatisticsVo> storeGrainRotationStatistics(AreaQueryParam queryParam);

    /**
     * 储备计划完成情况
     *
     * @param queryParam 查询参数
     * @return 结果
     */
    List<StorePlanCompleteDetail> planCompleteDetail(AreaQueryParam queryParam);

    /**
     * 统计各地市仓房数量
     *
     * @return 结果
     */
    List<CountCityWarehouseNumVo> countCityWarehouseNum();

}
