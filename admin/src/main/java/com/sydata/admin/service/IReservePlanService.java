package com.sydata.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.admin.domain.ReservePlan;
import com.sydata.admin.param.ReservePlanPageParam;
import com.sydata.admin.vo.ReservePlanVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 行政管理-储备计划Service接口
 *
 * @author lzq
 * @date 2022-07-25
 */
public interface IReservePlanService extends IService<ReservePlan> {


    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ReservePlanVo> pages(ReservePlanPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ReservePlanVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}