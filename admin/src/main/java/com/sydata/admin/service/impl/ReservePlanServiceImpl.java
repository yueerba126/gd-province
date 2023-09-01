package com.sydata.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.domain.ReservePlan;
import com.sydata.admin.mapper.ReservePlanMapper;
import com.sydata.admin.param.ReservePlanPageParam;
import com.sydata.admin.service.IReservePlanService;
import com.sydata.admin.vo.ReservePlanVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.admin.ReservePlanApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.admin.annotation.DataBindReservePlan;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.admin.ReservePlanReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 行政管理-储备计划Service业务层处理
 *
 * @author lzq
 * @date 2022-07-25
 */
@CacheConfig(cacheNames = ReservePlanServiceImpl.CACHE_NAME)
@Service("reservePlanService")
public class ReservePlanServiceImpl
        extends BaseDataImpl<ReservePlanApiParam, ReservePlanMapper, ReservePlan, ReservePlanReportParam>
        implements IReservePlanService {


    final static String CACHE_NAME = "admin:reservePlan";

    @Resource
    private ReservePlanMapper reservePlanMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public ReservePlan getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(ReservePlan entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(ReservePlan entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(ReservePlan entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<ReservePlanVo> pages(ReservePlanPageParam pageParam) {
        Page<ReservePlan> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ReservePlan::getId, pageParam.getId())
                .likeRight(isNotEmpty(pageParam.getJhwh()), ReservePlan::getJhwh, pageParam.getJhwh())
                .likeRight(isNotEmpty(pageParam.getJhmc()), ReservePlan::getJhmc, pageParam.getJhmc())
                .eq(isNotEmpty(pageParam.getJhnd()), ReservePlan::getJhnd, pageParam.getJhnd())
                .eq(isNotEmpty(pageParam.getJhxdsj()), ReservePlan::getJhxdsj, pageParam.getJhxdsj())
                .eq(isNotEmpty(pageParam.getLspzdm()), ReservePlan::getLspzdm, pageParam.getLspzdm())
                .eq(isNotEmpty(pageParam.getLsdjdm()), ReservePlan::getLsdjdm, pageParam.getLsdjdm())
                .eq(isNotEmpty(pageParam.getLsxzdm()), ReservePlan::getLsxzdm, pageParam.getLsxzdm())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ReservePlan::getZhgxsj, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ReservePlan::getZhgxsj, pageParam.getEndUpdateTime())
                .orderByDesc(ReservePlan::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ReservePlanVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ReservePlanVo detail(String id) {
        IReservePlanService reservePlanService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(reservePlanService.getById(id), ReservePlanVo.class);
    }

    @DataBindService(strategy = DataBindReservePlan.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, reservePlanMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.RESERVE_PLAN;
    }
}