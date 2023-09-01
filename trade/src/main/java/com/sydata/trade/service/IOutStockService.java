package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.OutStock;
import com.sydata.trade.param.OutStockPageParam;
import com.sydata.trade.vo.OutStockVo;

import java.util.Collection;

/**
 * <p>
 * 粮食出库数据表 服务类
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
public interface IOutStockService extends IService<OutStock> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 粮食出库数据
     */
    Page<OutStockVo> pages(OutStockPageParam pageParam);


    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    OutStockVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
