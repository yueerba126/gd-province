package com.sydata.dashboard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.StockHouse;
import com.sydata.dashboard.domain.StockAffiliation;
import com.sydata.dashboard.param.*;
import com.sydata.dashboard.vo.*;

import java.util.List;

/**
 * 报表管理-库存归属报表Service接口
 *
 * @author system
 * @date 2023-04-21
 */
public interface IStockAffiliationService extends IService<StockAffiliation> {

    /**
     * 分页查询
     *
     * @param pageParam 报表管理-库存归属报表分页参数
     * @return 分页列表
     */
    Page<StockAffiliationVo> pages(StockAffiliationPageParam pageParam);

    /**
     * 库存统计
     *
     * @return 报表管理-库存归属报表统计VO
     */
    StockAffiliationStatisticsVo statistics();

    List<GrainPropertyOverviewVo> grainProperty(AreaQueryParam queryParam);

    List<GrainAssortmentOverviewVo> grainAssortment(String city);

    List<ScheduledReceiptVo> grainScheduled(AreaQueryParam queryParam);

    List<QuantityDeliveredVo> grainQuantity(AreaQueryParam queryParam);

    List<PrefecturalReportVo> grainPrefectural();

    TotalityVo grainTotality();

    List<RepositoryReportVo> grainRepository();

    List<PhysicalProportionVo> grainProportion();

    List<WarningVo> grainWarning();

    List<CityVarietyVo> grainCityVariety();

    List<KdVarietyVo> grainKdVariety(String city);

    /**
     * 储备粮实物库存报表分页查询
     *
     * @param pageParam 储备粮实物库存报表查询参数
     * @return 分页列表
     */
    PhysicalInventoryPageVo physicalInventoryPage(PhysicalInventoryQueryParam pageParam);

    /**
     * 储备粮实物库存报表导出
     *
     * @param pageParam 储备粮实物库存报表查询参数
     */
    void physicalExport(PhysicalInventoryQueryParam pageParam);

    GranaryBasisVo granaryBasis(String kqdm);

    /**
     * 粮食实时库存监督查询
     *
     * @param param 粮食实时库存监督查询参数
     * @return 粮食实时库存监督
     */
    List<InventoryMonitoringVo> inventoryMonitoring(InventoryMonitoringQueryParam param);

    TemperatureVo temperature(String kqdm);

    List<LibraryChainVo> libraryChain(LibraryChainQueryParam queryParam);

    List<CargoCalVo> cargoCal(CargoCalQueryParam cargoCalQueryParam);

    List<StockHouse> listByRegionId(AreaQueryParam queryParam);

    CargoCalDtlVo cargoCalDtl(String hwdm);

    List<TemperatureVo> temperatureCurve(String hwdm, String kqdm);

    Page<MonitoringPointVo> monitoringPoint(MonitoringPointPageParam pageParam);

    /**
     * 储备油实物库存报表
     *
     * @param pageParam 参数
     * @return 报表
     */
    PhysicalInventoryOilPageVo oilPhysicalInventoryPage(PhysicalInventoryQueryParam pageParam);

    /**
     * 储备油实物库存报表导出
     *
     * @param pageParam 参数
     */
    void oilPhysicalExport(PhysicalInventoryQueryParam pageParam);

    /**
     * 储备粮油折合报表
     *
     * @param param 储备粮油折合报表参数
     * @return 储备粮油折合报表VO列表
     */
    List<ReserveGrainOilEquivalentVo> reserveGrainOilEquivalent(ReserveGrainOilEquivalentParam param);

    /**
     * 储备粮油折合报表excel模板下载
     */
    void reserveGrainOilEquivalentExcelDownload();

    /**
     * 储备粮油折合报表excel比对
     */
    void reserveGrainOilEquivalentExcelComparison(ReserveGrainOilEquivalentExcelComparisonParam param);
}