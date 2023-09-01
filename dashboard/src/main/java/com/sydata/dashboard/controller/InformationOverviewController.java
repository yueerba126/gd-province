package com.sydata.dashboard.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.StockHouse;
import com.sydata.dashboard.param.AreaQueryParam;
import com.sydata.dashboard.param.CargoCalQueryParam;
import com.sydata.dashboard.param.LibraryChainQueryParam;
import com.sydata.dashboard.param.MonitoringPointPageParam;
import com.sydata.dashboard.service.IStockAffiliationService;
import com.sydata.dashboard.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 全省信息总览-信息总览Controller
 *
 * @author system
 * @date 2023-05-25
 */
@Api(tags = "全省信息总览-信息总览")
@Validated
@RestController
@RequestMapping("/dashboard/information/overview")
public class InformationOverviewController {

    @Resource
    private IStockAffiliationService stockAffiliationService;

    @ApiOperation("全省库存量总览（万吨）-粮权")
    @PostMapping("/grain/property")
    public List<GrainPropertyOverviewVo> grainProperty(@RequestBody AreaQueryParam queryParam) {
        return stockAffiliationService.grainProperty(queryParam);
    }

    @ApiOperation("按品种分类（万吨）")
    @PostMapping("/grain/assortment")
    public List<GrainAssortmentOverviewVo> grainAssortment(@RequestParam(value = "city", required = false) String city) {
        city = regionQuery(city);
        return stockAffiliationService.grainAssortment(city);
    }

    @ApiOperation("入库量（万吨）")
    @PostMapping("/grain/scheduled")
    public List<ScheduledReceiptVo> grainScheduled(@RequestBody AreaQueryParam queryParam) {
        return stockAffiliationService.grainScheduled(queryParam);
    }

    @ApiOperation("出库量（万吨）")
    @PostMapping("/grain/quantity")
    public List<QuantityDeliveredVo> grainQuantity(@RequestBody AreaQueryParam queryParam) {
        return stockAffiliationService.grainQuantity(queryParam);
    }

    @ApiOperation("地市上报排名")
    @PostMapping("/grain/prefectural")
    public List<PrefecturalReportVo> grainPrefectural() {

        return stockAffiliationService.grainPrefectural();
    }

    @ApiOperation("库点总数")
    @PostMapping("/grain/totality")
    public TotalityVo grainTotality() {

        return stockAffiliationService.grainTotality();
    }

    @ApiOperation("库存实物比例")
    @PostMapping("/grain/proportion")
    public List<PhysicalProportionVo> grainProportion() {

        return stockAffiliationService.grainProportion();
    }

    @ApiOperation("库点上报排名")
    @PostMapping("/grain/repository")
    public List<RepositoryReportVo> grainRepository() {

        return stockAffiliationService.grainRepository();
    }

    @ApiOperation("粮情告警排名")
    @PostMapping("/grain/warning")
    public List<WarningVo> grainWarning() {

        return stockAffiliationService.grainWarning();
    }

    @ApiOperation("地市品种统计")
    @PostMapping("/grain/city/variety")
    public List<CityVarietyVo> grainCityVariety() {

        return stockAffiliationService.grainCityVariety();
    }

    @ApiOperation("库点品种统计")
    @PostMapping("/grain/kd/variety")
    public List<KdVarietyVo> grainKdVariety(@RequestParam(value = "city", required = false) String city) {
        city = regionQuery(city);
        return stockAffiliationService.grainKdVariety(city);
    }

    @ApiOperation("库区基本信息")
    @PostMapping("/grain/granary/basis")
    public GranaryBasisVo granaryBasis(@RequestParam(value = "kqdm", required = false) String kqdm) {
        return stockAffiliationService.granaryBasis(kqdm);
    }

    @ApiOperation("仓库实时温度")
    @PostMapping("/grain/temperature")
    public TemperatureVo temperature(@RequestParam(value = "kqdm", required = true) String kqdm) {
        return stockAffiliationService.temperature(kqdm);
    }

    @ApiOperation("仓房廒间货位链")
    @PostMapping("/grain/library/chain")
    public List<LibraryChainVo> grainQuantity(@RequestBody LibraryChainQueryParam queryParam) {
        return stockAffiliationService.libraryChain(queryParam);
    }

    @ApiOperation("货位卡")
    @PostMapping("/grain/cargo/cal")
    public List<CargoCalVo> cargoCal(@RequestBody CargoCalQueryParam cargoCalQueryParam) {
        return stockAffiliationService.cargoCal(cargoCalQueryParam);
    }

    @ApiOperation("货位卡详情")
    @PostMapping("/grain/cargo/cal/Dtl")
    public CargoCalDtlVo cargoCalDtl(@RequestParam(value = "hwdm", required = true) String hwdm) {
        return stockAffiliationService.cargoCalDtl(hwdm);
    }

    @ApiOperation("根据行政区划查询库区列表")
    @PostMapping("/grain/regions")
    public List<StockHouse> listByRegionId(@RequestBody AreaQueryParam queryParam) {
        return stockAffiliationService.listByRegionId(queryParam);
    }

    @ApiOperation("温度曲线图")
    @PostMapping("/grain/temperature/Curve")
    public List<TemperatureVo> temperatureCurve(@RequestParam(value = "hwdm", required = false) String hwdm,
                                                @RequestParam(value = "kqdm", required = false) String kqdm) {
        return stockAffiliationService.temperatureCurve(hwdm, kqdm);
    }

    @ApiOperation("监测点信息")
    @PostMapping("/grain/monitoring/point")
    public Page<MonitoringPointVo> monitoringPoint(@RequestBody MonitoringPointPageParam pageParam) {
        return stockAffiliationService.monitoringPoint(pageParam);
    }

    /**
     * 处理市或区县查询条件
     */
    public static String regionQuery(String code) {
        if (code == null) {
            return null;
        }
        return code.substring(0, 4);
    }
}