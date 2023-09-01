package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.CompanyCredit;
import com.sydata.basis.param.CompanyCreditPageParam;
import com.sydata.basis.vo.CompanyCreditVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 企业信用信息Service接口
 *
 * @author lzq
 * @date 2022-10-11
 */
public interface ICompanyCreditService extends IService<CompanyCredit> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CompanyCreditVo> pages(CompanyCreditPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    CompanyCreditVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

}