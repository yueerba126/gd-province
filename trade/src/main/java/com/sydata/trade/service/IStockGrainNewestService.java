package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.collect.api.param.trade.StockGrainApiParam;
import com.sydata.trade.domain.StockGrain;
import com.sydata.trade.domain.StockGrainNewest;
import com.sydata.trade.param.StockGrainDetailPageParam;
import com.sydata.trade.param.StockGrainPageParam;
import com.sydata.trade.vo.StockGrainDetailVo;
import com.sydata.trade.vo.StockGrainNewestVo;
import com.sydata.trade.vo.StockGrainStatisticsVo;

import java.util.List;


/**
 * @author lzq
 * @description 粮油购销-粮食库存最新数据表Service接口
 * @date 2023/4/21 10:28
 */
public interface IStockGrainNewestService extends IService<StockGrainNewest> {

    /**
     * 粮食库存最新数据列表
     *
     * @param pageParam 分页参数
     * @return 最新库存信息
     */
    Page<StockGrainNewestVo> pages(StockGrainPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    StockGrainNewestVo detail(String id);

    /**
     * 库存同步
     *
     * @param param      粮食库存信息API操作参数
     * @param stockGrain 粮食库存
     */
    void stockSyn(StockGrainApiParam param, StockGrain stockGrain);

    /**
     * 统计库区储备粮库存统计
     *
     * @param stockHouseIds 库区ID列表
     * @return 库区库存统计VO列表
     */
    List<StockGrainStatisticsVo> reserveStatistics(List<String> stockHouseIds);

    Page<StockGrainDetailVo> pageDetail(StockGrainDetailPageParam pageParam);

    void stockDetailExport(StockGrainDetailPageParam pageParam);
}
