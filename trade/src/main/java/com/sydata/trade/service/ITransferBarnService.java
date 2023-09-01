package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.TransferBarn;
import com.sydata.trade.param.TransferBarnPageParam;
import com.sydata.trade.vo.TransferBarnVo;

import java.util.Collection;

/**
 * @author lzq
 * @date 2022/8/18 17:48
 */
public interface ITransferBarnService extends IService<TransferBarn> {

    /**
     * 分页查询
     *
     * @param pageParam 分页参数
     * @return Page<OutStockMoveVo>
     */
    Page<TransferBarnVo> pages(TransferBarnPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    TransferBarnVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
