package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.entity.MonitoringStatisticsStorageFacilities;

import java.util.List;

import com.sydata.monitoring.constatns.MonitoringConstants;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.*;
import com.sydata.monitoring.events.GoodCompanyAuditPassEvent;
import com.sydata.monitoring.service.*;
import com.sydata.monitoring.vo.*;
import com.sydata.monitoring.mapper.MonitoringStatisticsMapper;
import com.sydata.organize.security.UserSecurity;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

/**
 * <p>
 * 流通检测-粮油流通统计 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsServiceImpl extends ServiceImpl<MonitoringStatisticsMapper, MonitoringStatistics> implements IMonitoringStatisticsService {

    @Resource
    private MonitoringStatisticsMapper monitoringStatisticsMapper;
    @Resource
    private IMonitoringStatisticsStorehouseService statisticsStorehouseService;
    @Resource
    private IMonitoringStatisticsStockScaleService statisticsStockScaleService;
    @Resource
    private IMonitoringStatisticsInfrastructureConstructionService statisticsInfrastructureConstructionService;
    @Resource
    private IMonitoringStatisticsPurchaseSaleService statisticsPurchaseSaleService;
    @Resource
    private IMonitoringStatisticsFarmerService statisticsFarmerService;
    @Resource
    private IMonitoringStatisticsStorageFacilitiesService statisticsStorageFacilitiesService;
    @Resource
    private IMonitoringStatisticsProcessService statisticsProcessService;
    @Resource
    private IMonitoringStatisticsCheckPointService statisticsCheckPointService;
    @Resource
    private IMonitoringStatisticsPriceCheckService statisticsPriceCheckService;
    @Resource
    private IMonitoringStatisticsProcessingRotationService statisticsProcessingRotationService;
    @Resource
    private IMonitoringStatisticsScienceInfoService statisticsScienceInfoService;
    @Resource
    private IMonitoringStatisticsIncomeExpensesService statisticsIncomeExpensesService;
    @Resource
    private IGoodCompanyService goodCompanyService;
    @Resource
    private ICheckPointService checkPointService;

    @Override

    public Page<MonitoringStatisticsVO> page(MonitoringStatisticsQueryDTO queryDTO) {
        Page<MonitoringStatistics> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatistics> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        Set<String> ids = records.stream().map(MonitoringStatistics::getId).collect(Collectors.toSet());
        Set<String> goodCompanyIds = records.stream().map(MonitoringStatistics::getGoodCompanyId).collect(Collectors.toSet());

        // 好粮油企业
        Map<String, String> goodCompanyMap = goodCompanyService.listByIds(goodCompanyIds)
                .stream()
                .collect(Collectors.toMap(GoodCompany::getId, GoodCompany::getCompanyName));

        // 查询关联的库点
        Map<String, String> storeHouseNamesMap = statisticsStorehouseService.lambdaQuery()
                .in(MonitoringStatisticsStorehouse::getStatisticId, ids)
                .list()
                .stream()
                .collect(Collectors.groupingBy(MonitoringStatisticsStorehouse::getStatisticId,
                        Collectors.mapping(MonitoringStatisticsStorehouse::getName,
                                Collectors.joining(","))));

        // 库存总数：储备规模
        Map<String, Optional<BigDecimal>> storeScaleMap = statisticsStockScaleService.lambdaQuery()
                .in(MonitoringStatisticsStockScale::getStatisticId, ids)
                .list()
                .stream()
                .collect(Collectors.groupingBy(MonitoringStatisticsStockScale::getId,
                        Collectors.mapping(MonitoringStatisticsStockScale::getStoreScale,
                                Collectors.reducing((preStoreScale, postStoreScale) -> {
                                    return Optional.ofNullable(preStoreScale).orElse(BigDecimal.ZERO)
                                            .add(Optional.ofNullable(postStoreScale).orElse(BigDecimal.ZERO));
                                }))));

        // 构建VO
        List<MonitoringStatisticsVO> voList = records.stream()
                .map(rec -> {
                    MonitoringStatisticsVO vo = new MonitoringStatisticsVO(rec);
                    vo.setCompanyName(goodCompanyMap.get(vo.getGoodCompanyId()));
                    vo.setStorehouseNames(storeHouseNamesMap.get(vo.getId()));
                    vo.setStockTotal(Optional.ofNullable(storeScaleMap.get(vo.getId()))
                            .map(opt -> opt.orElse(BigDecimal.ZERO)).orElse(BigDecimal.ZERO));
                    return vo;
                }).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsDetailVO detailById(String id) {
        MonitoringStatistics entity = getById(id);
        if (entity == null) {
            return null;
        }

        MonitoringStatisticsDetailVO vo = new MonitoringStatisticsDetailVO(entity);

        // 填充VO
        fillDetailVo(vo);

        return vo;
    }

    /**
     * 填充详情VO
     *
     * @param detailVO 详情VO
     */
    private void fillDetailVo(MonitoringStatisticsDetailVO detailVO) {
        String id = detailVO.getId();
        // 仓储设施
        MonitoringStatisticsStorageFacilitiesVO storageFacilitiesVO = statisticsStorageFacilitiesService.getByStatisticsId(id);
        // 个体工商户
        GoodCompanyVO goodCompanyVO = goodCompanyService.detailById(detailVO.getGoodCompanyId());
        // 加工轮换
        List<MonitoringStatisticsProcessingRotationVO> processingRotationVos = statisticsProcessingRotationService.getByStatisticsId(id);
        // 粮油科技信息
        List<MonitoringStatisticsScienceInfoVO> scienceInfoVos = statisticsScienceInfoService.getByStatisticsId(id);
        // 粮油收支平衡月度数据
        List<MonitoringStatisticsIncomeExpensesVO> incomeExpensesVos = statisticsIncomeExpensesService.getByStatisticsId(id);
        // 粮食市场监测点信息
        List<MonitoringStatisticsCheckPointVO> statisticsCheckPointVos = statisticsCheckPointService.getByStatisticsId(id);
        Set<String> pointIds = statisticsCheckPointVos.stream().map(MonitoringStatisticsCheckPointVO::getPointId).collect(Collectors.toSet());
        Map<String, CheckPointVo> checkPointVoMap = checkPointService.selectByIds(pointIds)
                .stream()
                .collect(Collectors.toMap(CheckPointVo::getId, Function.identity(), (preVal, postVal) -> preVal));
        statisticsCheckPointVos.forEach(vo->vo.fillProperty(checkPointVoMap.get(vo.getPointId())));
        // 居民农户信息
        List<MonitoringStatisticsFarmerVO> farmerVos = statisticsFarmerService.getByStatisticsId(id);
        // 基础设施建设信息
        List<MonitoringStatisticsInfrastructureConstructionVO> infrastructureConstructionVos =
                statisticsInfrastructureConstructionService.getByStatisticsId(id);
        // 价格监测信息
        List<MonitoringStatisticsPriceCheckVO> priceCheckVos = statisticsPriceCheckService.getByStatisticsId(id);
        // 加工信息
        List<MonitoringStatisticsProcessVO> processVos = statisticsProcessService.getByStatisticsId(id);
        // 粮油购销信息
        List<MonitoringStatisticsPurchaseSaleVO> purchaseSaleVos = statisticsPurchaseSaleService.getByStatisticsId(id);
        // 库存规模信息
        List<MonitoringStatisticsStockScaleVO> stockScaleVos = statisticsStockScaleService.getByStatisticsId(id);
        // 库点信息
        List<MonitoringStatisticsStorehouseVO> storehouseVos = statisticsStorehouseService.getByStatisticsId(id);

        detailVO.setStorageFacilitiesVO(storageFacilitiesVO);
        detailVO.setGoodCompanyVO(goodCompanyVO);
        detailVO.setProcessingRotationVos(processingRotationVos);
        detailVO.setScienceInfoVos(scienceInfoVos);
        detailVO.setIncomeExpensesVos(incomeExpensesVos);
        detailVO.setCheckPointVos(statisticsCheckPointVos);
        detailVO.setFarmerVos(farmerVos);
        detailVO.setInfrastructureConstructionVos(infrastructureConstructionVos);
        detailVO.setPriceCheckVos(priceCheckVos);
        detailVO.setProcessVos(processVos);
        detailVO.setPurchaseSaleVos(purchaseSaleVos);
        detailVO.setStockScaleVos(stockScaleVos);
        detailVO.setStorehouseVos(storehouseVos);
    }

    @Override
    public Boolean edit(MonitoringStatisticsEditDTO editDTO) {

        if (editDTO.getId() == null) {
            editDTO.setDataDate(LocalDate.now());
            editDTO.setCreateBy(UserSecurity.userName());
            editDTO.setCreateTime(LocalDateTime.now());
            editDTO.setUpdateBy(UserSecurity.userName());
            editDTO.setUpdateTime(LocalDateTime.now());
            return save(editDTO);
        }

        editDTO.setUpdateBy(UserSecurity.userName());
        editDTO.setUpdateTime(LocalDateTime.now());

        return updateById(editDTO);
    }

    @Override
    public Boolean add(MonitoringStatisticsAddDTO addDTO) {
        addDTO.setDataDate(LocalDate.now());
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public Boolean delete(MonitoringStatisticsDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

    /**
     * 监听好粮油企业审核通过的事件
     *
     * @param event 事件
     */
    @EventListener
    public void listenGoodCompanyAudit(GoodCompanyAuditPassEvent event) {
        GoodCompany goodCompany = event.getGoodCompany();

        MonitoringStatisticsAddDTO addDTO = new MonitoringStatisticsAddDTO();
        addDTO.setGoodCompanyId(goodCompany.getId());

        add(addDTO);
    }

    @Override
    public List<MonitoringStatisticsCheckPointStatisticsVo> priceStatistics(MonitoringStatisticsPriceCheckStatisticsDTO queryDTO) {
        String id = queryDTO.getId();

        if (id == null) {
            return Collections.emptyList();
        }

        Set<String> statisticsIds;

        String account = UserSecurity.loginUser().getAccount();
        // 超管账号查全部
        if (MonitoringConstants.ADMIN_ACCOUNT.equals(account)) {

            statisticsIds = statisticsCheckPointService.lambdaQuery()
                    .select(MonitoringStatisticsCheckPoint::getStatisticId)
                    .eq(id != null, MonitoringStatisticsCheckPoint::getPointId, id)
                    .list()
                    .stream().map(MonitoringStatisticsCheckPoint::getStatisticId).collect(Collectors.toSet());

        } else {
            CheckPointVo checkPointVo = checkPointService.currentPoint();

            Assert.notNull(checkPointVo, "当前账户未分配监测点，因此没有权限查询");

            String currentUserCheckPointId = checkPointVo.getId();

            Assert.state(StringUtils.hasText(id) && id.equals(currentUserCheckPointId), "指定的监测点并未分配给当前用户");

            statisticsIds = statisticsCheckPointService.lambdaQuery()
                    .select(MonitoringStatisticsCheckPoint::getStatisticId)
                    .eq(MonitoringStatisticsCheckPoint::getPointId, currentUserCheckPointId)
                    .list()
                    .stream().map(MonitoringStatisticsCheckPoint::getStatisticId).collect(Collectors.toSet());
        }

        List<MonitoringStatisticsPriceCheckVO> priceCheckVos = statisticsPriceCheckService.selectByStatisticsIds(statisticsIds);

        return priceCheckVos.stream().map(MonitoringStatisticsCheckPointStatisticsVo::new).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean detailBatchEdit(MonitoringStatisticsDetailEditDto editDTO) {

        Assert.hasText(editDTO.getId(), "主表ID不能为空");

        MonitoringStatisticsStorageFacilities storageFacilitiesVO = editDTO.getStorageFacilitiesVO();
        List<MonitoringStatisticsProcessingRotation> processingRotation = editDTO.getProcessingRotation();
        List<MonitoringStatisticsScienceInfo> scienceInfo = editDTO.getScienceInfo();
        List<MonitoringStatisticsIncomeExpenses> incomeExpenses = editDTO.getIncomeExpenses();
        List<MonitoringStatisticsCheckPoint> checkPoint = editDTO.getCheckPoint();
        List<MonitoringStatisticsFarmer> farmer = editDTO.getFarmer();
        List<MonitoringStatisticsInfrastructureConstruction> infrastructureConstruction = editDTO.getInfrastructureConstruction();
        List<MonitoringStatisticsPriceCheck> priceCheck = editDTO.getPriceCheck();
        List<MonitoringStatisticsProcess> process = editDTO.getProcess();
        List<MonitoringStatisticsPurchaseSale> purchaseSale = editDTO.getPurchaseSale();
        List<MonitoringStatisticsStockScale> stockScale = editDTO.getStockScale();
        List<MonitoringStatisticsStorehouse> storehouse = editDTO.getStorehouse();

        statisticsStorehouseService.saveOrUpdateBatch(storehouse);
        statisticsStockScaleService.saveOrUpdateBatch(stockScale);
        statisticsInfrastructureConstructionService.saveOrUpdateBatch(infrastructureConstruction);
        statisticsPurchaseSaleService.saveOrUpdateBatch(purchaseSale);
        statisticsFarmerService.saveOrUpdateBatch(farmer);
        statisticsStorageFacilitiesService.saveOrUpdate(storageFacilitiesVO);
        statisticsProcessService.saveOrUpdateBatch(process);
        statisticsCheckPointService.saveOrUpdateBatch(checkPoint);
        statisticsPriceCheckService.saveOrUpdateBatch(priceCheck);
        statisticsProcessingRotationService.saveOrUpdateBatch(processingRotation);
        statisticsScienceInfoService.saveOrUpdateBatch(scienceInfo);
        statisticsIncomeExpensesService.saveOrUpdateBatch(incomeExpenses);

        return true;
    }
}
