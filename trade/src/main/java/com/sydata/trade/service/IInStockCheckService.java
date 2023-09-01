package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.InStockCheck;
import com.sydata.trade.param.InStockCheckPageParam;
import com.sydata.trade.vo.InStockCheckVo;

import java.util.Collection;

/**
 * @Author chenzx
 * @Date 2022/8/19 14:06
 * @Description:
 * @Version 1.0
 */
public interface IInStockCheckService extends IService<InStockCheck> {

    /**
     * 分页列表
     *
     * @param pageParam 查询参数
     * @return 入库检验信息
     */
    Page<InStockCheckVo> pages(InStockCheckPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    InStockCheckVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
