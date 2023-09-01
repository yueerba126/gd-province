package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.StockGrain;
import com.sydata.trade.param.StockGrainPageParam;
import com.sydata.trade.vo.StockGrainVo;

import java.util.Collection;

/**
 * @Author chenzx
 * @Date 2022/8/18 18:07
 * @Description: 粮食库存控制层
 * @Version 1.0
 */
public interface IStockGrainService extends IService<StockGrain> {

    /**
     * 粮食库存列表
     *
     * @param pageParam 入参
     * @return 库存信息
     */
    Page<StockGrainVo> pages(StockGrainPageParam pageParam);


    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    StockGrainVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
