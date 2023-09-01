package com.sydata.chart.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.sydata.basis.domain.Warehouse;
import com.sydata.basis.service.IWarehouseService;
import com.sydata.chart.pojo.Carrier;
import com.sydata.chart.pojo.CheckResult;
import com.sydata.chart.pojo.CustomInformation;
import com.sydata.chart.pojo.Register;
import com.sydata.chart.pojo.cargo.Stock;
import com.sydata.chart.pojo.cargo.StockCheck;
import com.sydata.chart.pojo.in.CheckIn;
import com.sydata.chart.pojo.in.CustomInformationIn;
import com.sydata.chart.pojo.in.WeighingIn;
import com.sydata.chart.pojo.out.PlanNo;
import com.sydata.chart.pojo.out.WeighingOut;
import com.sydata.chart.pojo.quality.People;
import com.sydata.chart.pojo.quality.QualityCheckResult;
import com.sydata.chart.pojo.quality.Sample;
import com.sydata.chart.pojo.steam.PestInfo;
import com.sydata.chart.pojo.steam.SteamHistory;
import com.sydata.chart.pojo.steam.WarehouseInfo;
import com.sydata.chart.pojo.ventilation.BasisVentilation;
import com.sydata.chart.pojo.ventilation.CoolingVentilation;
import com.sydata.chart.pojo.ventilation.ReduceMoistureVentilation;
import com.sydata.chart.service.IChartService;
import com.sydata.chart.vo.*;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.PestInformation;
import com.sydata.manage.domain.QualityInspection;
import com.sydata.manage.domain.SteamTaskInformation;
import com.sydata.manage.domain.Ventilation;
import com.sydata.manage.service.IPestInformationService;
import com.sydata.manage.service.IQualityInspectionService;
import com.sydata.manage.service.ISteamTaskInformationService;
import com.sydata.manage.service.IVentilationService;
import com.sydata.trade.domain.*;
import com.sydata.trade.service.*;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static jodd.util.StringPool.COMMA;
import static jodd.util.StringPool.HASH;

/**
 * 卡片图表Service业务层处理
 *
 * @author lzq
 * @date 2022-10-11
 */

@Service("charService")
public class CharServiceImpl implements IChartService {

    @Resource
    private IWarehouseService warehouseService;

    @Resource
    private IQualityInspectionService qualityInspectionService;

    @Resource
    private IStockGrainService stockGrainService;

    @Resource
    private IInStockService inStockService;

    @Resource
    private IContractService contractService;

    @Resource
    private IInStockSettlementService iInStockSettlementService;

    @Resource
    private IInStockCheckService inStockCheckService;

    @Resource
    private IOutStockService outStockService;

    @Resource
    private IOutStockSettlementService outStockSettlementService;

    @Resource
    private ILossService lossService;

    @Resource
    private ITransferNatureService transferNatureService;

    @Resource
    private IVentilationService ventilationService;

    @Resource
    private ISteamTaskInformationService steamTaskInformationService;

    @Resource
    private IPestInformationService pestInformationService;


    @Override
    public QualityCheckChartVo qualityCheckChart(String id) {
        QualityInspection qualityInspection = qualityInspectionService.getById(id);
        //截取仓房代码
        String cfdm = qualityInspection.getHwdm().substring(0, 25);
        Sample sample = BeanUtils.copyByClass(qualityInspection, Sample.class);
        sample.setCfdm(cfdm);
        dealSample(sample);

        QualityCheckChartVo qualityCheckChartVo = new QualityCheckChartVo()
                .setJydw(qualityInspection.getJydw())
                .setZjry(BeanUtils.copyByClass(qualityInspection, People.class))
                .setZjjg(dealCheckResult(qualityInspection))
                .setBz(qualityInspection.getBz())
                .setYpxx(sample)
                .setLspz(sample.getLspzdmTopkey())
                .setBm(qualityInspection.getZjbgdh());
        DataBindHandleBootstrap.dataHandConvert(qualityCheckChartVo);
        return qualityCheckChartVo;
    }


    @Override
    public InStockCheckChartVo inStockCheckChart(String id) {
        InStock inStock = inStockService.getById(id);
        InStockCheckChartVo inStockCheckChartVo = BeanUtils.copyByClass(inStock, InStockCheckChartVo.class);
        //客户及结算信息
        CustomInformationIn customInformationIn = new CustomInformationIn();
        if (inStock.getRkjsdh() == null) {
            //合同结算模式
            Contract contract = contractService.getById(inStock.getHth());
            customInformationIn = BeanUtils.copyByClass(contract, CustomInformationIn.class);
            customInformationIn.setDj(contract.getJsjg() != null ? contract.getJsjg() : contract.getHtdj())
                    .setZje(contract.getJszje() != null ? contract.getJszje() : contract.getHtzje());
        } else {
            //一车一结模式
            InStockSettlement inStockSettlement = iInStockSettlementService.getById(inStock.getRkjsdh());
            customInformationIn
                    .setHth(inStockSettlement.getHth() == null ? "一车一结模式" : inStockSettlement.getHth())
                    .setDj(inStockSettlement.getJsdj())
                    .setKhfkhh(inStockSettlement.getKhhh() == null ? "现金" : inStockSettlement.getKhhh())
                    .setKhfzh(inStockSettlement.getYhzh() == null ? "现金" : inStockSettlement.getYhzh())
                    .setZje(inStockSettlement.getJsje())
                    .setKhmc(inStockSettlement.getSkr())
                    .setKhtyshxydm(inStockSettlement.getSkrsfzh());
        }
        //检验信息
        CheckIn checkIn = new CheckIn();
        //根据货位代码和入库业务单号获取入库检验
        InStockCheck inStockCheck = inStockCheckService.lambdaQuery().select(InStockCheck::getId,
                        InStockCheck::getLspzdm,
                        InStockCheck::getLsdd,
                        InStockCheck::getJyxm,
                        InStockCheck::getJyrxm,
                        InStockCheck::getJysj,
                        InStockCheck::getQyrxm, InStockCheck::getJyz)
                .eq(InStockCheck::getHwdm, inStock.getHwdm()).eq(InStockCheck::getRkywdh, inStock.getRkywdh()).one();
        if (inStockCheck != null) {
            checkIn.setLspzdm(inStockCheck.getLspzdm())
                    .setLsdjdm(inStockCheck.getLsdd())
                    .setCheckResults(getCheckResult(inStockCheck.getJyxm(), inStockCheck.getJyz()));
            inStockCheckChartVo
                    .setQyrxm(inStockCheck.getQyrxm())
                    .setJyrxm(inStockCheck.getJyrxm())
                    .setJysj(inStockCheck.getJysj());
        }

        //实际数量取毛重-皮重
        WeighingIn weighingIn = BeanUtils.copyByClass(inStock, WeighingIn.class);
        weighingIn.setSjsl(weighingIn.getMz().subtract(weighingIn.getPz()));
        inStockCheckChartVo
                .setCfdm(inStockCheckChartVo.getHwdm().substring(0, 25))
                .setKhjjs(customInformationIn)
                .setCy(BeanUtils.copyByClass(inStock, Carrier.class))
                .setDj(BeanUtils.copyByClass(inStock, Register.class))
                .setJj(weighingIn)
                .setJy(checkIn);

        DataBindHandleBootstrap.dataHandConvert(inStockCheckChartVo);
        return inStockCheckChartVo;
    }


    @Override
    public OutStockCheckChartVo outStockCheckChart(String id) {

        OutStock outStock = outStockService.getById(id);

        OutStockCheckChartVo outStockCheckChartVo = BeanUtils.copyByClass(outStock, OutStockCheckChartVo.class);
        //客户信息
        CustomInformation customInformation;
        Contract contract;
        if (outStock.getCkjsdh() == null) {
            //合同结算模式
            contract = contractService.getById(outStock.getHth());

        } else {
            //一车一结模式
            OutStockSettlement outStockSettlement = outStockSettlementService.getById(outStock.getCkjsdh());
            //因为合同号是出库结算中必填的，所以还是从合同中取
            contract = contractService.getById(outStockSettlement.getHth());
        }
        customInformation = BeanUtils.copyByClass(contract, CustomInformation.class);

        //检斤,取计价数量=净重
        WeighingOut weighingOut = BeanUtils.copyByClass(outStock, WeighingOut.class);
        weighingOut.setJjsl(weighingOut.getJz());
        outStockCheckChartVo
                .setKh(customInformation)
                .setCfdm(outStock.getHwdm().substring(0, 25))
                .setJhtz(new PlanNo().setCktzdh(outStock.getCktzdh()).setHth(contract.getHth()))
                .setCy(BeanUtils.copyByClass(outStock, Carrier.class))
                .setDj(BeanUtils.copyByClass(outStock, Register.class))
                .setJj(weighingOut);
        DataBindHandleBootstrap.dataHandConvert(outStockCheckChartVo);
        return outStockCheckChartVo;
    }


    @Override
    public CargoChartVo cargoChart(String id) {
        //获取仓房信息
        Warehouse warehouse = warehouseService.getById(id.substring(0, 25));
        CargoChartVo cargoChartVo = BeanUtils.copyByClass(warehouse, CargoChartVo.class);
        cargoChartVo.setHwdm(id);
        List<StockGrain> stockGrains = stockGrainService.lambdaQuery()
                .eq(StockGrain::getHwdm, id).gt(StockGrain::getJjsl, BigDecimal.ZERO).list();
        if (CollectionUtil.isNotEmpty(stockGrains)) {
            List<Stock> kcxx = BeanUtils.copyToList(stockGrains, Stock.class);
            CollectionUtil.sort(stockGrains, Comparator.comparing(StockGrain::getZhgxsj));
            cargoChartVo.setHwzt(stockGrains.get(0).getHwzt());
            cargoChartVo.setKcxx(kcxx);
        }

        List<QualityInspection> qualityInspections = qualityInspectionService.lambdaQuery()
                .select(QualityInspection::getJydw, QualityInspection::getQysj, QualityInspection::getJylb)
                .eq(QualityInspection::getHwdm, id)
                .ge(QualityInspection::getJysj, LocalDateTime.now().minusYears(1L))
                .le(QualityInspection::getJysj, LocalDateTime.now())
                .list();
        if (CollectionUtil.isNotEmpty(qualityInspections)) {
            List<StockCheck> jyjl = BeanUtils.copyToList(qualityInspections, StockCheck.class);
            CollectionUtil.sort(jyjl, Comparator.comparing(StockCheck::getQysj));
            cargoChartVo.setJyjl(jyjl);
        }
        DataBindHandleBootstrap.dataHandConvert(cargoChartVo);
        return cargoChartVo;
    }


    @Override
    public LossChartVo lossChart(String id) {
        Loss loss = lossService.getById(id);
        LossChartVo lossChartVo = BeanUtils.copyByClass(loss, LossChartVo.class);
        lossChartVo.setCfdm(id.substring(0, 25));
        return lossChartVo;
    }


    @Override
    public TransferNatureChartVo transferNatureChar(String id) {
        TransferNature transferNature = transferNatureService.getById(id);
        TransferNatureChartVo transferNatureChartVo = BeanUtils.copyByClass(transferNature, TransferNatureChartVo.class);
        transferNatureChartVo.setCfdm(id.substring(0, 25));
        DataBindHandleBootstrap.dataHandConvert(transferNatureChartVo);
        return transferNatureChartVo;
    }


    @Override
    public VentilationChartVo ventilationChart(String id) {
        Ventilation ventilation = ventilationService.getById(id);
        VentilationChartVo ventilationChartVo = BeanUtils.copyByClass(ventilation, VentilationChartVo.class);
        BasisVentilation basisVentilation = BeanUtils.copyByClass(ventilation, BasisVentilation.class);
        //获取仓内粮食品种数量
        List<StockGrain> stockGrains = stockGrainService.lambdaQuery().select(StockGrain::getId)
                .likeRight(StockGrain::getHwdm, basisVentilation.getCfdm()).gt(StockGrain::getJjsl, BigDecimal.ZERO)
                .groupBy(StockGrain::getLspzdm).list();
        basisVentilation.setPzsl(stockGrains.size());
        //获取仓房基础信息
        Warehouse warehouse = warehouseService.getById(basisVentilation.getCfdm());
        BeanUtils.copyProperties(warehouse, basisVentilation);
        ventilationChartVo.setJcpz(basisVentilation)
                .setTfzyjwtf(BeanUtils.copyByClass(ventilation, CoolingVentilation.class))
                .setTfzyjstf(BeanUtils.copyByClass(ventilation, ReduceMoistureVentilation.class));
        DataBindHandleBootstrap.dataHandConvert(ventilationChartVo);
        return ventilationChartVo;
    }


    @Override
    public SteamTaskChartVo steamTaskChart(String id) {
        SteamTaskInformation steamTaskInformation = steamTaskInformationService.getById(id);
        String cfdm = steamTaskInformation.getCfdm();
        SteamTaskChartVo steamTaskChartVo = BeanUtils.copyByClass(steamTaskInformation, SteamTaskChartVo.class);
        //获取粮仓基本情况
        WarehouseInfo warehouseInfo = getWarehouseInfo(cfdm);
        //获取虫害信息
        PestInformation pestInformation = pestInformationService.getById(steamTaskInformation.getHcjcdh());
        steamTaskChartVo.setLcjbqk(warehouseInfo);
        if (pestInformation != null) {
            steamTaskChartVo.setChfsqk(BeanUtils.copyByClass(pestInformation, PestInfo.class));
        }
        DataBindHandleBootstrap.dataHandConvert(steamTaskChartVo);
        return steamTaskChartVo;
    }


    /**
     * 获取检验项目返回结果集
     *
     * @param jyxm  检验项目，英文逗号分割
     * @param jyxmz 检验项目值，英文逗号分割，与检验项目一一对应
     * @return CheckResult 检验结果集
     */
    private List<CheckResult> getCheckResult(String jyxm, String jyxmz) {

        String[] jyxms = jyxm.split(COMMA);
        String[] jyxmzs = jyxmz.split(COMMA);
        List<CheckResult> checkResultList = new ArrayList<>();
        for (int i = 0; i < jyxms.length; i++) {
            CheckResult checkResult = new CheckResult();
            checkResult.setJyxm(jyxms[i]).setJyxmz(jyxmzs[i]);
            checkResultList.add(checkResult);
        }
        return checkResultList;
    }


    /**
     * 处理样品信息聚合结果
     *
     * @param sample 样品信息
     */
    private void dealSample(Sample sample) {
        List<StockGrain> stockGrains = stockGrainService.lambdaQuery().select(StockGrain::getHwdm,
                        StockGrain::getLsxzdm,
                        StockGrain::getLspzdm,
                        StockGrain::getJjsl,
                        StockGrain::getCd,
                        StockGrain::getShnd)
                .likeRight(StockGrain::getHwdm, sample.getCfdm()).gt(StockGrain::getJjsl, BigDecimal.ZERO).list();

        List<StockGrain> stockGrainList = stockGrains
                .stream()
                .filter(v -> v.getHwdm().equals(sample.getHwdm()) && v.getLspzdm().equals(sample.getLspzdm()))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(stockGrainList)) {
            //获取仓内粮食数量
            sample.setCnlssl(stockGrains
                    .stream()
                    .map(StockGrain::getJjsl)
                    .reduce(BigDecimal::add)
                    .get()
                    .divide(BigDecimal.valueOf(1000), 6, BigDecimal.ROUND_HALF_UP));
            StockGrain stockGrain = stockGrainList.get(0);
            sample.setCd(stockGrain.getCd())
                    .setShnd(stockGrain.getShnd())
                    .setLsxzdm(stockGrain.getLsxzdm());
        }
        DataBindHandleBootstrap.dataHandConvert(sample);
    }

    /**
     * 获取质检报告结果集
     *
     * @param qualityInspection 质检信息
     * @return 质检结果集
     */
    private List<QualityCheckResult> dealCheckResult(QualityInspection qualityInspection) {
        String[] jyxm = qualityInspection.getJyxm().split(COMMA);
        String[] jyxmz = qualityInspection.getJyxmz().split(COMMA);
        List<QualityCheckResult> qualityCheckResults = new ArrayList<>();
        for (int i = 0; i < jyxm.length; i++) {
            QualityCheckResult qualityCheckResult = new QualityCheckResult();
            qualityCheckResult.setJyxm(jyxm[i]).setJyxmz(jyxmz[i]);
            qualityCheckResults.add(qualityCheckResult);
        }
        DataBindHandleBootstrap.dataHandConvert(qualityCheckResults);
        return qualityCheckResults;
    }

    private WarehouseInfo getWarehouseInfo(String cfdm) {
        WarehouseInfo warehouseInfo = new WarehouseInfo();
        List<StockGrain> stockGrains = stockGrainService.lambdaQuery().select(StockGrain::getHwdm,
                        StockGrain::getLspzdm,
                        StockGrain::getJjsl,
                        StockGrain::getShnd,
                        StockGrain::getRcsj)
                .likeRight(StockGrain::getHwdm, cfdm).gt(StockGrain::getJjsl, BigDecimal.ZERO).list();
        if (CollectionUtil.isNotEmpty(stockGrains)) {
            //获取仓内粮食数量
            warehouseInfo.setSl(stockGrains.stream()
                    .map(StockGrain::getJjsl)
                    .reduce(BigDecimal::add)
                    .get()
                    .divide(BigDecimal.valueOf(1000), 6, BigDecimal.ROUND_HALF_UP));
            //获取粮食品种
            StringJoiner stringJoiner = new StringJoiner(HASH);
            StreamEx.of(stockGrains).groupingBy(StockGrain::getLspzdm).keySet().forEach(stringJoiner::add);
            warehouseInfo.setLspzdm(stringJoiner.toString());
            CollectionUtil.sort(stockGrains, Comparator.comparing(v -> Integer.valueOf(v.getShnd())));
            //取当前年份，计算收获年限和入库年限
            int year = LocalDateTime.now().getYear();
            warehouseInfo.setShnx(year - Integer.parseInt(stockGrains.get(0).getShnd()) + 1);
            CollectionUtil.sort(stockGrains, Comparator.comparing(StockGrain::getRcsj));
            warehouseInfo.setRknx(year - Integer.parseInt(stockGrains.get(0).getShnd()) + 1);
        }
        //历年熏蒸情况
        List<SteamTaskInformation> steamTaskInformationList = steamTaskInformationService.lambdaQuery()
                .select(SteamTaskInformation::getDjcxz,
                        SteamTaskInformation::getZyyl,
                        SteamTaskInformation::getSyff
                )
                .eq(SteamTaskInformation::getCfdm, cfdm).list();
        List<SteamHistory> steamHistoryList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(steamTaskInformationList)) {
            steamHistoryList = BeanUtils.copyToList(steamTaskInformationList, SteamHistory.class);
            CollectionUtil.sort(steamHistoryList, Comparator.comparing(SteamHistory::getDjcxz));
        }
        warehouseInfo.setCfdm(cfdm).setLnxzqk(steamHistoryList);
        return warehouseInfo;
    }

}
