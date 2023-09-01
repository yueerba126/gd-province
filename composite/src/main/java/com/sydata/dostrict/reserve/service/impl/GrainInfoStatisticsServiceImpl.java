package com.sydata.dostrict.reserve.service.impl;

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
import com.sydata.dostrict.reserve.mapper.GrainInfoStatisticsMapper;
import com.sydata.dostrict.reserve.mapper.GrainPurSaleMangeMapper;
import com.sydata.dostrict.reserve.param.GrainSelectParam;
import com.sydata.dostrict.reserve.service.IGrainInfoStatisticsService;
import com.sydata.dostrict.reserve.vo.GrainHarvestStatisticsVo;
import com.sydata.dostrict.reserve.vo.GrainMaterialStatisticsVo;
import com.sydata.dostrict.reserve.vo.MaterialPriceTrendStatisticsVo;
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
 * @description: 粮食储备-粮油信息统计 Service业务层处理
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
@Service("grainInfoStatisticsService")
public class GrainInfoStatisticsServiceImpl implements IGrainInfoStatisticsService {
    @Resource
    private GrainInfoStatisticsMapper grainInfoStatisticsMapper;
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取按物料分组的库存的统计信息
     * @Date 16:14 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.GrainMaterialStatisticsVo>
     **/
    @DataBindFieldConvert
    @Override
    public List<GrainMaterialStatisticsVo> listGrainMaterialStatisticsVo(@Valid GrainSelectParam param) {
        return grainInfoStatisticsMapper.queryRealtimeInventoryByMaterialList(param);
    }
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取按收获年度分组的库存的统计信息
     * @Date 16:15 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.GrainHarvestStatisticsVo>
     **/
    @DataBindFieldConvert
    @Override
    public List<GrainHarvestStatisticsVo> listGrainHarvestStatisticsVos(@Valid GrainSelectParam param) {
        return grainInfoStatisticsMapper.queryRealtimeInventoryByHarvestList(param);
    }
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取按业务日期、物料分组的物料价格的统计信息
     * @Date 16:15 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.MaterialPriceTrendStatisticsVo>
     **/
    @DataBindFieldConvert
    @Override
    public List<MaterialPriceTrendStatisticsVo> listMaterialPriceTrendStatisticsVos(@Valid GrainSelectParam param) {
        return grainInfoStatisticsMapper.queryMaterialPriceTrendList(param);
    }
}
