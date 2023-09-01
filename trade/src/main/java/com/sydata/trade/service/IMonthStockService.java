package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.MonthStock;
import com.sydata.trade.param.MonthStockPageParam;
import com.sydata.trade.vo.MonthStockVo;

import java.util.Collection;

/**
 * 粮油购销-账面库存Service接口
 *
 * @author lzq
 * @date 2022-07-22
 */
public interface IMonthStockService extends IService<MonthStock> {


    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<MonthStockVo> pages(MonthStockPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    MonthStockVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}