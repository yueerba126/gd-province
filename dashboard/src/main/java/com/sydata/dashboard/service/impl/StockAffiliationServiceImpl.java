package com.sydata.dashboard.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.basis.domain.Dict;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.service.IDictService;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.dashboard.domain.StockAffiliation;
import com.sydata.dashboard.dto.InventoryMonitoringDto;
import com.sydata.dashboard.dto.InventoryPlanDto;
import com.sydata.dashboard.enums.InventoryMonitoringEnums;
import com.sydata.dashboard.job.IDashboardStatistics;
import com.sydata.dashboard.mapper.StockAffiliationMapper;
import com.sydata.dashboard.param.*;
import com.sydata.dashboard.service.IStockAffiliationService;
import com.sydata.dashboard.vo.*;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.GrainMonitor;
import com.sydata.manage.service.IGrainMonitorService;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IRegionService;
import com.sydata.trade.domain.InStock;
import com.sydata.trade.domain.OutStock;
import com.sydata.trade.service.IInStockService;
import com.sydata.trade.service.IOutStockService;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.SPACE;
import static com.sydata.framework.util.StringUtils.isNotEmpty;
import static com.sydata.organize.enums.RegionTypeEnum.*;
import static com.sydata.organize.handler.OrganizeMetaObjectHandler.DEFAULT_ID;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * 报表管理-库存归属报表Service业务层处理
 *
 * @author system
 * 2023-04-21
 */
@CacheConfig(cacheNames = StockAffiliationServiceImpl.CACHE_NAME)
@Service("stockAffiliationService")
public class StockAffiliationServiceImpl extends ServiceImpl<StockAffiliationMapper, StockAffiliation>
        implements IStockAffiliationService, IDashboardStatistics {

    final static String CACHE_NAME = "dashboard:stockAffiliation";

    final static String LSPZ_DIC = "food_big_variety";
    final static String SJCBL_TATLE = "0";
    final static String SJCBL_HJ = "合计（万吨）";
    /**
     * 省（自治区、直辖市）级地方储备粮
     */
    final static String FOOD_PROVINCE = "121";
    /**
     * 市（地区、自治州、盟）级地方储备粮
     */
    final static String FOOD_CITY = "122";
    final static String DEFAULT_ONE = "-1";

    final static BigDecimal RATE = new BigDecimal("100");

    @Resource
    private StockAffiliationMapper stockAffiliationMapper;

    @Resource
    private IStockHouseService stockHouseService;

    @Resource
    private IDictService dictService;

    @Resource
    private IRegionService regionService;

    @Resource
    private IGrainMonitorService grainMonitorService;

    @Resource
    private IInStockService inStockService;

    @Resource
    private IOutStockService outStockService;

    @DataBindFieldConvert
    @Override
    public Page<StockAffiliationVo> pages(StockAffiliationPageParam pageParam) {
        Page<StockAffiliation> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getLsxzdm()), StockAffiliation::getLsxzdm, pageParam.getLsxzdm())
                .eq(isNotEmpty(pageParam.getLspzlb()), StockAffiliation::getLspzlb, pageParam.getLspzlb())
                .eq(isNotEmpty(pageParam.getCity()), StockAffiliation::getCityId, pageParam.getCity())
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, StockAffiliationVo.class);
    }

    @DataBindFieldConvert
    @Override
    public StockAffiliationStatisticsVo statistics() {
        StatisticsQueryParam param = getStatisticsQueryParam();
        return new StockAffiliationStatisticsVo()
                .setProvinceTotalStock(stockAffiliationMapper.reserveTotalStock(param))
                .setCityStocks(stockAffiliationMapper.cityStocks(param))
                .setAreaStocks(stockAffiliationMapper.areaStocks(param))
                .setStockHouseStocks(stockAffiliationMapper.stockHouseStocks(param))
                .setVarietyStocks(stockAffiliationMapper.varietyStocks(param))
                .setOilVarietyStocks(stockAffiliationMapper.varietyOilStocks(param));

    }

    private StatisticsQueryParam getStatisticsQueryParam() {
        StatisticsQueryParam param = new StatisticsQueryParam();
        LoginUser loginUser = UserSecurity.loginUser();
        String areaId = loginUser.getAreaId();
        String provinceId = loginUser.getProvinceId();
        String cityId = loginUser.getCityId();
        if (isNotEmpty(areaId) && !DEFAULT_ONE.equals(areaId)) {
            param.setAreaId(areaId);
        }
        if (isNotEmpty(provinceId) && !DEFAULT_ONE.equals(provinceId)) {
            param.setProvinceId(provinceId);
        }
        if (isNotEmpty(cityId) && !DEFAULT_ONE.equals(cityId)) {
            param.setCityId(cityId);
        }
        return param;
    }

    @DataBindFieldConvert
    @Override
    public List<GrainPropertyOverviewVo> grainProperty(AreaQueryParam queryParam) {
        List<StockAffiliationVo> stockAffiliationVos = stockAffiliationMapper.natureStocks(queryParam.getCityId(), queryParam.getAreaId());
        return BeanUtils.copyToList(stockAffiliationVos, GrainPropertyOverviewVo.class);
    }

    @DataBindFieldConvert
    @Override
    public List<GrainAssortmentOverviewVo> grainAssortment(String city) {
        return stockAffiliationMapper.varietyNatureStocks(city);
    }

    @DataBindFieldConvert
    @Override
    public List<ScheduledReceiptVo> grainScheduled(AreaQueryParam queryParam) {
        return stockAffiliationMapper.scheduledReceipt(queryParam.getCityId(), queryParam.getAreaId());
    }

    @DataBindFieldConvert
    @Override
    public List<QuantityDeliveredVo> grainQuantity(AreaQueryParam queryParam) {
        return stockAffiliationMapper.quantityDelivered(queryParam.getCityId(), queryParam.getAreaId());
    }

    @DataBindFieldConvert
    @Override
    public List<PrefecturalReportVo> grainPrefectural() {
        return stockAffiliationMapper.prefecturalInventory();
    }

    @DataBindFieldConvert
    @Override
    public TotalityVo grainTotality() {
        return stockAffiliationMapper.Totality();
    }

    @DataBindFieldConvert
    @Override
    public List<RepositoryReportVo> grainRepository() {

        return stockAffiliationMapper.repositoryReport();
    }

    @DataBindFieldConvert
    @Override
    public List<PhysicalProportionVo> grainProportion() {
        List<PhysicalProportionVo> physicalProportionVos = stockAffiliationMapper.physicalProportion();
        physicalProportionVos.forEach(item -> {
            if (item.getCity().equals("-1")) {
                item.setCityName("省级储备粮");
            }
            item.setKcswbl((item.getCbgml() != null && item.getKcswbl() != null) ? item.getKcswbl().divide(item.getCbgml(), BigDecimal.ROUND_CEILING) : null);
        });
        return physicalProportionVos;
    }

    @DataBindFieldConvert
    @Override
    public List<WarningVo> grainWarning() {

        return stockAffiliationMapper.warningRanking();
    }

    @DataBindFieldConvert
    @Override
    public List<CityVarietyVo> grainCityVariety() {
        List<VarietyVo> varietyVos = stockAffiliationMapper.cityVariety();
        Map<String, List<VarietyVo>> collect = varietyVos.stream().filter(
                t -> t.getCity() != null
        ).collect(Collectors.groupingBy(t -> t.getCity()));
        List<CityVarietyVo> varietyVoList = new ArrayList<>();
        collect.forEach((key, value) -> {
            CityVarietyVo cityVarietyVo = new CityVarietyVo();
            cityVarietyVo.setCity(key);
            cityVarietyVo.setLspzlb(value);
            cityVarietyVo.setGross(value.stream().mapToDouble(t -> t.getSjsl().doubleValue()).sum());
            varietyVoList.add(cityVarietyVo);
        });
        return varietyVoList;
    }

    @DataBindFieldConvert
    @Override
    public List<KdVarietyVo> grainKdVariety(String city) {
        List<VarietyVo> varietyVos = stockAffiliationMapper.kdVariety(city);
        Map<String, List<VarietyVo>> collect = varietyVos.stream().filter(
                t -> t.getKddm() != null
        ).collect(Collectors.groupingBy(t -> t.getKddm()));
        List<KdVarietyVo> varietyVoList = new ArrayList<>();
        collect.forEach((key, value) -> {
            KdVarietyVo kdVarietyVo = new KdVarietyVo();
            kdVarietyVo.setKddm(key);
            kdVarietyVo.setLspzlb(value);
            kdVarietyVo.setGross(value.stream().mapToDouble(t -> t.getSjsl().doubleValue()).sum());
            varietyVoList.add(kdVarietyVo);
        });
        return varietyVoList;
    }

    @DataBindFieldConvert(convertNumber = 2)
    @Override
    public PhysicalInventoryPageVo physicalInventoryPage(PhysicalInventoryQueryParam pageParam) {
        getParamRegion(pageParam);
        PhysicalInventoryPageVo vo = new PhysicalInventoryPageVo();
        List<PhysicalInventoryNewVo> pageVo = stockAffiliationMapper.physicalTotalPage(pageParam);
        PhysicalInventoryNewVo total = stockAffiliationMapper.physicalTotal(pageParam);
        total = getInventoryAllVo(pageVo, total);
        return vo.setPageVo(pageVo).setInventoryVo(total);
    }

    private void getParamRegion(PhysicalInventoryQueryParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        String areaId = loginUser.getAreaId();
        String provinceId = loginUser.getProvinceId();
        String cityId = loginUser.getCityId();
        if (isNotEmpty(areaId) && !DEFAULT_ONE.equals(areaId)) {
            pageParam.setAreaId(areaId);
        }
        if (isNotEmpty(provinceId) && !DEFAULT_ONE.equals(provinceId)) {
            pageParam.setProvinceId(provinceId);
        }
        if (isNotEmpty(cityId) && !DEFAULT_ONE.equals(cityId)) {
            pageParam.setCityId(cityId);
        }
    }

    private PhysicalInventoryNewVo getInventoryAllVo(List<PhysicalInventoryNewVo> pageVo, PhysicalInventoryNewVo total) {
        if (!CollectionUtils.isEmpty(pageVo)) {
            total.setLqxzqhdmName("共" + pageVo.size() + "条记录");
        } else {
            if (Objects.isNull(total)) {
                total = new PhysicalInventoryNewVo().setLqxzqhdmName("共0条记录");
            } else {
                total.setLqxzqhdmName("共0条记录");
            }
        }
        return total;
    }

    private PhysicalInventoryOilNewVo getInventoryOilAllVo(List<PhysicalInventoryOilNewVo> pageVo, PhysicalInventoryOilNewVo total) {
        if (!CollectionUtils.isEmpty(pageVo)) {
            total.setLqxzqhdmName("共" + pageVo.size() + "条记录");
        } else {
            if (Objects.isNull(total)) {
                total = new PhysicalInventoryOilNewVo().setLqxzqhdmName("共0条记录");
            } else {
                total.setLqxzqhdmName("共0条记录");
            }
        }
        return total;
    }

    @Override
    public void physicalExport(PhysicalInventoryQueryParam pageParam) {
        getParamRegion(pageParam);
        PhysicalInventoryPageVo vo = physicalInventoryPage(pageParam);
        List<PhysicalInventoryNewVo> vos = vo.getPageVo();
        vos.add(vo.getInventoryVo());
        DataBindHandleBootstrap.dataHandConvert(vos, 2);
        ZtExcelBuildUtil.buildExcelExport(PhysicalInventoryNewVo.class, "储备粮实物库存报表").setData(vos).doWebExport();
    }

    @DataBindFieldConvert(convertNumber = 2)
    @Override
    public PhysicalInventoryOilPageVo oilPhysicalInventoryPage(PhysicalInventoryQueryParam pageParam) {
        getParamRegion(pageParam);
        PhysicalInventoryOilPageVo vo = new PhysicalInventoryOilPageVo();
        List<PhysicalInventoryOilNewVo> pageVo = stockAffiliationMapper.physicalOilTotalPage(pageParam);
        PhysicalInventoryOilNewVo total = stockAffiliationMapper.physicalOilTotal(pageParam);
        total = getInventoryOilAllVo(pageVo, total);
        return vo.setPageVo(pageVo).setInventoryVo(total);
    }

    @Override
    public void oilPhysicalExport(PhysicalInventoryQueryParam pageParam) {
        getParamRegion(pageParam);
        PhysicalInventoryOilPageVo vo = oilPhysicalInventoryPage(pageParam);
        List<PhysicalInventoryOilNewVo> vos = vo.getPageVo();
        vos.add(vo.getInventoryVo());
        DataBindHandleBootstrap.dataHandConvert(vos, 2);
        ZtExcelBuildUtil.buildExcelExport(PhysicalInventoryOilNewVo.class, "储备油实物库存报表").setData(vos).doWebExport();
    }

    @Override
    public List<ReserveGrainOilEquivalentVo> reserveGrainOilEquivalent(ReserveGrainOilEquivalentParam param) {
        // 根据当前登录人所属行政区划类型动态分组：国/省分市,市/县分区
        Map<String, String> groupByMap = MapUtil.newHashMap();
        groupByMap.put(CITY.getType(), "area_id");
        groupByMap.put(AREA.getType(), "area_id");

        String groupBy = groupByMap.getOrDefault(param.getType(), "city_id");
        param.setGroupBy(groupBy);
        List<ReserveGrainOilEquivalentVo> list = stockAffiliationMapper.reserveGrainOilEquivalent(param);
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }

        // 当本级区划为省/市时需要进行数据处理：统计、本级区划重命名
        ReserveGrainOilEquivalentVo root = StreamEx.of(list)
                .toMap(ReserveGrainOilEquivalentVo::getId, Function.identity())
                .getOrDefault(param.getId(), list.get(0));
        if (AREA.getType().equals(root.getType())) {
            return list;
        }

        boolean province = PROVINCE.getType().equals(root.getType());

        ReserveGrainOilEquivalentVo statistics = new ReserveGrainOilEquivalentVo()
                .setId(DEFAULT_ID)
                .setName(root.getName())
                .setType(root.getType())
                .setZhyl(StreamEx.of(list).map(ReserveGrainOilEquivalentVo::getZhyl).reduce(ZERO, BigDecimal::add))
                .setZhyy(StreamEx.of(list).map(ReserveGrainOilEquivalentVo::getZhyy).reduce(ZERO, BigDecimal::add));
        list.add(0, statistics);

        // 本级区划重命名
        root.setName(province ? "广东省储备粮管理集团有限公司" : "市本级");

        return list;
    }

    @Override
    public void reserveGrainOilEquivalentExcelDownload() {
        ZtExcelBuildUtil.buildExcelExport(ReserveGrainOilEquivalentVo.class, "储备粮油折合报表导入比对模板")
                .setData(Collections.emptyList())
                .doWebExport();
    }

    @Override
    public void reserveGrainOilEquivalentExcelComparison(ReserveGrainOilEquivalentExcelComparisonParam param) {
        // 读取数据
        InputStream inputStream = null;
        List<ReserveGrainOilEquivalentVo> excels;
        try {
            inputStream = param.getFile().getInputStream();
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            excels = ExcelImportUtil.importExcel(inputStream, ReserveGrainOilEquivalentVo.class, params);
        } catch (Exception e) {
            throw new RuntimeException("解析导入的Excel失败", e);
        } finally {
            IoUtil.close(inputStream);
        }

        Map<String, ReserveGrainOilEquivalentVo> nameMap = StreamEx.of(excels)
                .filter(v -> StringUtils.isNotEmpty(v.getName()))
                .peek(v -> v.setName(v.getName().replaceAll(SPACE, StringPool.EMPTY)))
                .groupingBy(ReserveGrainOilEquivalentVo::getName, collectingAndThen(toList(), ts -> ts.get(0)));


        // 获取数据进行比对
        List<ReserveGrainOilEquivalentExcelVo> excelVos = StreamEx.of(reserveGrainOilEquivalent(param))
                .map(vo -> {
                    ReserveGrainOilEquivalentVo importVo = nameMap.get(vo.getName());
                    BigDecimal importZhyl = importVo == null ? ZERO : importVo.getZhyl();
                    BigDecimal importZhyy = importVo == null ? ZERO : importVo.getZhyy();

                    BigDecimal grainDiffer = importZhyl.compareTo(ZERO) == 0 ? ZERO :
                            NumberUtil.div(vo.getZhyl(), importZhyl, 2, HALF_UP).abs();

                    BigDecimal oilDiffer = importZhyy.compareTo(ZERO) == 0 ? ZERO :
                            NumberUtil.div(vo.getZhyy(), importZhyy, 2, HALF_UP).abs();

                    return new ReserveGrainOilEquivalentExcelVo()
                            .setName(vo.getName())
                            .setZhyl(vo.getZhyl())
                            .setImportZhyl(importZhyl)
                            .setGrainDiffer(grainDiffer)
                            .setZhyy(vo.getZhyy())
                            .setImportZhyy(importZhyy)
                            .setOilDiffer(oilDiffer);
                }).toList();

        ZtExcelBuildUtil.buildExcelExport(ReserveGrainOilEquivalentExcelVo.class, "储备粮油折合报表导入比对结果")
                .setData(excelVos)
                .doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public GranaryBasisVo granaryBasis(String kqdm) {
        return stockAffiliationMapper.granaryBasis(kqdm);
    }

    @Override
    public List<InventoryMonitoringVo> inventoryMonitoring(InventoryMonitoringQueryParam param) {
        List<InventoryMonitoringVo> vos = new ArrayList<>();
        //物料品种数据
        List<Dict> dicts = dictService.lambdaQuery()
                .eq(Dict::getDictType, LSPZ_DIC)
                .list();
        if (CollectionUtils.isEmpty(dicts)) {
            return vos;
        }
        getMonitoringVos(vos, dicts);
        getCompletionRate(vos);
        return vos;
    }

    private void getCompletionRate(List<InventoryMonitoringVo> vos) {
        vos.forEach(sc -> {
            //计划量
            InventoryMonitoringCityVo plannedQuantity = sc.getPlannedQuantity();
            //实际量
            InventoryMonitoringCityVo realityQuantity = sc.getRealityQuantity();
            //储存完成率
            InventoryMonitoringCityVo vo = new InventoryMonitoringCityVo();
            vo.setSjcb(getRate(plannedQuantity.getSjcb(), realityQuantity.getSjcb()));
            vo.setGzs(getRate(plannedQuantity.getGzs(), realityQuantity.getGzs()));
            vo.setSgs(getRate(plannedQuantity.getSgs(), realityQuantity.getSgs()));
            vo.setSzs(getRate(plannedQuantity.getSzs(), realityQuantity.getSzs()));
            vo.setZhs(getRate(plannedQuantity.getZhs(), realityQuantity.getZhs()));
            vo.setSts(getRate(plannedQuantity.getSts(), realityQuantity.getSts()));
            vo.setFss(getRate(plannedQuantity.getFss(), realityQuantity.getFss()));
            vo.setJms(getRate(plannedQuantity.getJms(), realityQuantity.getJms()));
            vo.setZjs(getRate(plannedQuantity.getZjs(), realityQuantity.getZjs()));
            vo.setMms(getRate(plannedQuantity.getMms(), realityQuantity.getMms()));
            vo.setZqs(getRate(plannedQuantity.getZqs(), realityQuantity.getZqs()));
            vo.setHzs(getRate(plannedQuantity.getHzs(), realityQuantity.getHzs()));
            vo.setMzs(getRate(plannedQuantity.getMzs(), realityQuantity.getMzs()));
            vo.setSws(getRate(plannedQuantity.getSws(), realityQuantity.getSws()));
            vo.setHys(getRate(plannedQuantity.getHys(), realityQuantity.getHys()));
            vo.setYjs(getRate(plannedQuantity.getYjs(), realityQuantity.getYjs()));
            vo.setQys(getRate(plannedQuantity.getQys(), realityQuantity.getQys()));
            vo.setDgs(getRate(plannedQuantity.getDgs(), realityQuantity.getDgs()));
            vo.setZss(getRate(plannedQuantity.getZss(), realityQuantity.getZss()));
            vo.setCss(getRate(plannedQuantity.getCss(), realityQuantity.getCss()));
            vo.setJys(getRate(plannedQuantity.getJys(), realityQuantity.getJys()));
            vo.setYfs(getRate(plannedQuantity.getYfs(), realityQuantity.getYfs()));
            sc.setCompletionRate(vo);
        });
    }

    private BigDecimal getRate(BigDecimal plan, BigDecimal reality) {
        if (reality.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        boolean status = (plan.compareTo(BigDecimal.ZERO) == 0) &&
                (reality.compareTo(BigDecimal.ZERO) > 0);
        if (status) {
            return RATE;
        }
        return reality.divide(plan, 4, BigDecimal.ROUND_CEILING).multiply(RATE);
    }

    /**
     * 粮食实时库存监督
     *
     * @param monitoringVos 粮食实时库存监督数据
     * @param dicts         粮食品种
     */
    private void getMonitoringVos(List<InventoryMonitoringVo> monitoringVos, List<Dict> dicts) {
        List<InventoryMonitoringVo> vos = new ArrayList<>();
        //省级储备
        List<InventoryMonitoringDto> provinceDtos = stockAffiliationMapper.getProvinceStock();
        Map<String, InventoryMonitoringDto> monitoringMap = StreamEx.of(provinceDtos)
                .toMap(InventoryMonitoringDto::getLspzlb, Function.identity());
        //市级储备
        List<InventoryMonitoringDto> cityDtos = stockAffiliationMapper.getCityStock();
        Map<String, List<InventoryMonitoringDto>> lspzlbMap = StreamEx.of(cityDtos)
                .groupingBy(InventoryMonitoringDto::getLspzlb);
        InventoryPlanQueryParam param = new InventoryPlanQueryParam();
        param.setNd(LocalDateTime.now().getYear());
        //查询省级储备计划
        List<InventoryPlanDto> planDtos = stockAffiliationMapper.getPlanDtos(param);
        //查询市级储备计划
        List<InventoryPlanDto> planCityDtos = stockAffiliationMapper.getPlanCityDtos(param);
        Map<String, InventoryPlanDto> planMap = StreamEx.of(planDtos)
                .toMap(InventoryPlanDto::getLspzlbmc, Function.identity());
        Map<String, List<InventoryPlanDto>> planCityMap = StreamEx.of(planCityDtos)
                .groupingBy(InventoryPlanDto::getLspzlbmc);
        dicts.forEach(sc -> {
            //实际量
            InventoryMonitoringVo vo = new InventoryMonitoringVo();
            InventoryMonitoringCityVo cityVo = new InventoryMonitoringCityVo();
            provinceDtos.forEach(scc -> {
                if (monitoringMap.containsKey(scc.getLspzlb())) {
                    cityVo.setSjcb(monitoringMap.get(scc.getLspzlb()).getSjsl());
                }
            });
            getCityVo(lspzlbMap, sc, cityVo);
            //计划量
            InventoryMonitoringCityVo planCityVo = new InventoryMonitoringCityVo();
            planDtos.forEach(scc -> {
                if (monitoringMap.containsKey(scc.getLspzlbmc())) {
                    planCityVo.setSjcb(planMap.get(scc.getLspzlbmc()).getSjsl());
                }
            });
            getPlanCityVo(planCityMap, sc, planCityVo);
            vos.add(vo.setYlpz(sc.getDictKey())
                    .setYlpzName(sc.getDictValue())
                    .setRealityQuantity(cityVo)
                    .setPlannedQuantity(planCityVo));
        });
        InventoryMonitoringVo vo = new InventoryMonitoringVo();
        InventoryMonitoringCityVo totalCityVo = getTotalCityVo(vos);
        InventoryMonitoringCityVo totalPlanCityVo = getTotalPlanCityVo(vos);
        vo.setYlpz(SJCBL_TATLE)
                .setYlpzName(SJCBL_HJ)
                .setRealityQuantity(totalCityVo)
                .setPlannedQuantity(totalPlanCityVo);
        monitoringVos.add(vo);
        monitoringVos.addAll(vos);
    }

    private InventoryMonitoringCityVo getTotalPlanCityVo(List<InventoryMonitoringVo> vos) {
        InventoryMonitoringCityVo vo = new InventoryMonitoringCityVo();
        vo.setSjcb(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getSjcb)
                .reduce(ZERO, BigDecimal::add));
        vo.setGzs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getGzs)
                .reduce(ZERO, BigDecimal::add));
        vo.setSgs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getSgs)
                .reduce(ZERO, BigDecimal::add));
        vo.setSzs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getSzs)
                .reduce(ZERO, BigDecimal::add));
        vo.setZhs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getZhs)
                .reduce(ZERO, BigDecimal::add));
        vo.setSts(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getSts)
                .reduce(ZERO, BigDecimal::add));
        vo.setFss(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getFss)
                .reduce(ZERO, BigDecimal::add));
        vo.setJms(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getJms)
                .reduce(ZERO, BigDecimal::add));
        vo.setZjs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getZjs)
                .reduce(ZERO, BigDecimal::add));
        vo.setMms(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getMms)
                .reduce(ZERO, BigDecimal::add));
        vo.setZqs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getZqs)
                .reduce(ZERO, BigDecimal::add));
        vo.setHzs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getHzs)
                .reduce(ZERO, BigDecimal::add));
        vo.setMzs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getMzs)
                .reduce(ZERO, BigDecimal::add));
        vo.setSws(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getSws)
                .reduce(ZERO, BigDecimal::add));
        vo.setHys(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getHys)
                .reduce(ZERO, BigDecimal::add));
        vo.setYjs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getYjs)
                .reduce(ZERO, BigDecimal::add));
        vo.setQys(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getQys)
                .reduce(ZERO, BigDecimal::add));
        vo.setDgs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getDgs)
                .reduce(ZERO, BigDecimal::add));
        vo.setZss(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getZss)
                .reduce(ZERO, BigDecimal::add));
        vo.setCss(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getCss)
                .reduce(ZERO, BigDecimal::add));
        vo.setJys(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getJys)
                .reduce(ZERO, BigDecimal::add));
        vo.setYfs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getPlannedQuantity)
                .map(InventoryMonitoringCityVo::getYfs)
                .reduce(ZERO, BigDecimal::add));
        return vo;
    }

    private void getPlanCityVo(Map<String, List<InventoryPlanDto>> planCityMap, Dict sc, InventoryMonitoringCityVo cityVo) {
        if (Objects.nonNull(planCityMap) && planCityMap.containsKey(sc.getDictValue())) {
            List<InventoryPlanDto> dtos = planCityMap.get(sc.getDictValue());
            dtos.forEach(d -> {
                if (InventoryMonitoringEnums.GZS.getCode().equals(d.getCityId())) {
                    cityVo.setGzs(d.getSjsl());
                } else if (InventoryMonitoringEnums.SGS.getCode().equals(d.getCityId())) {
                    cityVo.setSgs(d.getSjsl());
                } else if (InventoryMonitoringEnums.SZS.getCode().equals(d.getCityId())) {
                    cityVo.setSzs(d.getSjsl());
                } else if (InventoryMonitoringEnums.ZHS.getCode().equals(d.getCityId())) {
                    cityVo.setZhs(d.getSjsl());
                } else if (InventoryMonitoringEnums.STS.getCode().equals(d.getCityId())) {
                    cityVo.setSts(d.getSjsl());
                } else if (InventoryMonitoringEnums.FSS.getCode().equals(d.getCityId())) {
                    cityVo.setFss(d.getSjsl());
                } else if (InventoryMonitoringEnums.JMS.getCode().equals(d.getCityId())) {
                    cityVo.setJms(d.getSjsl());
                } else if (InventoryMonitoringEnums.ZJS.getCode().equals(d.getCityId())) {
                    cityVo.setZjs(d.getSjsl());
                } else if (InventoryMonitoringEnums.MMS.getCode().equals(d.getCityId())) {
                    cityVo.setMms(d.getSjsl());
                } else if (InventoryMonitoringEnums.ZQS.getCode().equals(d.getCityId())) {
                    cityVo.setZqs(d.getSjsl());
                } else if (InventoryMonitoringEnums.HZS.getCode().equals(d.getCityId())) {
                    cityVo.setHzs(d.getSjsl());
                } else if (InventoryMonitoringEnums.MZS.getCode().equals(d.getCityId())) {
                    cityVo.setMzs(d.getSjsl());
                } else if (InventoryMonitoringEnums.SWS.getCode().equals(d.getCityId())) {
                    cityVo.setSws(d.getSjsl());
                } else if (InventoryMonitoringEnums.HYS.getCode().equals(d.getCityId())) {
                    cityVo.setHys(d.getSjsl());
                } else if (InventoryMonitoringEnums.YJS.getCode().equals(d.getCityId())) {
                    cityVo.setYjs(d.getSjsl());
                } else if (InventoryMonitoringEnums.QYS.getCode().equals(d.getCityId())) {
                    cityVo.setQys(d.getSjsl());
                } else if (InventoryMonitoringEnums.DGS.getCode().equals(d.getCityId())) {
                    cityVo.setDgs(d.getSjsl());
                } else if (InventoryMonitoringEnums.ZSS.getCode().equals(d.getCityId())) {
                    cityVo.setZss(d.getSjsl());
                } else if (InventoryMonitoringEnums.CSS.getCode().equals(d.getCityId())) {
                    cityVo.setCss(d.getSjsl());
                } else if (InventoryMonitoringEnums.JYS.getCode().equals(d.getCityId())) {
                    cityVo.setJys(d.getSjsl());
                } else if (InventoryMonitoringEnums.YFS.getCode().equals(d.getCityId())) {
                    cityVo.setYfs(d.getSjsl());
                }
            });
        }
    }

    private void getCityVo(Map<String, List<InventoryMonitoringDto>> lspzlbMap, Dict sc, InventoryMonitoringCityVo cityVo) {
        if (Objects.nonNull(lspzlbMap) && lspzlbMap.containsKey(sc.getDictValue())) {
            List<InventoryMonitoringDto> dtos = lspzlbMap.get(sc.getDictValue());
            dtos.forEach(d -> {
                if (InventoryMonitoringEnums.GZS.getCode().equals(d.getCityId())) {
                    cityVo.setGzs(d.getSjsl());
                } else if (InventoryMonitoringEnums.SGS.getCode().equals(d.getCityId())) {
                    cityVo.setSgs(d.getSjsl());
                } else if (InventoryMonitoringEnums.SZS.getCode().equals(d.getCityId())) {
                    cityVo.setSzs(d.getSjsl());
                } else if (InventoryMonitoringEnums.ZHS.getCode().equals(d.getCityId())) {
                    cityVo.setZhs(d.getSjsl());
                } else if (InventoryMonitoringEnums.STS.getCode().equals(d.getCityId())) {
                    cityVo.setSts(d.getSjsl());
                } else if (InventoryMonitoringEnums.FSS.getCode().equals(d.getCityId())) {
                    cityVo.setFss(d.getSjsl());
                } else if (InventoryMonitoringEnums.JMS.getCode().equals(d.getCityId())) {
                    cityVo.setJms(d.getSjsl());
                } else if (InventoryMonitoringEnums.ZJS.getCode().equals(d.getCityId())) {
                    cityVo.setZjs(d.getSjsl());
                } else if (InventoryMonitoringEnums.MMS.getCode().equals(d.getCityId())) {
                    cityVo.setMms(d.getSjsl());
                } else if (InventoryMonitoringEnums.ZQS.getCode().equals(d.getCityId())) {
                    cityVo.setZqs(d.getSjsl());
                } else if (InventoryMonitoringEnums.HZS.getCode().equals(d.getCityId())) {
                    cityVo.setHzs(d.getSjsl());
                } else if (InventoryMonitoringEnums.MZS.getCode().equals(d.getCityId())) {
                    cityVo.setMzs(d.getSjsl());
                } else if (InventoryMonitoringEnums.SWS.getCode().equals(d.getCityId())) {
                    cityVo.setSws(d.getSjsl());
                } else if (InventoryMonitoringEnums.HYS.getCode().equals(d.getCityId())) {
                    cityVo.setHys(d.getSjsl());
                } else if (InventoryMonitoringEnums.YJS.getCode().equals(d.getCityId())) {
                    cityVo.setYjs(d.getSjsl());
                } else if (InventoryMonitoringEnums.QYS.getCode().equals(d.getCityId())) {
                    cityVo.setQys(d.getSjsl());
                } else if (InventoryMonitoringEnums.DGS.getCode().equals(d.getCityId())) {
                    cityVo.setDgs(d.getSjsl());
                } else if (InventoryMonitoringEnums.ZSS.getCode().equals(d.getCityId())) {
                    cityVo.setZss(d.getSjsl());
                } else if (InventoryMonitoringEnums.CSS.getCode().equals(d.getCityId())) {
                    cityVo.setCss(d.getSjsl());
                } else if (InventoryMonitoringEnums.JYS.getCode().equals(d.getCityId())) {
                    cityVo.setJys(d.getSjsl());
                } else if (InventoryMonitoringEnums.YFS.getCode().equals(d.getCityId())) {
                    cityVo.setYfs(d.getSjsl());
                }
            });
        }
    }

    private InventoryMonitoringCityVo getTotalCityVo(List<InventoryMonitoringVo> vos) {
        InventoryMonitoringCityVo vo = new InventoryMonitoringCityVo();
        vo.setSjcb(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getSjcb)
                .reduce(ZERO, BigDecimal::add));
        vo.setGzs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getGzs)
                .reduce(ZERO, BigDecimal::add));
        vo.setSgs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getSgs)
                .reduce(ZERO, BigDecimal::add));
        vo.setSzs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getSzs)
                .reduce(ZERO, BigDecimal::add));
        vo.setZhs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getZhs)
                .reduce(ZERO, BigDecimal::add));
        vo.setSts(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getSts)
                .reduce(ZERO, BigDecimal::add));
        vo.setFss(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getFss)
                .reduce(ZERO, BigDecimal::add));
        vo.setJms(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getJms)
                .reduce(ZERO, BigDecimal::add));
        vo.setZjs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getZjs)
                .reduce(ZERO, BigDecimal::add));
        vo.setMms(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getMms)
                .reduce(ZERO, BigDecimal::add));
        vo.setZqs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getZqs)
                .reduce(ZERO, BigDecimal::add));
        vo.setHzs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getHzs)
                .reduce(ZERO, BigDecimal::add));
        vo.setMzs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getMzs)
                .reduce(ZERO, BigDecimal::add));
        vo.setSws(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getSws)
                .reduce(ZERO, BigDecimal::add));
        vo.setHys(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getHys)
                .reduce(ZERO, BigDecimal::add));
        vo.setYjs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getYjs)
                .reduce(ZERO, BigDecimal::add));
        vo.setQys(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getQys)
                .reduce(ZERO, BigDecimal::add));
        vo.setDgs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getDgs)
                .reduce(ZERO, BigDecimal::add));
        vo.setZss(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getZss)
                .reduce(ZERO, BigDecimal::add));
        vo.setCss(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getCss)
                .reduce(ZERO, BigDecimal::add));
        vo.setJys(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getJys)
                .reduce(ZERO, BigDecimal::add));
        vo.setYfs(StreamEx.of(vos)
                .map(InventoryMonitoringVo::getRealityQuantity)
                .map(InventoryMonitoringCityVo::getYfs)
                .reduce(ZERO, BigDecimal::add));
        return vo;
    }

    @Override
    public TemperatureVo temperature(String kqdm) {

        return stockAffiliationMapper.getTemperature(kqdm);
    }

    @Override
    public List<LibraryChainVo> libraryChain(LibraryChainQueryParam queryParam) {
        return stockAffiliationMapper.libraryChain(queryParam);
    }

    @Override
    public List<CargoCalVo> cargoCal(CargoCalQueryParam cargoCalQueryParam) {

        return stockAffiliationMapper.cargoCal(cargoCalQueryParam);
    }

    @Override
    public List<StockHouse> listByRegionId(AreaQueryParam queryParam) {
        return stockHouseService.lambdaQuery()
                .select(StockHouse::getId, StockHouse::getKqdm, StockHouse::getKqmc,
                        StockHouse::getJd, StockHouse::getWd, StockHouse::getHkRegionId)
                .eq(isNotEmpty(queryParam.getProvinceId()), StockHouse::getProvinceId, queryParam.getProvinceId())
                .eq(isNotEmpty(queryParam.getCityId()), StockHouse::getCityId, queryParam.getCityId())
                .eq(isNotEmpty(queryParam.getAreaId()), StockHouse::getAreaId, queryParam.getAreaId())
                .list();
    }

    @DataBindFieldConvert
    @Override
    public CargoCalDtlVo cargoCalDtl(String hwdm) {
        //拿到一级
        List<CargoCalDtlVo> cargoCalDtlVos = stockAffiliationMapper.cargoCalDtl(hwdm);
        if (cargoCalDtlVos.isEmpty()) {
            return null;
        }
        cargoCalDtlVos.forEach(iter -> {
            List<CargoCalDtlNumVo> calDtlNumVos = new ArrayList<>();
            //查询粮食入库
            List<InStock> inStockList = inStockService.lambdaQuery()
                    .eq(isNotEmpty(iter.getHwdm()), InStock::getHwdm, iter.getHwdm())
                    .eq(isNotEmpty(iter.getLspzdm()), InStock::getLspzdm, iter.getLspzdm())
                    .eq(isNotEmpty(iter.getLsxzdm()), InStock::getLsxzdm, iter.getLsxzdm())
                    .eq(isNotEmpty(iter.getShnd()), InStock::getShnd, iter.getShnd()).list();
            inStockList.forEach(inStock -> {
                CargoCalDtlNumVo cargoCalDtlNumVo = new CargoCalDtlNumVo();
                cargoCalDtlNumVo.setRks(inStock.getJz());
                cargoCalDtlNumVo.setZhgxsj(inStock.getYwrq());
                calDtlNumVos.add(cargoCalDtlNumVo);
            });
            //查询粮食出库
            List<OutStock> outStockList = outStockService.lambdaQuery()
                    .eq(isNotEmpty(iter.getHwdm()), OutStock::getHwdm, iter.getHwdm())
                    .eq(isNotEmpty(iter.getLspzdm()), OutStock::getLspzdm, iter.getLspzdm())
                    .eq(isNotEmpty(iter.getLsxzdm()), OutStock::getLsxzdm, iter.getLsxzdm())
                    .eq(isNotEmpty(iter.getShnd()), OutStock::getShnd, iter.getShnd()).list();
            outStockList.forEach(outStock -> {
                CargoCalDtlNumVo cargoCalDtlNumVo = new CargoCalDtlNumVo();
                cargoCalDtlNumVo.setCks(outStock.getJz());
                cargoCalDtlNumVo.setZhgxsj(outStock.getYwrq());
                calDtlNumVos.add(cargoCalDtlNumVo);
            });
            List<CargoCalDtlNumVo> calDtlNumVos1 = calDtlNumVos.stream().sorted(Comparator.comparing(CargoCalDtlNumVo::getZhgxsj).reversed()).collect(Collectors.toList());
            calDtlNumVos1.forEach(calDtlNumVo -> {
                if (calDtlNumVo.getRks() != null) {
                    calDtlNumVo.setJcs(iter.getSjsl());
                    iter.setSjsl(iter.getSjsl().subtract(calDtlNumVo.getRks()));
                }
                if (calDtlNumVo.getCks() != null) {
                    calDtlNumVo.setCks(iter.getSjsl().subtract(calDtlNumVo.getCks()));
                    iter.setSjsl(iter.getSjsl().add(calDtlNumVo.getCks()));
                }

            });
            List<CargoCalDtlNumVo> calDtlNumVos2 = calDtlNumVos1.stream().sorted(Comparator.comparing(CargoCalDtlNumVo::getZhgxsj)
                            .thenComparing(CargoCalDtlNumVo::getJcs))
                    .collect(Collectors.toList());
            iter.setCalDtlNumVos(calDtlNumVos2);
        });
        return cargoCalDtlVos.get(0);
    }

    @Override
    public List<TemperatureVo> temperatureCurve(String hwdm, String kqdm) {
        return stockAffiliationMapper.temperatureCurve(hwdm, kqdm);
    }

    @Override
    public Page<MonitoringPointVo> monitoringPoint(MonitoringPointPageParam pageParam) {
        List<GrainMonitor> grainMonitorList = grainMonitorService.lambdaQuery().eq(GrainMonitor::getHwdm, pageParam.getHwdm()).orderByDesc(GrainMonitor::getZhgxsj).list();
        if (grainMonitorList.isEmpty()) {
            return null;
        }
        GrainMonitor grainMonitor = grainMonitorList.get(0);
        String[] wdArray = grainMonitor.getLswdzjh() == null ? null : grainMonitor.getLswdzjh().split("\\|");
        String[] sdArray = grainMonitor.getLssdzjh() == null ? null : grainMonitor.getLssdzjh().split("\\|");

        List<MonitoringPointVo> monitoringPointVoList = new ArrayList<>();
        Map<String, MonitoringPointVo> resultMap = new HashMap<>();
        //温度
        if (wdArray != null) {
            for (String wd : wdArray) {
                String temp = wd.substring(0, wd.indexOf(','));
                String cjd = wd.substring(wd.indexOf(',') + 1);
                MonitoringPointVo p = new MonitoringPointVo();
                p.setCjd(cjd);
                p.setWd(temp);
                resultMap.put(cjd, p);
            }
        }
        //湿度
        if (sdArray != null) {
            for (String sd : sdArray) {
                String hum = sd.substring(0, sd.indexOf(','));
                String cjd = sd.substring(sd.indexOf(',') + 1);
                if (resultMap.containsKey(cjd)) {
                    MonitoringPointVo h = resultMap.get(cjd);
                    h.setSd(hum);
                    resultMap.put(cjd, h);
                } else {
                    MonitoringPointVo h = new MonitoringPointVo();
                    h.setCjd(cjd);
                    h.setSd(hum);
                    resultMap.put(cjd, h);
                }

            }
        }
        resultMap.forEach((key, value) -> {
            monitoringPointVoList.addAll(resultMap.values());
        });
        Page<MonitoringPointVo> page = new Page<MonitoringPointVo>(pageParam.getPageNum(), pageParam.getPageSize());
        page.setTotal(monitoringPointVoList.size());
        page.setRecords(monitoringPointVoList.subList(0 + (pageParam.getPageNum() - 1) * pageParam.getPageSize(),
                (pageParam.getPageNum()) * pageParam.getPageSize() > monitoringPointVoList.size() ? monitoringPointVoList.size() :
                        (pageParam.getPageNum()) * pageParam.getPageSize()));
        return page;
    }

    @Override
    public void dashboardStatistics() {
        stockAffiliationMapper.syncStockAffiliation();
    }
}