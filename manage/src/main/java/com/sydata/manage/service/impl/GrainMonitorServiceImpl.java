package com.sydata.manage.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.GrainMonitorApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.manage.annotation.DataBindGrainMonitor;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.GrainMonitor;
import com.sydata.manage.mapper.GrainMonitorMapper;
import com.sydata.manage.param.GrainMonitorPageParam;
import com.sydata.manage.service.IGrainMonitorService;
import com.sydata.manage.vo.GrainMonitorVo;
import com.sydata.report.api.param.manage.GrainMonitorReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.GRAIN_MONITOR;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 温湿度监测信息接口实现类
 *
 * @author lzq
 * @date 2022/8/19 10:52
 */
@CacheConfig(cacheNames = GrainMonitorServiceImpl.CACHE_NAME)
@Service("grainMonitorService")
public class GrainMonitorServiceImpl
        extends BaseDataImpl<GrainMonitorApiParam, GrainMonitorMapper, GrainMonitor, GrainMonitorReportParam>
        implements IGrainMonitorService {

    final static String CACHE_NAME = "manage:grainMonitor";

    @Resource
    private GrainMonitorMapper grainMonitorMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public GrainMonitor getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(GrainMonitor entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(GrainMonitor entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(GrainMonitor entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<GrainMonitorVo> pages(GrainMonitorPageParam pageParam) {
        Page<GrainMonitor> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getEnterpriseId()), GrainMonitor::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), GrainMonitor::getStockHouseId, pageParam.getStockHouseId())
                .eq(isNotEmpty(pageParam.getHwdm()), GrainMonitor::getHwdm, pageParam.getHwdm())
                .ge(isNotEmpty(pageParam.getBeginJcsj()), GrainMonitor::getJcsj, pageParam.getBeginJcsj())
                .le(isNotEmpty(pageParam.getEndJcsj()), GrainMonitor::getJcsj, pageParam.getEndJcsj())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), GrainMonitor::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), GrainMonitor::getUpdateTime, pageParam.getEndUpdateTime())
                .likeRight(isNotEmpty(pageParam.getId()), GrainMonitor::getId, pageParam.getId())
                .orderByDesc(GrainMonitor::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GrainMonitorVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GrainMonitorVo detail(String id) {
        IGrainMonitorService locationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(locationService.getById(id), GrainMonitorVo.class);
    }

    @DataBindService(strategy = DataBindGrainMonitor.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, grainMonitorMapper);
    }

    @Override
    public DataApiEnum api() {
        return GRAIN_MONITOR;
    }

}
