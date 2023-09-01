package com.sydata.dostrict.personnel.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.dostrict.personnel.domain.ApparitorPersonnel;
import com.sydata.dostrict.personnel.mapper.ApparitorPersonnelMapper;
import com.sydata.dostrict.personnel.param.ApparitorPersonnelPageParam;
import com.sydata.dostrict.personnel.param.ApparitorPersonnelSaveParam;
import com.sydata.dostrict.personnel.service.IApparitorPersonnelService;
import com.sydata.dostrict.personnel.vo.ApparitorPersonnelVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.common.composite.annotation.DataBindPersonnel;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 行政管理模块--企业人员 Service业务层处理
 *
 * @author fuql
 * @date 2022-08-19
 */
@CacheConfig(cacheNames = ApparitorPersonnelServiceImpl.CACHE_NAME)
@Service("apparitorPersonnelService")
public class ApparitorPersonnelServiceImpl extends ServiceImpl<ApparitorPersonnelMapper, ApparitorPersonnel>
        implements IApparitorPersonnelService {

    @Resource
    private ApparitorPersonnelMapper apparitorPersonnelMapper;

    final static String CACHE_NAME = "composite:apparitorPersonnel";

    @Override
    @DataBindFieldConvert
    public Page<ApparitorPersonnelVo> pages(ApparitorPersonnelPageParam pageParam) {
        Page<ApparitorPersonnel> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ApparitorPersonnel::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), ApparitorPersonnel::getEnterpriseId, pageParam.getEnterpriseId())
                .likeRight(isNotEmpty(pageParam.getDwmc()), ApparitorPersonnel::getDwmc, pageParam.getDwmc())
                .eq(isNotEmpty(pageParam.getStockHouseId()), ApparitorPersonnel::getStockHouseId, pageParam.getStockHouseId())
                .likeRight(isNotEmpty(pageParam.getXm()), ApparitorPersonnel::getXm, pageParam.getXm())
                .likeRight(isNotEmpty(pageParam.getSfzhm()), ApparitorPersonnel::getSfzhm, pageParam.getSfzhm())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ApparitorPersonnel::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ApparitorPersonnel::getUpdateTime, pageParam.getEndUpdateTime())
                .ge(isNotEmpty(pageParam.getBeginRzrq()), ApparitorPersonnel::getRzrq, pageParam.getBeginRzrq())
                .le(isNotEmpty(pageParam.getEndRzrq()), ApparitorPersonnel::getRzrq, pageParam.getEndRzrq())
                .eq(isNotEmpty(pageParam.getKqdm()), ApparitorPersonnel::getKqdm, pageParam.getKqdm())
                .eq(isNotEmpty(pageParam.getDwdm()), ApparitorPersonnel::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getZgzt()), ApparitorPersonnel::getZgzt, pageParam.getZgzt())
                .eq(isNotEmpty(pageParam.getZzmm()), ApparitorPersonnel::getZzmm, pageParam.getZzmm())
                .eq(isNotEmpty(pageParam.getGwxz()), ApparitorPersonnel::getGwxz, pageParam.getGwxz())
                .eq(isNotEmpty(pageParam.getRylb()), ApparitorPersonnel::getGwxz, pageParam.getRylb())
                .ne(ApparitorPersonnel::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorPersonnel::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorPersonnelVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorPersonnelVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), ApparitorPersonnelVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorPersonnelSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorPersonnel staff = BeanUtils.copyByClass(param, ApparitorPersonnel.class);
        staff.setId(param.buildId());
        staff.setCzbz(CzBzEnum.I.getCzBz());
        staff.setStockHouseId(param.getKqdm());
        staff.setCreateBy(loginUser.getName());
        staff.setUpdateBy(loginUser.getName());
        staff.setUpdateTime(LocalDateTime.now());
        staff.setCreateTime(LocalDateTime.now());
        super.save(staff);
        param.setId(staff.getId());
        return staff.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorPersonnelSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorPersonnel staff = BeanUtils.copyByClass(param, ApparitorPersonnel.class);
        staff.setCzbz(CzBzEnum.U.getCzBz());
        staff.setStockHouseId(param.getKqdm());
        staff.setUpdateBy(loginUser.getName());
        staff.setUpdateTime(LocalDateTime.now());
        super.updateById(staff);
        param.setId(staff.getId());
        return staff.getId();
    }

    @DataBindService(strategy = DataBindPersonnel.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, apparitorPersonnelMapper);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorPersonnel::getId, ids)
                .set(ApparitorPersonnel::getCzbz, CzBzEnum.D.getCzBz())
                .set(ApparitorPersonnel::getUpdateBy, loginUser.getName())
                .set(ApparitorPersonnel::getUpdateTime, LocalDateTime.now())
                .update();
    }

}
