package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.OutStockSettlement;
import com.sydata.trade.param.OutStockSettlementPageParam;
import com.sydata.trade.vo.OutStockSettlementVo;

import java.util.Collection;

/**
 * <p>
 * 出库结算信息表 服务类
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
public interface IOutStockSettlementService extends IService<OutStockSettlement> {

    /**
     * 分页列表
     *
     * @param pageParam 出库结算查询参数
     * @return 出库结算信息
     */
    Page<OutStockSettlementVo> pages(OutStockSettlementPageParam pageParam);


    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    OutStockSettlementVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
