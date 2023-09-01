package com.sydata.dostrict.plan.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.dostrict.plan.domain.ApparitorProject;
import com.sydata.dostrict.plan.mapper.ApparitorProjectMapper;
import com.sydata.dostrict.plan.param.ApparitorProjectPageParam;
import com.sydata.dostrict.plan.param.ApparitorProjectSaveParam;
import com.sydata.dostrict.plan.service.IApparitorProjectService;
import com.sydata.dostrict.plan.vo.ApparitorProjectVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


/**
 * @program: gd-province-platform
 * @description: 规划建设-项目管理 Service业务层处理
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
@Service("apparitorProjectService")
public class ApparitorProjectServiceImpl extends ServiceImpl<ApparitorProjectMapper, ApparitorProject> implements IApparitorProjectService {
    @Resource
    private ApparitorProjectMapper AdminProjectMapper;

    @Override
    @DataBindFieldConvert
    public Page<ApparitorProjectVo> pages(ApparitorProjectPageParam pageParam) {
        Page<ApparitorProject> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getId()), ApparitorProject::getId, pageParam.getId())
                .like(Objects.nonNull(pageParam.getXmmc()), ApparitorProject::getXmmc, pageParam.getXmmc())
                .eq(isNotEmpty(pageParam.getDwdm()), ApparitorProject::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getKqdm()), ApparitorProject::getKqdm, pageParam.getKqdm())
                .eq(isNotEmpty(pageParam.getXzqhdm()), ApparitorProject::getXzqhdm, pageParam.getXzqhdm())
                .ge(isNotEmpty(pageParam.getBeginSbrq()), ApparitorProject::getSbrq, pageParam.getBeginSbrq())
                .le(isNotEmpty(pageParam.getEndSbrq()), ApparitorProject::getSbrq, pageParam.getEndSbrq())
                .ne(ApparitorProject::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorProject::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorProjectVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorProjectVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), ApparitorProjectVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorProjectSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorProject apparitorProject = BeanUtils.copyByClass(param, ApparitorProject.class);
        apparitorProject.setId(IdUtil.simpleUUID());
        apparitorProject.setXmdm(IdWorker.getIdStr());
        apparitorProject.setCzbz(CzBzEnum.I.getCzBz());
        apparitorProject.setCreateBy(loginUser.getName());
        apparitorProject.setUpdateBy(loginUser.getName());
        apparitorProject.setUpdateTime(LocalDateTime.now());
        apparitorProject.setCreateTime(LocalDateTime.now());
        super.save(apparitorProject);
        param.setId(apparitorProject.getId());
        return apparitorProject.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorProjectSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorProject apparitorProject = BeanUtils.copyByClass(param, ApparitorProject.class);
        apparitorProject.setCzbz(CzBzEnum.U.getCzBz());
        apparitorProject.setUpdateBy(loginUser.getName());
        apparitorProject.setUpdateTime(LocalDateTime.now());
        super.updateById(apparitorProject);
        param.setId(apparitorProject.getId());
        return apparitorProject.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorProject::getId, ids)
                .set(ApparitorProject::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorProject::getUpdateBy ,loginUser.getName())
                .set(ApparitorProject::getUpdateTime ,LocalDateTime.now())
                .update();
    }
}
