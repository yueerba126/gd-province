/**
 * @filename:ApparitorSecureRiskUnitBeanServiceImpl 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2018 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.dostrict.storage.domain.ApparitorSecureRiskUnit;
import com.sydata.dostrict.storage.mapper.ApparitorSecureRiskUnitMapper;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskUnitPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskUnitSaveParam;
import com.sydata.dostrict.storage.service.IApparitorSecureRiskUnitService;
import com.sydata.dostrict.storage.vo.ApparitorSecureRiskUnitVo;
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
 * @Description:TODO(安全仓储-安全风险台账服务实现)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service
public class ApparitorSecureRiskUnitServiceImpl
        extends ServiceImpl<ApparitorSecureRiskUnitMapper, ApparitorSecureRiskUnit>
        implements IApparitorSecureRiskUnitService {
    
    @Override
    @DataBindFieldConvert
    public Page<ApparitorSecureRiskUnitVo> pages(ApparitorSecureRiskUnitPageParam pageParam) {
        Page<ApparitorSecureRiskUnit> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getDwdm()), ApparitorSecureRiskUnit::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getKqdm()), ApparitorSecureRiskUnit::getKqdm, pageParam.getKqdm())
                .ge(isNotEmpty(pageParam.getBeginJcsj()), ApparitorSecureRiskUnit::getInspectionTime, pageParam.getBeginJcsj())
                .le(isNotEmpty(pageParam.getEndJcsj()), ApparitorSecureRiskUnit::getInspectionTime, pageParam.getEndJcsj())
                .ne(ApparitorSecureRiskUnit::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorSecureRiskUnit::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorSecureRiskUnitVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorSecureRiskUnitVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), ApparitorSecureRiskUnitVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorSecureRiskUnitSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorSecureRiskUnit apparitorSecureRiskUnit = BeanUtils.copyByClass(param, ApparitorSecureRiskUnit.class);
        apparitorSecureRiskUnit.setId(IdUtil.simpleUUID());
        apparitorSecureRiskUnit.setCzbz(CzBzEnum.I.getCzBz());
        apparitorSecureRiskUnit.setCreateBy(loginUser.getName());
        apparitorSecureRiskUnit.setUpdateBy(loginUser.getName());
        apparitorSecureRiskUnit.setUpdateTime(LocalDateTime.now());
        apparitorSecureRiskUnit.setCreateTime(LocalDateTime.now());
        super.save(apparitorSecureRiskUnit);
        param.setId(apparitorSecureRiskUnit.getId());
        return apparitorSecureRiskUnit.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorSecureRiskUnitSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorSecureRiskUnit apparitorSecureRiskUnit = BeanUtils.copyByClass(param, ApparitorSecureRiskUnit.class);
        apparitorSecureRiskUnit.setCzbz(CzBzEnum.U.getCzBz());
        apparitorSecureRiskUnit.setUpdateBy(loginUser.getName());
        apparitorSecureRiskUnit.setUpdateTime(LocalDateTime.now());
        super.updateById(apparitorSecureRiskUnit);
        param.setId(apparitorSecureRiskUnit.getId());
        return apparitorSecureRiskUnit.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorSecureRiskUnit::getId, ids)
                .set(ApparitorSecureRiskUnit::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorSecureRiskUnit::getUpdateBy ,loginUser.getName())
                .set(ApparitorSecureRiskUnit::getUpdateTime ,LocalDateTime.now())
                .update();
    }
}