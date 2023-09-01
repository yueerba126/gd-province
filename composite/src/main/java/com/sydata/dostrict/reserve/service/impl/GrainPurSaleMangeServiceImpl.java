package com.sydata.dostrict.reserve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.sydata.basis.domain.Company;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.mapper.CompanyMapper;
import com.sydata.basis.mapper.StockHouseMapper;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.dostrict.plan.domain.ApparitorPlan;
import com.sydata.dostrict.plan.param.WarehousInvestManageSelectParam;
import com.sydata.dostrict.plan.vo.StockHouseDistributionVo;
import com.sydata.dostrict.plan.vo.WarehouseFacilityConstructionManagementVo;
import com.sydata.dostrict.reserve.mapper.GrainPurSaleMangeMapper;
import com.sydata.dostrict.reserve.param.GrainSelectParam;
import com.sydata.dostrict.reserve.param.GrainWheetSelectParam;
import com.sydata.dostrict.reserve.service.IGrainPurSaleMangeService;
import com.sydata.dostrict.reserve.vo.CompanyDistributionVo;
import com.sydata.dostrict.reserve.vo.RealAndPlanningQuantityVo;
import com.sydata.dostrict.reserve.vo.RealAndPlanningWheelOutAndInVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: gd-province-platform
 * @description: 粮食储备-粮油购销管理 Service业务层处理
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
@Service("grainPurSaleMangeService")
public class GrainPurSaleMangeServiceImpl implements IGrainPurSaleMangeService {
    @Resource
    private GrainPurSaleMangeMapper grainPurSaleMangeMapper;
    @Resource
    private CompanyMapper companyMapper;

    // 省储
    public static final String PROVINCE_RESERVE = "121";
    // 市储
    public static final String CITY_RESERVE = "122";
    // 县储
    public static final String REGION_RESERVE = "123";
    // 轮入
    public static final String WHEEL_IN = "1";
    // 轮出
    public static final String WHEEL_OUT = "2";
    // 实际
    public static final String REAL_STATUS = "1";
    // 计划
    public static final String PLAN_STATUS = "2";

    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取储备计划量和实际库存量在省储市储县储的统计信息
     * @Date 16:14 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.RealAndPlanningQuantityVo>
     **/
    @DataBindFieldConvert
    @Override
    public RealAndPlanningQuantityVo listRealAndPlanningQuantityVo(@Valid GrainSelectParam param) {
        RealAndPlanningQuantityVo realAndPlanningQuantityVo = new RealAndPlanningQuantityVo();

        List<RealAndPlanningQuantityVo> realtimeInventoryList = grainPurSaleMangeMapper.queryRealtimeInventoryList(param);
        List<RealAndPlanningQuantityVo> planList = grainPurSaleMangeMapper.queryReservePlanList(param);
        BigDecimal provinceReserveQuantity = getRealAndPlanningBigDecimal(realtimeInventoryList, PROVINCE_RESERVE);
        BigDecimal cityReserveQuantity = getRealAndPlanningBigDecimal(realtimeInventoryList, CITY_RESERVE);
        BigDecimal regionReserveQuantity = getRealAndPlanningBigDecimal(realtimeInventoryList, REGION_RESERVE);
        BigDecimal provincePlanningQuantity = getRealAndPlanningBigDecimal(planList, PROVINCE_RESERVE);
        BigDecimal cityPlanningQuantity = getRealAndPlanningBigDecimal(planList, CITY_RESERVE);
        BigDecimal regionPlanningQuantity = getRealAndPlanningBigDecimal(planList, REGION_RESERVE);

        if(!CollectionUtils.isEmpty(planList)){
            if(StringUtils.isNotEmpty(param.getDwdm())){
                realAndPlanningQuantityVo.setDwmc(planList.get(0).getDwmc());
                realAndPlanningQuantityVo.setDwdm(param.getDwdm());
            }
            if(StringUtils.isNotEmpty(param.getXzqhdm())){
                realAndPlanningQuantityVo.setXzqhdmName(planList.get(0).getXzqhdmName());
                realAndPlanningQuantityVo.setXzqhdm(param.getXzqhdm());
            }
        }
        if(CollectionUtils.isEmpty(planList)&&!CollectionUtils.isEmpty(realtimeInventoryList)){
            if(StringUtils.isNotEmpty(param.getDwdm())){
                realAndPlanningQuantityVo.setDwmc(realtimeInventoryList.get(0).getDwmc());
                realAndPlanningQuantityVo.setDwdm(param.getDwdm());
            }
            if(StringUtils.isNotEmpty(param.getXzqhdm())){
                realAndPlanningQuantityVo.setXzqhdmName(realtimeInventoryList.get(0).getXzqhdmName());
                realAndPlanningQuantityVo.setXzqhdm(param.getXzqhdm());
            }
        }
        realAndPlanningQuantityVo.setProvincePlanningQuantity(provincePlanningQuantity);
        realAndPlanningQuantityVo.setProvinceReserveQuantity(provinceReserveQuantity);
        realAndPlanningQuantityVo.setCityPlanningQuantity(cityPlanningQuantity);
        realAndPlanningQuantityVo.setCityReserveQuantity(cityReserveQuantity);
        realAndPlanningQuantityVo.setRegionPlanningQuantity(regionPlanningQuantity);
        realAndPlanningQuantityVo.setRegionReserveQuantity(regionReserveQuantity);
        return realAndPlanningQuantityVo;
    }



    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取不同年份的轮入轮出在省储市储县储的统计信息
     * @Date 16:15 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.RealAndPlanningWheelOutAndInVo>
     **/
    @DataBindFieldConvert
    @Override
    public List<RealAndPlanningWheelOutAndInVo> listRealAndPlanningWheelOutAndInVos(@Valid GrainWheetSelectParam param) {
        List<RealAndPlanningWheelOutAndInVo> realAndPlanningWheelOutAndInVos = Lists.newArrayList();
        List<RealAndPlanningWheelOutAndInVo> planningWheelVos = grainPurSaleMangeMapper.queryPlanToRotateInAndOutList(param);
        List<RealAndPlanningWheelOutAndInVo> realWheelVos = grainPurSaleMangeMapper.queryRealToRotateInAndOutList(param);

        // 计划统计信息
        Map<String, BigDecimal> planGroup = Optional.ofNullable(planningWheelVos).orElse(new ArrayList<>()).stream().filter(Objects::nonNull).collect(Collectors.groupingBy(e->{
            return PLAN_STATUS+"_"+e.getLsxzdm()+"_"+e.getNd();},
                Collectors.mapping(RealAndPlanningWheelOutAndInVo::getQuantity, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        // 实际统计信息
        Map<String, BigDecimal> realGroup = Optional.ofNullable(realWheelVos).orElse(new ArrayList<>()).stream().filter(Objects::nonNull).collect(Collectors.groupingBy(e->{
            return REAL_STATUS+"_"+e.getLsxzdm()+"_"+e.getNd();
            }, Collectors.mapping(RealAndPlanningWheelOutAndInVo::getQuantity, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        // 总的统计信息
        Map<String, BigDecimal> allGroup = planGroup.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, () -> new HashMap<>(realGroup)));

        List<RealAndPlanningWheelOutAndInVo> collectList = allGroup.keySet().stream().map(key -> {
            RealAndPlanningWheelOutAndInVo realAndPlanningWheelOutAndInVo = new RealAndPlanningWheelOutAndInVo();
            BigDecimal quantity = allGroup.get(key);
            String[] combineArray = key.split("_");
            String status = combineArray[0];
            String lsxzdm = combineArray[1];
            String nd = combineArray[2];
            if(StringUtils.isNotEmpty(param.getDwdm())){
                realAndPlanningWheelOutAndInVo.setDwdm(param.getDwdm());
            }
            if(StringUtils.isNotEmpty(param.getXzqhdm())){
                realAndPlanningWheelOutAndInVo.setXzqhdm(param.getXzqhdm());
            }
            realAndPlanningWheelOutAndInVo.setLsxzdm(lsxzdm);
            realAndPlanningWheelOutAndInVo.setNd(nd);

            realAndPlanningWheelOutAndInVo.setProvincePlanningInQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setProvinceInQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setProvincePlanningOutQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setProvinceOutQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setCityPlanningInQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setCityInQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setCityPlanningOutQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setCityOutQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setRegionPlanningInQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setRegionInQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setRegionPlanningOutQuantity(BigDecimal.ZERO);
            realAndPlanningWheelOutAndInVo.setRegionOutQuantity(BigDecimal.ZERO);

            if(StringUtils.equals(param.getLhlx(),WHEEL_IN)){
                if(StringUtils.equals(status,PLAN_STATUS)){
                    if(StringUtils.equals(lsxzdm,PROVINCE_RESERVE)){
                        realAndPlanningWheelOutAndInVo.setProvincePlanningInQuantity(quantity);
                    }else if(StringUtils.equals(lsxzdm,CITY_RESERVE)){
                        realAndPlanningWheelOutAndInVo.setCityPlanningInQuantity(quantity);
                    }else{
                        realAndPlanningWheelOutAndInVo.setRegionPlanningInQuantity(quantity);
                    }
                }else {
                    if (StringUtils.equals(lsxzdm, PROVINCE_RESERVE)) {
                        realAndPlanningWheelOutAndInVo.setProvinceInQuantity(quantity);
                    } else if (StringUtils.equals(lsxzdm, CITY_RESERVE)) {
                        realAndPlanningWheelOutAndInVo.setCityInQuantity(quantity);
                    } else {
                        realAndPlanningWheelOutAndInVo.setRegionInQuantity(quantity);
                    }
                }
            }else{
                if(StringUtils.equals(status,PLAN_STATUS)){
                    if(StringUtils.equals(lsxzdm,PROVINCE_RESERVE)){
                        realAndPlanningWheelOutAndInVo.setProvincePlanningOutQuantity(quantity);
                    }else if(StringUtils.equals(lsxzdm,CITY_RESERVE)){
                        realAndPlanningWheelOutAndInVo.setCityPlanningOutQuantity(quantity);
                    }else{
                        realAndPlanningWheelOutAndInVo.setRegionPlanningOutQuantity(quantity);
                    }
                }else{
                    if(StringUtils.equals(lsxzdm,PROVINCE_RESERVE)){
                        realAndPlanningWheelOutAndInVo.setProvinceOutQuantity(quantity);
                    }else if(StringUtils.equals(lsxzdm,CITY_RESERVE)){
                        realAndPlanningWheelOutAndInVo.setCityOutQuantity(quantity);
                    }else{
                        realAndPlanningWheelOutAndInVo.setRegionOutQuantity(quantity);
                    }
                }
            }
            return realAndPlanningWheelOutAndInVo;
        }).collect(Collectors.toList());

        // stream流通过字段分组对其他字段累加
        realAndPlanningWheelOutAndInVos = collectList.stream()
            .collect(Collectors.toMap(e->{return e.getNd();}, a -> a, (a,b)-> {
                a.setProvinceInQuantity(a.getProvinceInQuantity().add(b.getProvinceInQuantity()));
                a.setProvincePlanningInQuantity(a.getProvincePlanningInQuantity().add(b.getProvincePlanningInQuantity()));
                a.setProvinceOutQuantity(a.getProvinceOutQuantity().add(b.getProvinceOutQuantity()));
                a.setProvincePlanningOutQuantity(a.getProvincePlanningOutQuantity().add(b.getProvincePlanningOutQuantity()));

                a.setCityInQuantity(a.getCityInQuantity().add(b.getCityInQuantity()));
                a.setCityPlanningInQuantity(a.getCityPlanningInQuantity().add(b.getCityPlanningInQuantity()));
                a.setCityOutQuantity(a.getCityOutQuantity().add(b.getCityOutQuantity()));
                a.setCityPlanningOutQuantity(a.getCityPlanningOutQuantity().add(b.getCityPlanningOutQuantity()));

                a.setRegionInQuantity(a.getRegionInQuantity().add(b.getRegionInQuantity()));
                a.setRegionPlanningInQuantity(a.getRegionPlanningInQuantity().add(b.getRegionPlanningInQuantity()));
                a.setRegionOutQuantity(a.getRegionOutQuantity().add(b.getRegionOutQuantity()));
                a.setRegionPlanningOutQuantity(a.getRegionPlanningOutQuantity().add(b.getRegionPlanningOutQuantity()));
                return a;
            })).values().stream().collect(Collectors.toList());

        realAndPlanningWheelOutAndInVos =  realAndPlanningWheelOutAndInVos.stream()
                .sorted(Comparator.comparing(RealAndPlanningWheelOutAndInVo::getNd).reversed()).collect(Collectors.toList());

        return realAndPlanningWheelOutAndInVos;
    }

    /**
     * @Author lzq
     * @Description 根据行政区划获取企业分布信息
     * @Date 16:15 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.plan.vo.CompanyDistributionVo>
     **/
    @DataBindFieldConvert
    @Override
    public List<CompanyDistributionVo> listCompanyDistributionVos(@Valid GrainSelectParam param) {
        List<CompanyDistributionVo> stockHouseDistributionVos = Lists.newArrayList();
        QueryWrapper<Company> stockHouseQueryWrapper = new QueryWrapper();
        stockHouseQueryWrapper.lambda()
                .eq(StringUtils.isNotEmpty(param.getDwdm()),Company::getEnterpriseId,param.getDwdm())
                .eq(StringUtils.isNotEmpty(param.getKqdm()),Company::getStockHouseId,param.getKqdm())
                .likeRight(StringUtils.isNotEmpty(param.getXzqhdm()),Company::getRegionId,param.getXzqhdm().replaceAll("0+$", ""))
                .ne(Company::getCzbz, CzBzEnum.D);
        List<Company> companyList = companyMapper.selectList(stockHouseQueryWrapper);
        stockHouseDistributionVos = BeanUtils.copyToList(companyList, CompanyDistributionVo.class);
        return stockHouseDistributionVos;
    }


    private BigDecimal getRealAndPlanningBigDecimal(List<RealAndPlanningQuantityVo> list, String lsxzdm) {
        return list.stream().
                filter(x -> StringUtils.equals(lsxzdm, x.getLsxzdm())).
                map(RealAndPlanningQuantityVo::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
