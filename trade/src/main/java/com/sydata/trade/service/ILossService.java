package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.Loss;
import com.sydata.trade.param.LossPageParam;
import com.sydata.trade.vo.LossVo;

import java.util.Collection;

/**
 * @author lzq
 * @date 2022/8/19 9:37
 */
public interface ILossService extends IService<Loss> {

    /**
     * 分页查询
     *
     * @param pageParam 分页参数
     * @return Page<IncomeStatementVO>
     */
    Page<LossVo> pages(LossPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    LossVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
