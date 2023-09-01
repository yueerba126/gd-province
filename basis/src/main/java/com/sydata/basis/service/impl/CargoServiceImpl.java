package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Cargo;
import com.sydata.basis.mapper.CargoMapper;
import com.sydata.basis.param.CargoPageParam;
import com.sydata.basis.service.ICargoService;
import com.sydata.basis.vo.CargoVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.CargoApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.CargoReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.CARGO;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 基础信息-货位信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = CargoServiceImpl.CACHE_NAME)
@Service("cargoService")
public class CargoServiceImpl extends BaseDataImpl<CargoApiParam, CargoMapper, Cargo, CargoReportParam>
        implements ICargoService {

    final static String CACHE_NAME = "basis:cargo";

    @Resource
    private CargoMapper cargoMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Cargo getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Cargo entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Cargo entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Cargo entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<CargoVo> pages(CargoPageParam pageParam) {
        Page<Cargo> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Cargo::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Cargo::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getAjdm()), Cargo::getId, pageParam.getAjdm())
                .likeRight(isNotEmpty(pageParam.getHwdm()), Cargo::getId, pageParam.getHwdm())
                .likeRight(isNotEmpty(pageParam.getHwmc()), Cargo::getHwmc, pageParam.getHwmc())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Cargo::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Cargo::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Cargo::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CargoVo.class);
    }

    @DataBindFieldConvert
    @Override
    public CargoVo detail(String id) {
        ICargoService locationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(locationService.getById(id), CargoVo.class);
    }

    @DataBindService(strategy = DataBindCargo.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, cargoMapper);
    }

    @Override
    public DataApiEnum api() {
        return CARGO;
    }
}