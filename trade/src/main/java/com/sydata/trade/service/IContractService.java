package com.sydata.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.Contract;
import com.sydata.trade.param.ContractPageParam;
import com.sydata.trade.vo.ContractVo;

import java.util.Collection;

/**
 * @author lzq
 * @date 2022/8/19 11:42
 */
public interface IContractService extends IService<Contract> {


    /**
     * 分页查询
     *
     * @param pageParam 分页参数
     * @return Page<ContractsVO>
     */
    Page<ContractVo> pages(ContractPageParam pageParam);


    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ContractVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
