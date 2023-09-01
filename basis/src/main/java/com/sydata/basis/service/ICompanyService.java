package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.Company;
import com.sydata.basis.param.CompanyPageParam;
import com.sydata.basis.vo.CompanyVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 基础数据-企业 Service接口
 *
 * @author lzq
 * @date 2022-08-19
 */
public interface ICompanyService extends IService<Company> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CompanyVo> pages(CompanyPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键
     * @return 分页列表
     */
    CompanyVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
