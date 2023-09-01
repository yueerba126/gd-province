package com.sydata.dostrict.reserve.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.reserve.domain.ApparitorReservePlan;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanMasterSlaveParam;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanPageParam;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanSaveParam;
import com.sydata.dostrict.reserve.vo.ApparitorReservePlanMasterSlaveVo;
import com.sydata.dostrict.reserve.vo.ApparitorReservePlanVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 粮食储备-储备计划Service接口
 *
 * @author lzq
 * @date 2023-04-26
 */
public interface IApparitorReservePlanService extends IService<ApparitorReservePlan> {


    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorReservePlanVo> pages(ApparitorReservePlanPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ApparitorReservePlanVo detail(String id);

    /**
     * 详情 - 主从表
     *
     * @param id 主键ID
     * @return 详情
     */
    ApparitorReservePlanMasterSlaveVo detailMasterSlave(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorReservePlanSaveParam param);

    /**
     * 操作主从表数据
     *
     * @param param 参数
     * @return 主键ID
     */
    Boolean saveOrUpdateMasterSlaveData(ApparitorReservePlanMasterSlaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorReservePlanSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);
}