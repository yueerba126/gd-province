package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.Customer;
import com.sydata.trade.param.CustomerPageParam;
import com.sydata.trade.vo.CustomerVo;

import java.util.Collection;

/**
 * 粮油购销-客户Service接口
 *
 * @author lzq
 * @date 2022-07-22
 */
public interface ICustomerService extends IService<Customer> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CustomerVo> pages(CustomerPageParam pageParam);


    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    CustomerVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
