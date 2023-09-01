package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.Finance;
import com.sydata.basis.param.FinancePageParam;
import com.sydata.basis.param.FinanceReportDetailParam;
import com.sydata.basis.vo.FinanceVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.Map;

/**
 * 财务报信息Service接口
 *
 * @author system
 * @date 2022-12-07
 */
public interface IFinanceService extends IService<Finance> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<FinanceVo> pages(FinancePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    FinanceVo detail(String id);

    /**
     * 分页报表
     *
     * @param pageParam 分页参数
     * @return 分页报表
     */
    Page<FinanceVo> reportPages(FinancePageParam pageParam);

    /**
     * 报表详情
     *
     * @param param 财务报表详情查询参数
     * @return 报表详情
     */
    Map<Integer, Finance> reportDetail(FinanceReportDetailParam param);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}