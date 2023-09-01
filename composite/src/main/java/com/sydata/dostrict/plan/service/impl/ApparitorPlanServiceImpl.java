package com.sydata.dostrict.plan.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.dostrict.plan.domain.ApparitorPlan;
import com.sydata.dostrict.plan.mapper.ApparitorPlanMapper;
import com.sydata.dostrict.plan.param.ApparitorPlanPageParam;
import com.sydata.dostrict.plan.param.ApparitorPlanSaveParam;
import com.sydata.dostrict.plan.service.IApparitorPlanService;
import com.sydata.dostrict.plan.vo.ApparitorPlanVo;
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
 * @description: 规划建设-仓储设施建设管理 Service业务层处理
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
@Service("apparitorPlanService")
public class ApparitorPlanServiceImpl extends ServiceImpl<ApparitorPlanMapper, ApparitorPlan> implements IApparitorPlanService {

    @Resource
    private ApparitorPlanMapper apparitorPlanMapper;

    @Override
    @DataBindFieldConvert
    public Page<ApparitorPlanVo> pages(ApparitorPlanPageParam pageParam) {
        Page<ApparitorPlan> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getId()), ApparitorPlan::getId, pageParam.getId())
                .like(Objects.nonNull(pageParam.getXmmc()), ApparitorPlan::getXmmc, pageParam.getXmmc())
                .eq(isNotEmpty(pageParam.getDwdm()), ApparitorPlan::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getKqdm()), ApparitorPlan::getKqdm, pageParam.getKqdm())
                .eq(isNotEmpty(pageParam.getXzqhdm()), ApparitorPlan::getXzqhdm, pageParam.getXzqhdm())
                .ge(isNotEmpty(pageParam.getBeginLxrq()), ApparitorPlan::getLxrq, pageParam.getBeginLxrq())
                .le(isNotEmpty(pageParam.getEndLxrq()), ApparitorPlan::getLxrq, pageParam.getEndLxrq())
                .ne(ApparitorPlan::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorPlan::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorPlanVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorPlanVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), ApparitorPlanVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorPlanSaveParam param) {
        ApparitorPlan apparitorPlan = BeanUtils.copyByClass(param, ApparitorPlan.class);
        apparitorPlan.setId(IdUtil.simpleUUID());
        apparitorPlan.setXmdm(IdWorker.getIdStr());
        apparitorPlan.setCzbz(CzBzEnum.I.getCzBz());
        super.save(apparitorPlan);
        param.setId(apparitorPlan.getId());
        return apparitorPlan.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorPlanSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorPlan apparitorPlan = BeanUtils.copyByClass(param, ApparitorPlan.class);
        apparitorPlan.setCzbz(CzBzEnum.U.getCzBz());
        apparitorPlan.setUpdateBy(loginUser.getName());
        apparitorPlan.setUpdateTime(LocalDateTime.now());
        super.updateById(apparitorPlan);
        param.setId(apparitorPlan.getId());
        return apparitorPlan.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorPlan::getId, ids)
                .set(ApparitorPlan::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorPlan::getUpdateBy ,loginUser.getName())
                .set(ApparitorPlan::getUpdateTime ,LocalDateTime.now())
                .update();
    }

}
