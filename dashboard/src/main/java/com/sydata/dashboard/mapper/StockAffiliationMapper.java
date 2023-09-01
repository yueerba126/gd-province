package com.sydata.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.dashboard.domain.StockAffiliation;
import com.sydata.dashboard.dto.InventoryMonitoringDto;
import com.sydata.dashboard.dto.InventoryPlanDto;
import com.sydata.dashboard.param.*;
import com.sydata.dashboard.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报表管理-库存归属报表Mapper接口
 *
 * @author system
 * @date 2023-04-21
 */
public interface StockAffiliationMapper extends BaseMapper<StockAffiliation> {


    /**
     * 同步库存
     */
    void syncStockAffiliation();

    /**
     * 储备总库存统计
     *
     * @param param param
     * @return 储备总库存
     */
    @DataPermissionExclude
    NatureStockVo reserveTotalStock(@Param("param") StatisticsQueryParam param);

    /**
     * 市储库存统计
     *
     * @param param param
     * @return 市储库存
     */
    @DataPermissionExclude
    List<NatureStockVo> cityStocks(@Param("param") StatisticsQueryParam param);

    /**
     * 县储库存统计
     *
     * @param param param
     * @return 县储库存
     */
    @DataPermissionExclude
    List<NatureStockVo> areaStocks(@Param("param") StatisticsQueryParam param);

    /**
     * 库区库存统计
     *
     * @param param param
     * @return 县储库存
     */
    @DataPermissionExclude
    List<StockAffiliationVo> stockHouseStocks(@Param("param") StatisticsQueryParam param);

    /**
     * 粮食品种类别库存统计
     *
     * @param param param
     * @return 品种类别
     */
    @DataPermissionExclude
    List<VarietyStockVo> varietyStocks(@Param("param") StatisticsQueryParam param);

    /**
     * 性质库存统计
     *
     * @param cityId 市ID
     * @return 性质库存
     */
    @DataPermissionExclude
    List<StockAffiliationVo> natureStocks(@Param("cityId") String cityId, @Param("areaId") String areaId);

    /**
     * 品种类别性质库存统计
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<GrainAssortmentOverviewVo> varietyNatureStocks(@Param("city") String city);

    /**
     * 入库量
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<ScheduledReceiptVo> scheduledReceipt(@Param("cityId") String cityId, @Param("areaId") String areaId);

    /**
     * 出库量
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<QuantityDeliveredVo> quantityDelivered(@Param("cityId") String cityId, @Param("areaId") String areaId);

    /**
     * 地市上报排名
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<PrefecturalReportVo> prefecturalInventory();

    /**
     * 库存实物比例
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<PhysicalProportionVo> physicalProportion();

    /**
     * 库点总数
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    TotalityVo Totality();

    /**
     * 库点上报排名
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<RepositoryReportVo> repositoryReport();

    /**
     * 粮情告警排名
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<WarningVo> warningRanking();

    /**
     * 地市品种统计
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<VarietyVo> cityVariety();

    /**
     * 库点品种统计
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<VarietyVo> kdVariety(@Param("city") String city);

    /**
     * 库点品种统计
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    GranaryBasisVo granaryBasis(@Param("kqdm") String kqdm);

    /**
     * 仓库实时温度
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    TemperatureVo getTemperature(@Param("kqdm") String kqdm);

    /**
     * 仓房廒间货位链
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<LibraryChainVo> libraryChain(LibraryChainQueryParam libraryChainQueryParam);

    /**
     * 查询省级储备
     *
     * @return 省级储备
     */
    @DataPermissionExclude
    List<InventoryMonitoringDto> getProvinceStock();

    /**
     * 查询市级储备
     *
     * @return 市级储备
     */
    @DataPermissionExclude
    List<InventoryMonitoringDto> getCityStock();

    /**
     * 查询省级储备计划
     *
     * @param param 参数
     * @return 省级储备计划
     */
    @DataPermissionExclude
    List<InventoryPlanDto> getPlanDtos(@Param("param") InventoryPlanQueryParam param);

    /**
     * 查询市级储备计划
     *
     * @param param 参数
     * @return 市级储备
     */
    @DataPermissionExclude
    List<InventoryPlanDto> getPlanCityDtos(@Param("param") InventoryPlanQueryParam param);

    /**
     * 货位卡
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<CargoCalVo> cargoCal(CargoCalQueryParam cargoCalQueryParam);

    /**
     * 货位卡详情
     *
     * @return 性质库存
     */
    @DataPermissionExclude
    List<CargoCalDtlVo> cargoCalDtl(@Param("hwdm") String hwdm);

    /**
     * 储备粮实物库存报表分页查询
     *
     * @param param 储备粮实物库存报表查询参数
     * @return 分页列表
     */
    @DataPermissionExclude
    List<PhysicalInventoryNewVo> physicalTotalPage(@Param("param") PhysicalInventoryQueryParam param);

    /**
     * 储备粮实物库存报表分页查询-省市区县合计
     *
     * @param param 储备粮实物库存报表查询参数
     * @return 分页列表
     */
    @DataPermissionExclude
    PhysicalInventoryNewVo physicalTotal(@Param("param") PhysicalInventoryQueryParam param);

    /**
     * 储备油实物库存报表分页查询
     *
     * @param param 储备粮实物库存报表查询参数
     * @return 分页列表
     */
    @DataPermissionExclude
    List<PhysicalInventoryOilNewVo> physicalOilTotalPage(@Param("param") PhysicalInventoryQueryParam param);

    /**
     * 储备油实物库存报表合计
     *
     * @param param 储备粮实物库存报表查询参数
     * @return 分页列表
     */
    @DataPermissionExclude
    PhysicalInventoryOilNewVo physicalOilTotal(@Param("param") PhysicalInventoryQueryParam param);

    /**
     * 温度曲线
     *
     * @param hwdm
     * @return
     */
    @DataPermissionExclude
    List<TemperatureVo> temperatureCurve(@Param("hwdm") String hwdm, @Param("kqdm") String kqdm);

    /**
     * 油品种类别库存统计
     *
     * @param param param
     * @return 品种类别
     */
    @DataPermissionExclude
    List<VarietyStockVo> varietyOilStocks(@Param("param") StatisticsQueryParam param);

    /**
     * 储备粮油折合报表
     *
     * @param param 储备粮油折合报表参数
     * @return 储备粮折合报表VO列表
     */
    List<ReserveGrainOilEquivalentVo> reserveGrainOilEquivalent(@Param("param") ReserveGrainOilEquivalentParam param);
}