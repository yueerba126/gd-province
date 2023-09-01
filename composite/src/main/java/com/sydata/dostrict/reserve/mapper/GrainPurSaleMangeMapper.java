/**
 * @filename:AdminPlanBeanDao 2023年04月24日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.reserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.dostrict.reserve.domain.ApparitorReservePlan;
import com.sydata.dostrict.reserve.param.GrainSelectParam;
import com.sydata.dostrict.reserve.param.GrainWheetSelectParam;
import com.sydata.dostrict.reserve.vo.RealAndPlanningQuantityVo;
import com.sydata.dostrict.reserve.vo.RealAndPlanningWheelOutAndInVo;

import java.util.List;

/**   
 * @Description:TODO(粮食储备-粮油购销管理数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface GrainPurSaleMangeMapper extends BaseMapper<ApparitorReservePlan> {
    /**
     * 按粮食性质分组查询省储、市储、县储的实时库存量(万吨),取即时库存表
     * @param param
     * @return
     */
    List<RealAndPlanningQuantityVo> queryRealtimeInventoryList(GrainSelectParam param);
    /**
     * 按粮食性质分组查询省储、市储、县储的储备计划量(万吨),取储备计划表
     * @param param
     * @return
     */
    List<RealAndPlanningQuantityVo> queryReservePlanList(GrainSelectParam param);
    /**
     * 按粮食性质分组查询省储、市储、县储的计划轮入轮出和实际轮入轮出(万吨),取轮换计划表
     * @param param
     * @return
     */
    List<RealAndPlanningWheelOutAndInVo> queryPlanToRotateInAndOutList(GrainWheetSelectParam param);
    /**
     * 按粮食性质分组查询省储、市储、县储的实际轮入轮出(万吨),取出入库表
     * @param param
     * @return
     */
    List<RealAndPlanningWheelOutAndInVo> queryRealToRotateInAndOutList(GrainWheetSelectParam param);
}
