package com.sydata.manage.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.GasInformationApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.manage.annotation.DataBindGasInformation;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.GasInformation;
import com.sydata.manage.mapper.GasInformationMapper;
import com.sydata.manage.param.GasInformationPageParam;
import com.sydata.manage.service.IGasInformationService;
import com.sydata.manage.vo.GasInformationVo;
import com.sydata.report.api.param.manage.GasInformationReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.GAS_INFORMATION;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * <p>
 * 气体信息表服务实现类
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@CacheConfig(cacheNames = GasInformationServiceImpl.CACHE_NAME)
@Service("gasInformationService")
public class GasInformationServiceImpl
        extends BaseDataImpl<GasInformationApiParam, GasInformationMapper, GasInformation, GasInformationReportParam>
        implements IGasInformationService {

    final static String CACHE_NAME = "manage:gasInformation";

    @Resource
    private GasInformationMapper gasInformationMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public GasInformation getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(GasInformation entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(GasInformation entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(GasInformation entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<GasInformationVo> pages(GasInformationPageParam pageParam) {
        Page<GasInformation> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), GasInformation::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), GasInformation::getEnterpriseId, pageParam.getEnterpriseId())
                .ge(isNotEmpty(pageParam.getBeginJcsj()), GasInformation::getJcsj, pageParam.getBeginJcsj())
                .le(isNotEmpty(pageParam.getEndJcsj()), GasInformation::getJcsj, pageParam.getEndJcsj())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), GasInformation::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), GasInformation::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(GasInformation::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GasInformationVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GasInformationVo detail(String id) {
        IGasInformationService locationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(locationService.getById(id), GasInformationVo.class);
    }

    @DataBindService(strategy = DataBindGasInformation.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gasInformationMapper);
    }

    @Override
    public DataApiEnum api() {
        return GAS_INFORMATION;
    }

}
