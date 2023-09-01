package com.sydata.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.manage.domain.QualityInspection;
import com.sydata.manage.param.QualityInspectionPageParam;
import com.sydata.manage.vo.QualityInspectionVo;

import java.util.Collection;

/**
 * <p>
 * 质检信息表 服务类
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
public interface IQualityInspectionService extends IService<QualityInspection> {


    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<QualityInspectionVo> pages(QualityInspectionPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    QualityInspectionVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
