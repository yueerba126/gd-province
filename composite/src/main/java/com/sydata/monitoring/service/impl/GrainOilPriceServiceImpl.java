package com.sydata.monitoring.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Cargo;
import com.sydata.basis.domain.Granary;
import com.sydata.basis.domain.Warehouse;
import com.sydata.basis.service.ICargoService;
import com.sydata.basis.service.IGranaryService;
import com.sydata.basis.service.IWarehouseService;
import com.sydata.chart.service.IChartService;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.*;
import com.sydata.monitoring.mapper.GrainOilPriceMapper;
import com.sydata.monitoring.service.ICheckPointService;
import com.sydata.monitoring.service.IGrainOilPriceDtlService;
import com.sydata.monitoring.service.IGrainOilPriceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.monitoring.vo.*;
import com.sydata.organize.enums.OrganizeKindEnum;
import com.sydata.organize.security.UserSecurity;
import com.sydata.trade.domain.Contract;
import com.sydata.trade.domain.InStockSettlement;
import com.sydata.trade.domain.OutStockSettlement;
import com.sydata.trade.param.InStockPageParam;
import com.sydata.trade.param.OutStockPageParam;
import com.sydata.trade.service.*;
import com.sydata.trade.vo.InStockVo;
import com.sydata.trade.vo.OutStockVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 流通检测-粮油价格采集明主表 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Service
public class GrainOilPriceServiceImpl extends ServiceImpl<GrainOilPriceMapper, GrainOilPrice> implements IGrainOilPriceService {

    @Resource
    private IInStockService inStockService;

    @Resource
    private IOutStockService outStockService;

    @Resource
    private IChartService chartService;

    @Resource
    private ICheckPointService checkPointService;

    @Resource
    private IGrainOilPriceDtlService grainOilPriceDtlService;

    @Resource
    private IInStockSettlementService inStockSettlementService;

    @Resource
    private IOutStockSettlementService outStockSettlementService;

    @Resource
    private IContractService contractService;

    @Resource
    private ICargoService cargoService;

    @Resource
    private IGranaryService granaryService;

    @Resource
    private IWarehouseService warehouseService;

    @Override
    public Page<GrainOilPriceDtlVo> detailPagination(GrainOilPriceQueryDto queryDto) {
        String userId = UserSecurity.userId();

        Optional<CheckPoint> checkPointOpt = checkPointService.lambdaQuery()
                .eq(CheckPoint::getAccountUserId, userId)
                .oneOpt();

        // 监测点账号或行政组织可放行查询
        boolean ifAdminTenant = OrganizeKindEnum.ADMIN.getKind().equals(UserSecurity.loginUser().getOrganizeKind());
        Assert.state(checkPointOpt.isPresent() || ifAdminTenant, "当前账号没有任何监测点权限");

        String pointId = checkPointOpt.map(CheckPoint::getId).orElse(null);

        Set<String> mainIds = lambdaQuery()
                .eq(checkPointOpt.isPresent(), GrainOilPrice::getPointId, pointId)
                .eq(queryDto.getEnterpriseId() != null, GrainOilPrice::getEnterpriseId, queryDto.getEnterpriseId())
                .eq(queryDto.getStockHouseId() != null, GrainOilPrice::getStockHouseId, queryDto.getStockHouseId())
                .list()
                .stream()
                .map(GrainOilPrice::getId)
                .collect(Collectors.toSet());

        GrainOilPriceDtlQueryDto dtlQueryDto = new GrainOilPriceDtlQueryDto();
        dtlQueryDto.setMainIds(mainIds);
        dtlQueryDto.setLspzdm(queryDto.getLspzdm());
        dtlQueryDto.setBeginBillDate(queryDto.getBeginBillDate());
        dtlQueryDto.setEndBillDate(queryDto.getEndBillDate());
        dtlQueryDto.setPageNum(queryDto.pageNum);
        dtlQueryDto.setPageSize(queryDto.getPageSize());

        return grainOilPriceDtlService.pagination(dtlQueryDto);
    }

    @Override
    public Boolean add(GrainOilPriceAddDto addDto) {
        addDto.setCreateBy(UserSecurity.userName());
        addDto.setCreateTime(LocalDateTime.now());
        addDto.setUpdateBy(UserSecurity.userName());
        addDto.setUpdateTime(LocalDateTime.now());
        addDto.setCountryId(UserSecurity.loginUser().getCountryId());

        save(addDto);

        List<GrainOilPriceDtl> details = addDto.getDetails();
        for (GrainOilPriceDtl detail : details) {
            detail.setCreateBy(UserSecurity.userName());
            detail.setCreateTime(LocalDateTime.now());
            detail.setUpdateBy(UserSecurity.userName());
            detail.setUpdateTime(LocalDateTime.now());
            detail.setCountryId(UserSecurity.loginUser().getCountryId());
        }
        return grainOilPriceDtlService.saveBatch(details);
    }

    @Override
    public Boolean remove(GrainOilPriceRemoveDto removeDto) {
        // 删除明细列表
        grainOilPriceDtlService.lambdaUpdate()
                .in(GrainOilPriceDtl::getMainId, removeDto.getIds())
                .remove();

        return removeByIds(removeDto.getIds());
    }

    @DataBindFieldConvert
    @Override
    public Page<MonitoringInStockSettlementVo> inBillPage(GrainOilPriceBillQueryDto queryDto) {
        InStockPageParam inStockPageParam = new InStockPageParam();
        inStockPageParam.setPageNum(queryDto.pageNum);
        inStockPageParam.setPageSize(queryDto.getPageSize());
        Page<InStockVo> page = inStockService.pages(inStockPageParam);

        List<InStockVo> inStockVos = page.getRecords();

        Page<MonitoringInStockSettlementVo> inStockSettlementVoPage = BeanUtils.copyToPage(page, MonitoringInStockSettlementVo.class);

        if (CollectionUtils.isEmpty(inStockVos)) {
            return inStockSettlementVoPage;
        }

        // 获取入库关联信息
        StockRelationInfo stockRelationInfo = getInStockInfo(inStockVos);

        Map<String, String> cargoGranaryCodeMap = stockRelationInfo.getCargoGranaryCodeMap();
        Map<String, Granary> granaryMap = stockRelationInfo.getGranaryMap();
        Map<String, Warehouse> warehouseMap = stockRelationInfo.getWarehouseMap();

        // 构建入库结算VO列表
        List<MonitoringInStockSettlementVo> inStockSettlementVos = inStockVos.stream().map(inStockVo -> {
            MonitoringInStockSettlementVo monitoringVo = new MonitoringInStockSettlementVo(inStockVo);

            //客户及结算信息
            if (inStockVo.getRkjsdh() == null) {
                //合同结算模式
                Contract contract = stockRelationInfo.getContractMap().get(inStockVo.getHth());
                monitoringVo.setQty(contract.getLysl());
                monitoringVo.setPrice(contract.getJsjg() != null ? contract.getJsjg() : contract.getHtdj());
                monitoringVo.setAmount(contract.getJszje() != null ? contract.getJszje() : contract.getHtzje());
            } else {
                //一车一结模式
                InStockSettlement inStockSettlement = stockRelationInfo.getInStockSettlementMap().get(inStockVo.getRkjsdh());

                monitoringVo.setQty(inStockSettlement.getJssl());
                monitoringVo.setPrice(inStockSettlement.getJsdj());
                monitoringVo.setAmount(inStockSettlement.getJsje());
            }

            // 匹配廒间和仓房信息
            String hwdm = monitoringVo.getHwdm();

            if (cargoGranaryCodeMap.containsKey(hwdm)) {
                String ajdm = cargoGranaryCodeMap.get(hwdm);
                Optional<Granary> granaryOpt = Optional.ofNullable(granaryMap.get(ajdm));
                monitoringVo.setAjdm(ajdm);
                monitoringVo.setAjmc(granaryOpt.map(Granary::getAjmc).orElse(null));
                monitoringVo.setCfdm(granaryOpt.map(Granary::getCfbh).orElse(null));

                Optional<Warehouse> warehouse = Optional.ofNullable(warehouseMap.get(monitoringVo.getCfdm()));
                monitoringVo.setCfmc(warehouse.map(Warehouse::getCfdm).orElse(null));
                monitoringVo.setKqdm(warehouse.map(Warehouse::getKqdm).orElse(null));
            }

            return monitoringVo;
        }).collect(Collectors.toList());

        inStockSettlementVoPage.setRecords(inStockSettlementVos);

        return inStockSettlementVoPage;
    }

    @DataBindFieldConvert
    @Override
    public Page<MonitoringOutStockSettlementVo> outBillPage(GrainOilPriceBillQueryDto queryDto) {
        OutStockPageParam outStockPageParam = new OutStockPageParam();
        outStockPageParam.setPageNum(queryDto.pageNum);
        outStockPageParam.setPageSize(queryDto.getPageSize());

        Page<OutStockVo> page = outStockService.pages(outStockPageParam);

        List<OutStockVo> outStockVos = page.getRecords();

        Page<MonitoringOutStockSettlementVo> outStockSettlementVoPage = BeanUtils.copyToPage(page, MonitoringOutStockSettlementVo.class);

        if (CollectionUtils.isEmpty(outStockVos)) {
            return outStockSettlementVoPage;
        }

        // 获取出库信息
        StockRelationInfo stockRelationInfo = getOutStockInfo(outStockVos);

        Map<String, String> cargoGranaryCodeMap = stockRelationInfo.getCargoGranaryCodeMap();
        Map<String, Granary> granaryMap = stockRelationInfo.getGranaryMap();
        Map<String, Warehouse> warehouseMap = stockRelationInfo.getWarehouseMap();

        // 构建入库结算VO列表
        List<MonitoringOutStockSettlementVo> outStockSettlementVos = outStockVos.stream().map(outStockVo -> {
            MonitoringOutStockSettlementVo monitoringVo = new MonitoringOutStockSettlementVo(outStockVo);

            //客户及结算信息
            if (outStockVo.getCkjsdh() == null) {
                //合同结算模式
                Contract contract = stockRelationInfo.getContractMap().get(outStockVo.getHth());
                monitoringVo.setQty(contract.getLysl());
                monitoringVo.setPrice(contract.getJsjg() != null ? contract.getJsjg() : contract.getHtdj());
                monitoringVo.setAmount(contract.getJszje() != null ? contract.getJszje() : contract.getHtzje());
            } else {
                //一车一结模式
                OutStockSettlement outStockSettlement = stockRelationInfo.getOutStockSettlementMap().get(outStockVo.getCkjsdh());

                monitoringVo.setQty(outStockSettlement.getJssl());
                monitoringVo.setPrice(outStockSettlement.getJsdj());
                monitoringVo.setAmount(outStockSettlement.getJsje());
            }

            // 匹配廒间和仓房信息
            String hwdm = monitoringVo.getHwdm();

            if (cargoGranaryCodeMap.containsKey(hwdm)) {
                String ajdm = cargoGranaryCodeMap.get(hwdm);
                Optional<Granary> granaryOpt = Optional.ofNullable(granaryMap.get(ajdm));
                monitoringVo.setAjdm(ajdm);
                monitoringVo.setAjmc(granaryOpt.map(Granary::getAjmc).orElse(null));
                monitoringVo.setCfdm(granaryOpt.map(Granary::getCfbh).orElse(null));

                Optional<Warehouse> warehouse = Optional.ofNullable(warehouseMap.get(monitoringVo.getCfdm()));
                monitoringVo.setCfmc(warehouse.map(Warehouse::getCfdm).orElse(null));
                monitoringVo.setKqdm(warehouse.map(Warehouse::getKqdm).orElse(null));
            }

            return monitoringVo;
        }).collect(Collectors.toList());

        outStockSettlementVoPage.setRecords(outStockSettlementVos);

        return outStockSettlementVoPage;
    }

    /**
     * 获取出库关联信息
     *
     * @param outStockVos 出库信息列表
     * @return 结果
     */
    private StockRelationInfo getOutStockInfo(List<OutStockVo> outStockVos) {
        //---------查询关联的结算信息----------
        Map<Boolean, List<OutStockVo>> ifContractModeInStockVosMap = outStockVos.stream()
                .collect(Collectors.groupingBy(inStock -> inStock.getCkjsdh() == null));

        // 按合同结算模式
        List<OutStockVo> contractModeOutStockVoList = ifContractModeInStockVosMap.get(true);
        // 按车次结算模式的入库单
        List<OutStockVo> settleCarTripsModeOutStockVoList = ifContractModeInStockVosMap.get(false);

        Map<String, Contract> contractMap;
        Map<String, OutStockSettlement> outStockSettlementMap;

        // 按合同结算模式的出库单
        if (CollectionUtils.isNotEmpty(contractModeOutStockVoList)) {
            Set<String> hthSet = contractModeOutStockVoList.stream().map(OutStockVo::getHth).collect(Collectors.toSet());
            contractMap = contractService.lambdaQuery()
                    .in(Contract::getId, hthSet)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(Contract::getId, Function.identity(), (preVal, postVal) -> preVal));

        } else {
            contractMap = Collections.emptyMap();
        }

        // 按车次结算模式的出库单
        if (CollectionUtils.isNotEmpty(settleCarTripsModeOutStockVoList)) {
            Set<String> ckjsdhSet = settleCarTripsModeOutStockVoList.stream().map(OutStockVo::getCkjsdh).collect(Collectors.toSet());

            outStockSettlementMap = outStockSettlementService.lambdaQuery()
                    .in(OutStockSettlement::getId, ckjsdhSet)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(OutStockSettlement::getId, Function.identity(), (preVal, postVal) -> preVal));

        } else {
            outStockSettlementMap = Collections.emptyMap();
        }

        //---------查询关联的仓房和廒间信息----------
        Set<String> hwdmSet = outStockVos.stream()
                .map(OutStockVo::getHwdm)
                .collect(Collectors.toSet());

        StockRelationInfo stockRelationInfo = buildStockInfo(hwdmSet);

        stockRelationInfo.setContractMap(contractMap);
        stockRelationInfo.setOutStockSettlementMap(outStockSettlementMap);
        return stockRelationInfo;
    }


    /**
     * 获取入库关联信息
     *
     * @param inStockVos 入库信息列表
     * @return 结果
     */
    private StockRelationInfo getInStockInfo(List<InStockVo> inStockVos) {
        //---------查询关联的结算信息----------

        Map<Boolean, List<InStockVo>> ifContractModeInStockVosMap = inStockVos.stream()
                .collect(Collectors.groupingBy(inStock -> inStock.getRkjsdh() == null));

        // 按合同结算模式
        List<InStockVo> contractModeInStockVoList = ifContractModeInStockVosMap.get(true);
        // 按车次结算模式的入库单
        List<InStockVo> settleCarTripsModeInStockVoList = ifContractModeInStockVosMap.get(false);

        Map<String, Contract> contractMap;
        Map<String, InStockSettlement> inStockSettlementMap;

        if (CollectionUtils.isNotEmpty(contractModeInStockVoList)) {
            Set<String> hthSet = contractModeInStockVoList.stream().map(InStockVo::getHth).collect(Collectors.toSet());
            contractMap = contractService.lambdaQuery()
                    .in(Contract::getId, hthSet)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(Contract::getId, Function.identity(), (preVal, postVal) -> preVal));

        } else {
            contractMap = Collections.emptyMap();
        }

        if (CollectionUtils.isNotEmpty(settleCarTripsModeInStockVoList)) {
            Set<String> rkjsdhSet = settleCarTripsModeInStockVoList.stream().map(InStockVo::getRkjsdh).collect(Collectors.toSet());

            inStockSettlementMap = inStockSettlementService.lambdaQuery()
                    .in(InStockSettlement::getId, rkjsdhSet)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(InStockSettlement::getId, Function.identity(), (preVal, postVal) -> preVal));

        } else {
            inStockSettlementMap = Collections.emptyMap();
        }

        //---------查询关联的仓房和廒间信息----------

        Set<String> hwdmSet = inStockVos.stream()
                .map(InStockVo::getHwdm)
                .collect(Collectors.toSet());

        StockRelationInfo stockRelationInfo = buildStockInfo(hwdmSet);

        stockRelationInfo.setContractMap(contractMap);
        stockRelationInfo.setInStockSettlementMap(inStockSettlementMap);
        return stockRelationInfo;
    }


    private StockRelationInfo buildStockInfo(Set<String> hwdmSet) {
        StockRelationInfo stockRelationInfo = new StockRelationInfo();

        Map<String, String> cargoGranaryCodeMap = cargoService.lambdaQuery()
                .in(Cargo::getHwdm, hwdmSet)
                .list()
                .stream()
                .collect(Collectors.toMap(Cargo::getHwdm, Cargo::getAjdm));

        stockRelationInfo.setCargoGranaryCodeMap(cargoGranaryCodeMap);

        if (MapUtils.isNotEmpty(cargoGranaryCodeMap)) {

            Collection<String> ajdmCollection = cargoGranaryCodeMap.values();

            Map<String, Granary> granaryMap = granaryService.lambdaQuery()
                    .in(Granary::getAjdh, ajdmCollection)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(Granary::getAjdh, Function.identity(), (preVal, postVal) -> preVal));

            stockRelationInfo.setGranaryMap(granaryMap);

            if (MapUtils.isNotEmpty(granaryMap)) {
                Set<String> cfdmSet = granaryMap.values().stream().map(Granary::getCfbh).collect(Collectors.toSet());

                Map<String, Warehouse> warehouseMap = warehouseService.lambdaQuery()
                        .in(Warehouse::getCfdm, cfdmSet)
                        .list()
                        .stream()
                        .collect(Collectors.toMap(Warehouse::getCfdm, Function.identity(), (preVal, postVal) -> preVal));

                stockRelationInfo.setWarehouseMap(warehouseMap);
            }
        }

        return stockRelationInfo;
    }
}
