package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.reserve.layout.domain.CompanyStaffRecord;
import com.sydata.reserve.layout.param.CompanyStaffRecordPageParam;
import com.sydata.reserve.layout.param.CompanyStaffRecordSaveParam;
import com.sydata.reserve.layout.param.CompanyStaffRecordUpdateParam;
import com.sydata.reserve.layout.vo.CompanyStaffRecordVo;


/**
 * 储备布局地理信息-人员信息备案Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface ICompanyStaffRecordService extends IService<CompanyStaffRecord> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CompanyStaffRecordVo> pages(CompanyStaffRecordPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    CompanyStaffRecordVo detail(String id);


    Boolean save(CompanyStaffRecordSaveParam companyStaffRecordSaveParam);

    Boolean update(CompanyStaffRecordUpdateParam companyStaffRecordUpdateParam);

    Boolean updateStatus(String id, String status);
}