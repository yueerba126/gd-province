/**
 * @filename:AdminPlanBeanDao 2023年04月24日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.reserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.basis.vo.StockHouseVo;
import com.sydata.dostrict.plan.param.WarehousInvestManageSelectParam;
import com.sydata.dostrict.reserve.domain.ApparitorReservePlan;
import com.sydata.dostrict.reserve.param.GrainSelectParam;
import com.sydata.dostrict.reserve.vo.GrainHarvestStatisticsVo;
import com.sydata.dostrict.reserve.vo.GrainMaterialStatisticsVo;
import com.sydata.dostrict.reserve.vo.MaterialPriceTrendStatisticsVo;
import com.sydata.dostrict.reserve.vo.RealAndPlanningQuantityVo;

import java.util.List;

/**   
 * @Description:TODO(粮食储备-粮油信息统计数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface GrainInfoStatisticsMapper extends BaseMapper<ApparitorReservePlan> {
    /**
     * 按粮食品种前三位分组统计的实时库存量(万吨),取即时库存表
     * @param param
     * @return
     */
    List<GrainMaterialStatisticsVo> queryRealtimeInventoryByMaterialList(GrainSelectParam param);
    /**
     * 按收获年度分组统计的实时库存量(万吨),取即时库存表
     * @param param
     * @return
     */
    List<GrainHarvestStatisticsVo> queryRealtimeInventoryByHarvestList(GrainSelectParam param);
    /**
     * 按粮食品种前三位，业务月份分组统计的结算价格,取入库结算表
     * @param param
     * @return
     */
    List<MaterialPriceTrendStatisticsVo> queryMaterialPriceTrendList(GrainSelectParam param);
}
