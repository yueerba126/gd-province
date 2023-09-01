/**
 * @filename:ApparitorSecureRiskIdenBeanServiceImpl 2023年04月28日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2018 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.dostrict.storage.domain.ApparitorSecureRiskIden;
import com.sydata.dostrict.storage.domain.ApparitorSecureRiskIden;
import com.sydata.dostrict.storage.mapper.ApparitorSecureRiskIdenMapper;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskIdenPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskIdenSaveParam;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskIdenPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskIdenSaveParam;
import com.sydata.dostrict.storage.service.IApparitorSecureRiskIdenService;
import com.sydata.dostrict.storage.vo.ApparitorSecureRiskIdenVo;
import com.sydata.dostrict.storage.vo.ApparitorSecureRiskIdenVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**   
 * @Description:TODO(安全仓储-风险智能识别服务实现)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service
public class ApparitorSecureRiskIdenServiceImpl
        extends ServiceImpl<ApparitorSecureRiskIdenMapper, ApparitorSecureRiskIden>
        implements IApparitorSecureRiskIdenService {

    @Override
    @DataBindFieldConvert
    public Page<ApparitorSecureRiskIdenVo> pages(ApparitorSecureRiskIdenPageParam pageParam) {
        Page<ApparitorSecureRiskIden> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getDwdm()), ApparitorSecureRiskIden::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getKqdm()), ApparitorSecureRiskIden::getKqdm, pageParam.getKqdm())
                .eq(isNotEmpty(pageParam.getRiskType()), ApparitorSecureRiskIden::getRiskType, pageParam.getRiskType())
                .ge(isNotEmpty(pageParam.getBeginYjrq()), ApparitorSecureRiskIden::getYjrq, pageParam.getBeginYjrq())
                .le(isNotEmpty(pageParam.getEndYjrq()), ApparitorSecureRiskIden::getYjrq, pageParam.getEndYjrq())
                .ne(ApparitorSecureRiskIden::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorSecureRiskIden::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorSecureRiskIdenVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorSecureRiskIdenVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), ApparitorSecureRiskIdenVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorSecureRiskIdenSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorSecureRiskIden apparitorSecureRiskIden = BeanUtils.copyByClass(param, ApparitorSecureRiskIden.class);
        apparitorSecureRiskIden.setId(IdUtil.simpleUUID());
        apparitorSecureRiskIden.setCzbz(CzBzEnum.I.getCzBz());
        apparitorSecureRiskIden.setCreateBy(loginUser.getName());
        apparitorSecureRiskIden.setUpdateBy(loginUser.getName());
        apparitorSecureRiskIden.setUpdateTime(LocalDateTime.now());
        apparitorSecureRiskIden.setCreateTime(LocalDateTime.now());
        super.save(apparitorSecureRiskIden);
        param.setId(apparitorSecureRiskIden.getId());
        return apparitorSecureRiskIden.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorSecureRiskIdenSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorSecureRiskIden apparitorSecureRiskIden = BeanUtils.copyByClass(param, ApparitorSecureRiskIden.class);
        apparitorSecureRiskIden.setCzbz(CzBzEnum.U.getCzBz());
        apparitorSecureRiskIden.setUpdateBy(loginUser.getName());
        apparitorSecureRiskIden.setUpdateTime(LocalDateTime.now());
        super.updateById(apparitorSecureRiskIden);
        param.setId(apparitorSecureRiskIden.getId());
        return apparitorSecureRiskIden.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorSecureRiskIden::getId, ids)
                .set(ApparitorSecureRiskIden::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorSecureRiskIden::getUpdateBy ,loginUser.getName())
                .set(ApparitorSecureRiskIden::getUpdateTime ,LocalDateTime.now())
                .update();
    }
}