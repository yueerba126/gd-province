package com.sydata.dostrict.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.mapper.StockHouseMapper;
import com.sydata.basis.vo.StockHouseVo;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.dostrict.plan.domain.ApparitorPlan;
import com.sydata.dostrict.plan.mapper.ApparitorPlanMapper;
import com.sydata.dostrict.plan.mapper.WareHouseInvestMapper;
import com.sydata.dostrict.plan.param.WarehousInvestManageSelectParam;
import com.sydata.dostrict.plan.service.IWarehouseInvestManageService;
import com.sydata.dostrict.plan.vo.FacilitiesAndBuildingsInfoVo;
import com.sydata.dostrict.plan.vo.StockHouseDistributionVo;
import com.sydata.dostrict.plan.vo.WarehouseFacilityConstructionManagementVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: gd-province-platform
 * @description: 规划建设-仓储设施投资管理 Service业务层处理
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
@Service("warehouseInvestManageService")
public class WarehouseInvestManageServiceImpl implements IWarehouseInvestManageService {
    @Resource
    private StockHouseMapper stockHouseMapper;
    @Resource
    private WareHouseInvestMapper wareHouseInvestMapper;
    @Resource
    private ApparitorPlanMapper adminPlanMapper;

    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取仓容、仓房数量、廒间数量、货位数量、设备数量的统计信息
     * @Date 16:14 2023/4/25
     * @Param [warehousInvestManageSelectParam]
     * @return java.util.List<com.sydata.dostrict.plan.vo.FacilitiesAndBuildingsInfoVo>
     **/
    @DataBindFieldConvert
    @Override
    public FacilitiesAndBuildingsInfoVo listFacilitiesAndBuildingsInfoVo(@Valid WarehousInvestManageSelectParam param) {
        FacilitiesAndBuildingsInfoVo infoVo = new FacilitiesAndBuildingsInfoVo();
        List<StockHouseVo> stockList = wareHouseInvestMapper.queryStockHouseList(param);
        // 仓容
        BigDecimal yxcrSum = stockList.stream().map(StockHouse::getYxcr).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal yxgrSum = stockList.stream().map(StockHouse::getYxgr).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal crSum = yxcrSum.add(yxgrSum);
        // 仓房数量
        Integer cfSum = stockList.stream().mapToInt(StockHouse::getCfs).sum();
        Integer ygSum = stockList.stream().mapToInt(StockHouse::getYgs).sum();
        Integer cfsSum = cfSum+ygSum;
        // 廒间数量
        Integer granarySum = wareHouseInvestMapper.queryGranaryCount(param);
        // 货位数量
        Integer cargoSum = wareHouseInvestMapper.queryCargoCount(param);
        // 设备数量
        Integer deviceSum = wareHouseInvestMapper.queryDeviceCount(param);
        if(!CollectionUtils.isEmpty(stockList)){
            if(StringUtils.isNotEmpty(param.getDwdm())){
                infoVo.setDwmc(stockList.get(0).getDwmc());
            }
            if(StringUtils.isNotEmpty(param.getKqdm())){
                infoVo.setKqmc(stockList.get(0).getKqmc());
            }
            if(StringUtils.isNotEmpty(param.getXzqhdm())){
                infoVo.setXzqhdmName(stockList.get(0).getXzqhdmName());
            }
        }
        infoVo.setXzqhdm(param.getXzqhdm());
        infoVo.setKqdm(param.getKqdm());
        infoVo.setZcr(crSum);
        infoVo.setCfsl(cfsSum);
        infoVo.setAjsl(granarySum);
        infoVo.setHwsl(cargoSum);
        infoVo.setSbsl(deviceSum);
        return infoVo;
    }
    /**
     * @Author lzq
     * @Description 根据行政区划获取库区分布信息
     * @Date 16:15 2023/4/25
     * @Param [warehousInvestManageSelectParam]
     * @return java.util.List<com.sydata.dostrict.plan.vo.StockHouseDistributionVo>
     **/
    @DataBindFieldConvert
    @Override
    public List<StockHouseDistributionVo> listStockHouseDistributionVos(@Valid WarehousInvestManageSelectParam param) {
        List<StockHouseDistributionVo> stockHouseDistributionVos = Lists.newArrayList();
        QueryWrapper<StockHouse> stockHouseQueryWrapper = new QueryWrapper();
        stockHouseQueryWrapper.lambda()
                .eq(StringUtils.isNotEmpty(param.getDwdm()),StockHouse::getEnterpriseId,param.getDwdm())
                .eq(StringUtils.isNotEmpty(param.getKqdm()),StockHouse::getStockHouseId,param.getKqdm())
                .likeRight(StringUtils.isNotEmpty(param.getXzqhdm()),StockHouse::getRegionId,param.getXzqhdm().replaceAll("0+$", ""))
                .ne(StockHouse::getCzbz,CzBzEnum.D);
        List<StockHouse> stockList = stockHouseMapper.selectList(stockHouseQueryWrapper);
        stockHouseDistributionVos = BeanUtils.copyToList(stockList, StockHouseDistributionVo.class);
        return stockHouseDistributionVos;
    }
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取仓储设施建设管理信息统计信息
     * @Date 16:15 2023/4/25
     * @Param [warehousInvestManageSelectParam]
     * @return java.util.List<com.sydata.dostrict.plan.vo.WarehouseFacilityConstructionManagementVo>
     **/
    @Override
    public List<WarehouseFacilityConstructionManagementVo> listWarehouseFacilityConstructionManagementVos(@Valid WarehousInvestManageSelectParam param) {
        List<WarehouseFacilityConstructionManagementVo> warehouseFacilityConstructionManagementVos = Lists.newArrayList();
        QueryWrapper<ApparitorPlan> adminPlanQueryWrapper = new QueryWrapper();
        adminPlanQueryWrapper.lambda()
                .eq(StringUtils.isNotEmpty(param.getDwdm()), ApparitorPlan::getEnterpriseId,param.getDwdm())
                .eq(StringUtils.isNotEmpty(param.getKqdm()), ApparitorPlan::getStockHouseId,param.getKqdm())
                .likeRight(StringUtils.isNotEmpty(param.getXzqhdm()),ApparitorPlan::getRegionId,param.getXzqhdm().replaceAll("0+$", ""))
                .ne(ApparitorPlan::getCzbz,CzBzEnum.D);
        List<ApparitorPlan> adminPlanList = adminPlanMapper.selectList(adminPlanQueryWrapper);

        Map<String, Long> groupCountMap = adminPlanList.stream()
                .collect(Collectors.groupingBy(e->{return String.valueOf(e.getLxrq().getYear());}, Collectors.counting()));

        Map<String, BigDecimal> yearGroup = adminPlanList.stream().collect(Collectors.groupingBy(e->{ return String.valueOf(e.getLxrq().getYear());},
                Collectors.mapping(ApparitorPlan::getJhtz, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));


        for (Map.Entry<String, Long> entry : groupCountMap.entrySet()) {
            WarehouseFacilityConstructionManagementVo warehouseFacilityConstructionManagementVo = new WarehouseFacilityConstructionManagementVo();
            warehouseFacilityConstructionManagementVo.setYear(entry.getKey());
            warehouseFacilityConstructionManagementVo.setItemQuantity(entry.getValue().intValue());
            warehouseFacilityConstructionManagementVo.setPlannedInvestmentAmount(yearGroup.get(entry.getKey()).setScale(4,BigDecimal.ROUND_HALF_UP));
            warehouseFacilityConstructionManagementVos.add(warehouseFacilityConstructionManagementVo);
        }
        return warehouseFacilityConstructionManagementVos;
    }
}
