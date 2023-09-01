package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Tank;
import com.sydata.basis.mapper.TankMapper;
import com.sydata.basis.param.TankPageParam;
import com.sydata.basis.service.ITankService;
import com.sydata.basis.vo.TankVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.TankApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.basis.annotation.DataBindTank;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.TankReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.TANK;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 基础信息-油罐信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = TankServiceImpl.CACHE_NAME)
@Service("tankService")
public class TankServiceImpl extends BaseDataImpl<TankApiParam, TankMapper, Tank, TankReportParam>
        implements ITankService {

    final static String CACHE_NAME = "basis:tank";

    @Resource
    private TankMapper tankMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Tank getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Tank entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Tank entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Tank entity) {
        return super.removeById(entity);
    }


    @DataBindFieldConvert
    @Override
    public Page<TankVo> pages(TankPageParam pageParam) {
        Page<Tank> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Tank::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Tank::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), Tank::getStockHouseId, pageParam.getStockHouseId())
                .likeRight(isNotEmpty(pageParam.getYgdm()), Tank::getId, pageParam.getYgdm())
                .likeRight(isNotEmpty(pageParam.getYgmc()), Tank::getYgmc, pageParam.getYgmc())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Tank::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Tank::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Tank::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, TankVo.class);
    }

    @DataBindFieldConvert
    @Override
    public TankVo detail(String id) {
        ITankService oilcanService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(oilcanService.getById(id), TankVo.class);
    }

    @DataBindService(strategy = DataBindTank.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, tankMapper);
    }

    @Override
    public DataApiEnum api() {
        return TANK;
    }
}