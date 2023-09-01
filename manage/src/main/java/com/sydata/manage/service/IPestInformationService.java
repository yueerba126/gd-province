package com.sydata.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.manage.domain.PestInformation;
import com.sydata.manage.param.PestInformationPageParam;
import com.sydata.manage.vo.PestInformationVo;

import java.util.Collection;

/**
 * <p>
 * 虫害信息表 服务类
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
public interface IPestInformationService extends IService<PestInformation> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<PestInformationVo> pages(PestInformationPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    PestInformationVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
