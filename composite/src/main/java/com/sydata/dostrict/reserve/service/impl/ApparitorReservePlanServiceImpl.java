package com.sydata.dostrict.reserve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.composite.annotation.DataBindApparitorReservePlan;
import com.sydata.dostrict.reserve.domain.ApparitorReservePlan;
import com.sydata.dostrict.reserve.domain.ApparitorReserveScale;
import com.sydata.dostrict.reserve.mapper.ApparitorReservePlanMapper;
import com.sydata.dostrict.reserve.mapper.ApparitorReserveScaleMapper;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanMasterSlaveParam;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanPageParam;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanSaveParam;
import com.sydata.dostrict.reserve.service.IApparitorReservePlanService;
import com.sydata.dostrict.reserve.service.IApparitorReserveScaleService;
import com.sydata.dostrict.reserve.vo.ApparitorReservePlanMasterSlaveVo;
import com.sydata.dostrict.reserve.vo.ApparitorReservePlanVo;
import com.sydata.dostrict.reserve.vo.ApparitorReserveScaleVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
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
 * @program: gd-province-platform
 * @description: 粮食储备-储备计划Service业务层处理
 * @author: lzq
 * @create: 2023-04-26 18:47
 */
@CacheConfig(cacheNames = ApparitorReservePlanServiceImpl.CACHE_NAME)
@Service("apparitorReservePlanService")
public class ApparitorReservePlanServiceImpl
        extends ServiceImpl<ApparitorReservePlanMapper, ApparitorReservePlan>
        implements IApparitorReservePlanService {

    final static String CACHE_NAME = "composite:apparitorReservePlan";

    @Resource
    private ApparitorReservePlanMapper apparitorReservePlanMapper;

    @Resource
    private ApparitorReserveScaleMapper apparitorReserveScaleMapper;

    @Resource
    private IApparitorReserveScaleService apparitorReserveScaleService;

    @DataBindFieldConvert
    @Override
    public Page<ApparitorReservePlanVo> pages(ApparitorReservePlanPageParam pageParam) {
        Page<ApparitorReservePlan> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ApparitorReservePlan::getId, pageParam.getId())
                .likeRight(isNotEmpty(pageParam.getJhwh()), ApparitorReservePlan::getJhwh, pageParam.getJhwh())
                .eq(isNotEmpty(pageParam.getJhzddw()), ApparitorReservePlan::getJhzddw, pageParam.getJhzddw())
                .eq(isNotEmpty(pageParam.getJhnd()), ApparitorReservePlan::getJhnd, pageParam.getJhnd())
                .like(isNotEmpty(pageParam.getJhmc()), ApparitorReservePlan::getJhmc, pageParam.getJhmc())
                .ge(isNotEmpty(pageParam.getBeginJhxdsj()), ApparitorReservePlan::getJhxdsj, pageParam.getBeginJhxdsj())
                .le(isNotEmpty(pageParam.getEndJhxdsj()), ApparitorReservePlan::getJhxdsj, pageParam.getEndJhxdsj())
                .eq(isNotEmpty(pageParam.getLspzdm()), ApparitorReservePlan::getLspzdm, pageParam.getLspzdm())
                .eq(isNotEmpty(pageParam.getLsdjdm()), ApparitorReservePlan::getLsdjdm, pageParam.getLsdjdm())
                .eq(isNotEmpty(pageParam.getLsxzdm()), ApparitorReservePlan::getLsxzdm, pageParam.getLsxzdm())
                .ne(ApparitorReservePlan::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorReservePlan::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorReservePlanVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorReservePlanVo detail(String id) {
        IApparitorReservePlanService reservePlanService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(reservePlanService.getById(id), ApparitorReservePlanVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorReservePlanMasterSlaveVo detailMasterSlave(String id) {
        IApparitorReservePlanService reservePlanService = SpringUtil.getBean(this.getClass());
        ApparitorReservePlan apparitorReservePlan = reservePlanService.getById(id);
        ApparitorReservePlanMasterSlaveVo apparitorReservePlanMasterSlaveVo = BeanUtils.copyByClass(apparitorReservePlan,
                ApparitorReservePlanMasterSlaveVo.class);

        QueryWrapper<ApparitorReserveScale> apparitorReserveScaleQueryWrapper = new QueryWrapper();
        apparitorReserveScaleQueryWrapper.lambda()
                .eq(StringUtils.isNotEmpty(id),ApparitorReserveScale::getCbjhId,id)
                .ne(ApparitorReserveScale::getCzbz,CzBzEnum.D);
        List<ApparitorReserveScale> apparitorReserveScaleList = apparitorReserveScaleMapper.selectList(apparitorReserveScaleQueryWrapper);
        List<ApparitorReserveScaleVo> apparitorReserveScaleVos = BeanUtils.copyToList(apparitorReserveScaleList, ApparitorReserveScaleVo.class);

        apparitorReservePlanMasterSlaveVo.setItemList(apparitorReserveScaleVos);
        return apparitorReservePlanMasterSlaveVo;
    }

    @DataBindService(strategy = DataBindApparitorReservePlan.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, apparitorReservePlanMapper);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorReservePlanSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorReservePlan apparitorReservePlan = BeanUtils.copyByClass(param, ApparitorReservePlan.class);
        apparitorReservePlan.setId(IdUtil.simpleUUID());
        apparitorReservePlan.setCzbz(CzBzEnum.I.getCzBz());
        apparitorReservePlan.setCreateBy(loginUser.getName());
        apparitorReservePlan.setUpdateBy(loginUser.getName());
        apparitorReservePlan.setUpdateTime(LocalDateTime.now());
        apparitorReservePlan.setCreateTime(LocalDateTime.now());
        apparitorReservePlan.setZhgxsj(LocalDateTime.now());
        super.save(apparitorReservePlan);
        param.setId(apparitorReservePlan.getId());
        return apparitorReservePlan.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateMasterSlaveData(ApparitorReservePlanMasterSlaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        // 更新主表
        ApparitorReservePlanSaveParam apparitorReservePlanMasterSlaveParam = BeanUtils.copyByClass(param, ApparitorReservePlanMasterSlaveParam.class);
        updateData(apparitorReservePlanMasterSlaveParam);
        // 新增或是更新从表
        List<ApparitorReserveScale> apparitorReserveScaleList = BeanUtils.copyToList(param.getItemList(),ApparitorReserveScale.class);
        for (int i = 0; i < apparitorReserveScaleList.size(); i++) {
            ApparitorReserveScale reserveScale = apparitorReserveScaleList.get(i);
            if(StringUtils.isNotEmpty(reserveScale.getId())){
                reserveScale.setUpdateBy(loginUser.getName());
                reserveScale.setUpdateTime(LocalDateTime.now());
                reserveScale.setZhgxsj(LocalDateTime.now());
            }else{
                reserveScale.setId(IdUtil.simpleUUID());
                reserveScale.setCbjhId(apparitorReservePlanMasterSlaveParam.getId());
                reserveScale.setCzbz(CzBzEnum.I.getCzBz());
                reserveScale.setCreateBy(loginUser.getName());
                reserveScale.setUpdateBy(loginUser.getName());
                reserveScale.setUpdateTime(LocalDateTime.now());
                reserveScale.setCreateTime(LocalDateTime.now());
                reserveScale.setZhgxsj(LocalDateTime.now());
            }
        }
        Boolean status = apparitorReserveScaleService.saveOrUpdateBatch(apparitorReserveScaleList);
        return status;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorReservePlanSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorReservePlan apparitorReservePlan = BeanUtils.copyByClass(param, ApparitorReservePlan.class);
        apparitorReservePlan.setCzbz(CzBzEnum.U.getCzBz());
        apparitorReservePlan.setUpdateBy(loginUser.getName());
        apparitorReservePlan.setUpdateTime(LocalDateTime.now());
        apparitorReservePlan.setZhgxsj(LocalDateTime.now());
        super.updateById(apparitorReservePlan);
        param.setId(apparitorReservePlan.getId());
        return apparitorReservePlan.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorReservePlan::getId, ids)
                .set(ApparitorReservePlan::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorReservePlan::getUpdateBy ,loginUser.getName())
                .set(ApparitorReservePlan::getUpdateTime ,LocalDateTime.now())
                .set(ApparitorReservePlan::getZhgxsj ,LocalDateTime.now())
                .update();
    }
}