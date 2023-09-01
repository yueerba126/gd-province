package com.sydata.manage.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.SteamTaskInformationApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.manage.annotation.DataBindSteamTaskInformation;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.SteamTaskInformation;
import com.sydata.manage.mapper.SteamTaskInformationMapper;
import com.sydata.manage.param.SteamTaskInformationPageParam;
import com.sydata.manage.service.ISteamTaskInformationService;
import com.sydata.manage.vo.SteamTaskInformationVo;
import com.sydata.report.api.param.manage.SteamTaskInformationReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.STEAM_TASK_INFORMATION;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * <p>
 * 熏蒸作业信息 服务实现类
 * </p>
 *
 * @author lzq
 * @since 2022-05-07
 */
@CacheConfig(cacheNames = SteamTaskInformationServiceImpl.CACHE_NAME)
@Service("steamTaskInformationService")
public class SteamTaskInformationServiceImpl
        extends BaseDataImpl<SteamTaskInformationApiParam, SteamTaskInformationMapper, SteamTaskInformation, SteamTaskInformationReportParam>
        implements ISteamTaskInformationService {

    final static String CACHE_NAME = "manage:steamTaskInformation";

    @Resource
    private SteamTaskInformationMapper steamTaskInformationMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public SteamTaskInformation getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(SteamTaskInformation entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(SteamTaskInformation entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(SteamTaskInformation entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<SteamTaskInformationVo> pages(SteamTaskInformationPageParam pageParam) {
        Page<SteamTaskInformation> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), SteamTaskInformation::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), SteamTaskInformation::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getCfdm()), SteamTaskInformation::getCfdm, pageParam.getCfdm())
                .eq(isNotEmpty(pageParam.getXzzydh()), SteamTaskInformation::getXzzydh, pageParam.getXzzydh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), SteamTaskInformation::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), SteamTaskInformation::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(SteamTaskInformation::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, SteamTaskInformationVo.class);
    }

    @DataBindFieldConvert
    @Override
    public SteamTaskInformationVo detail(String id) {
        ISteamTaskInformationService locationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(locationService.getById(id), SteamTaskInformationVo.class);
    }

    @DataBindService(strategy = DataBindSteamTaskInformation.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, steamTaskInformationMapper);
    }

    @Override
    public DataApiEnum api() {
        return STEAM_TASK_INFORMATION;
    }

}
