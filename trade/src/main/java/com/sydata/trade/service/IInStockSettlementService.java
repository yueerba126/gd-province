package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.InStockSettlement;
import com.sydata.trade.param.InStockSettlementPageParam;
import com.sydata.trade.vo.InStockSettlementVo;

import java.util.Collection;

/**
 * <p>
 * 入库结算信息表 服务类
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
public interface IInStockSettlementService extends IService<InStockSettlement> {

    /**
     * 分页列表
     *
     * @param pageParam 查询参数
     * @return 入库结算信息
     */
    Page<InStockSettlementVo> pages(InStockSettlementPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    InStockSettlementVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
