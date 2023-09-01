package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.reserve.layout.domain.ReserveCompany;
import com.sydata.reserve.layout.param.ReserveCompanyPageParam;
import com.sydata.reserve.layout.param.ReserveCompanySaveParam;
import com.sydata.reserve.layout.param.ReserveCompanyUpdateParam;
import com.sydata.reserve.layout.vo.ReserveCompanyVo;

import java.util.Collection;

/**
 * 储备布局地理信息-企业 Service接口
 *
 * @author lzq
 * @date 2022-08-19
 */
public interface IReserveCompanyService extends IService<ReserveCompany> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ReserveCompanyVo> pages(ReserveCompanyPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键
     * @return 分页列表
     */
    ReserveCompanyVo detail(String id);

    Boolean save(ReserveCompanySaveParam companySaveParam);

    Boolean update(ReserveCompanyUpdateParam companyUpdateParam);

    Boolean updateStatus(String id, String status);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
