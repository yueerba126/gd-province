package com.sydata.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.domain.ReserveScale;
import com.sydata.admin.mapper.ReserveScaleMapper;
import com.sydata.admin.param.ReserveScalePageParam;
import com.sydata.admin.service.IReserveScaleService;
import com.sydata.admin.vo.ReserveScaleVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.admin.ReserveScaleApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.admin.annotation.DataBindReserveScale;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.admin.ReserveScaleReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 行政管理-储备规模Service业务层处理
 *
 * @author lzq
 * @date 2022-07-25
 */
@CacheConfig(cacheNames = ReserveScaleServiceImpl.CACHE_NAME)
@Service("reserveScaleService")
public class ReserveScaleServiceImpl
        extends BaseDataImpl<ReserveScaleApiParam, ReserveScaleMapper, ReserveScale, ReserveScaleReportParam>
        implements IReserveScaleService {

    final static String CACHE_NAME = "admin:reserveScale";


    @Resource
    private ReserveScaleMapper reserveScaleMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public ReserveScale getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(ReserveScale entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(ReserveScale entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(ReserveScale entity) {
        return super.removeById(entity);
    }


    @DataBindFieldConvert
    @Override
    public Page<ReserveScaleVo> pages(ReserveScalePageParam pageParam) {
        Page<ReserveScale> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ReserveScale::getId, pageParam.getId())
                .likeRight(isNotEmpty(pageParam.getCbgmjhwh()), ReserveScale::getCbgmjhwh, pageParam.getCbgmjhwh())
                .eq(isNotEmpty(pageParam.getYlpz()), ReserveScale::getYlpz, pageParam.getYlpz())
                .eq(isNotEmpty(pageParam.getYlxz()), ReserveScale::getYlxz, pageParam.getYlxz())
                .eq(isNotEmpty(pageParam.getNf()), ReserveScale::getNf, pageParam.getNf())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ReserveScale::getZhgxsj, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ReserveScale::getZhgxsj, pageParam.getEndUpdateTime())
                .orderByDesc(ReserveScale::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ReserveScaleVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ReserveScaleVo detail(String id) {
        IReserveScaleService reserveScaleService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(reserveScaleService.getById(id), ReserveScaleVo.class);
    }

    @DataBindService(strategy = DataBindReserveScale.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, reserveScaleMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.RESERVE_SCALE;
    }
}