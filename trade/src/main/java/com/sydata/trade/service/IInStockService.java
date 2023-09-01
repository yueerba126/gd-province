package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.InStock;
import com.sydata.trade.param.InStockPageParam;
import com.sydata.trade.vo.InStockVo;

import java.util.Collection;

/**
 * @Author chenzx
 * @Date 2022/8/19 11:04
 * @Description:
 * @Version 1.0
 */
public interface IInStockService extends IService<InStock> {

    /**
     * 分页列表
     *
     * @param pageParam 查询参数
     * @return 入库信息
     */
    Page<InStockVo> pages(InStockPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    InStockVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
