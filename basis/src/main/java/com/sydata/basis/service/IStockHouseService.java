package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.param.StockHousePageParam;
import com.sydata.basis.vo.StockHouseVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 基础信息-库区信息Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface IStockHouseService extends IService<StockHouse> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<StockHouseVo> pages(StockHousePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    StockHouseVo detail(String id);

    /**
     * 数据视图分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<StockHouseVo> dataViewPage(StockHousePageParam pageParam);

    /**
     * 根据行政区划查询库区列表
     *
     * @param regionId 行政区划代码
     * @return 库区列表
     */
    List<StockHouse> listByRegionId(String regionId);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}