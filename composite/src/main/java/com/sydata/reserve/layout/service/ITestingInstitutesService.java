package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.reserve.layout.domain.TestingInstitutes;
import com.sydata.reserve.layout.param.TestingInstitutesPageParam;
import com.sydata.reserve.layout.param.TestingInstitutesSaveParam;
import com.sydata.reserve.layout.param.TestingInstitutesUpdateParam;
import com.sydata.reserve.layout.vo.TestingInstitutesVo;


/**
 * 储备布局地理信息-质检机构Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface ITestingInstitutesService extends IService<TestingInstitutes> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<TestingInstitutesVo> pages(TestingInstitutesPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    TestingInstitutesVo detail(String id);


    Boolean save(TestingInstitutesSaveParam testingInstitutesSaveParam);

    Boolean update(TestingInstitutesUpdateParam testingInstitutesUpdateParam);

    Boolean updateStatus(String id, String sfqy);
}