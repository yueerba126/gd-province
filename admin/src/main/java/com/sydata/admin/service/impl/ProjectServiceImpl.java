package com.sydata.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.domain.Project;
import com.sydata.admin.mapper.ProjectMapper;
import com.sydata.admin.param.ProjectPageParam;
import com.sydata.admin.service.IProjectService;
import com.sydata.admin.vo.ProjectVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.admin.ProjectApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.admin.annotation.DataBindProject;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.admin.ProjectReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 行政管理-项目信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-25
 */
@CacheConfig(cacheNames = ProjectServiceImpl.CACHE_NAME)
@Service("projectService")
public class ProjectServiceImpl extends BaseDataImpl<ProjectApiParam, ProjectMapper, Project, ProjectReportParam>
        implements IProjectService {

    final static String CACHE_NAME = "admin:project";

    @Resource
    private ProjectMapper projectMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Project getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Project entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Project entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Project entity) {
        return super.removeById(entity);
    }


    @DataBindFieldConvert
    @Override
    public Page<ProjectVo> pages(ProjectPageParam pageParam) {
        Page<Project> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Project::getId, pageParam.getId())
                .likeRight(isNotEmpty(pageParam.getFddbrzzhm()), Project::getFddbrzzhm, pageParam.getFddbrzzhm())
                .eq(isNotEmpty(pageParam.getJszt()), Project::getJszt, pageParam.getJszt())
                .eq(isNotEmpty(pageParam.getNf()), Project::getNf, pageParam.getNf())
                .eq(isNotEmpty(pageParam.getNkgsj()), Project::getNkgsj, pageParam.getNkgsj())
                .eq(isNotEmpty(pageParam.getNjcsj()), Project::getNjcsj, pageParam.getNjcsj())
                .likeRight(isNotEmpty(pageParam.getXmmc()), Project::getXmmc, pageParam.getXmmc())
                .likeRight(isNotEmpty(pageParam.getXmdm()), Project::getXmdm, pageParam.getXmdm())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Project::getZhgxsj, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Project::getZhgxsj, pageParam.getEndUpdateTime())
                .orderByDesc(Project::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ProjectVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ProjectVo detail(String id) {
        IProjectService projectService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(projectService.getById(id), ProjectVo.class);
    }

    @Override
    public String generate() {
        return IdWorker.getIdStr();
    }

    @DataBindService(strategy = DataBindProject.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, projectMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.PROJECT;
    }
}