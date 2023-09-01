package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.TransferNature;
import com.sydata.trade.param.TransferNaturePageParam;
import com.sydata.trade.vo.TransferNatureVo;

import java.util.Collection;

/**
 * @author lzq
 * @date 2022/8/19 9:48
 */
public interface ITransferNatureService extends IService<TransferNature> {

    /**
     * 分页查询
     *
     * @param pageParam 分页参数
     * @return Page<TransferNatureVO>
     */
    Page<TransferNatureVo> pages(TransferNaturePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    TransferNatureVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
