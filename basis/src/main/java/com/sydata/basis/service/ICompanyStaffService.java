package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.CompanyStaff;
import com.sydata.basis.param.CompanyStaffPageParam;
import com.sydata.basis.vo.CompanyStaffVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 基础数据-企业人员 Service接口
 *
 * @author lzq
 * @date 2022-08-19
 */
public interface ICompanyStaffService extends IService<CompanyStaff> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CompanyStaffVo> pages(CompanyStaffPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 企业人员信息VO
     */
    CompanyStaffVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
