package com.sydata.manage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.ViolationWarningApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.manage.annotation.DataBindViolationWarning;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.ViolationWarning;
import com.sydata.manage.mapper.ViolationWarningMapper;
import com.sydata.manage.param.ViolationWarningPageParam;
import com.sydata.manage.service.IViolationWarningService;
import com.sydata.manage.vo.ViolationWarningVo;
import com.sydata.report.api.param.manage.ViolationWarningReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.VIOLATION_WARNING;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮库管理-违规预警信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-25
 */
@CacheConfig(cacheNames = ViolationWarningServiceImpl.CACHE_NAME)
@Service("violationWarningService")
public class ViolationWarningServiceImpl
        extends BaseDataImpl<ViolationWarningApiParam, ViolationWarningMapper, ViolationWarning, ViolationWarningReportParam>
        implements IViolationWarningService {
    final static String CACHE_NAME = "manage:violationWarning";

    @Resource
    private ViolationWarningMapper violationWarningMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public ViolationWarning getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(ViolationWarning entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(ViolationWarning entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(ViolationWarning entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<ViolationWarningVo> pages(ViolationWarningPageParam pageParam) {
        Page<ViolationWarning> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ViolationWarning::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), ViolationWarning::getEnterpriseId, pageParam.getEnterpriseId())
                .ge(isNotEmpty(pageParam.getBeginFbsj()), ViolationWarning::getFbsj, pageParam.getBeginFbsj())
                .le(isNotEmpty(pageParam.getEndFbsj()), ViolationWarning::getFbsj, pageParam.getEndFbsj())
                .likeRight(isNotEmpty(pageParam.getQydm()), ViolationWarning::getQydm, pageParam.getQydm())
                .eq(isNotEmpty(pageParam.getWglx()), ViolationWarning::getWglx, pageParam.getWglx())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ViolationWarning::getZhgxsj, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ViolationWarning::getZhgxsj, pageParam.getEndUpdateTime())
                .orderByDesc(ViolationWarning::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ViolationWarningVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ViolationWarning detail(String id) {
        return violationWarningMapper.selectById(id);
    }

    @DataBindService(strategy = DataBindViolationWarning.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, violationWarningMapper);
    }


    @Override
    public DataApiEnum api() {
        return VIOLATION_WARNING;
    }

}