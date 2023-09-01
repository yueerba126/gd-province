package com.sydata.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.admin.domain.Project;
import com.sydata.admin.param.ProjectPageParam;
import com.sydata.admin.vo.ProjectVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 行政管理-项目信息Service接口
 *
 * @author lzq
 * @date 2022-07-25
 */
public interface IProjectService extends IService<Project> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ProjectVo> pages(ProjectPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ProjectVo detail(String id);

    /**
     * 生成项目代码
     *
     * @return 项目代码
     */
    String generate();

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}