package com.sydata.basis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.CompanyStaff;
import com.sydata.basis.mapper.CompanyStaffMapper;
import com.sydata.basis.param.CompanyStaffPageParam;
import com.sydata.basis.service.ICompanyStaffService;
import com.sydata.basis.vo.CompanyStaffVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.CompanyStaffApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.basis.annotation.DataBindCompanyStaff;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.CompanyStaffReportParam;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.COMPANY_STAFF;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 基础数据-企业人员 Service业务层处理
 *
 * @author lzq
 * @date 2022-08-19
 */
@Service("companyStaffService")
@CacheConfig(cacheNames = CompanyStaffServiceImpl.CACHE_NAME)
@RequiredArgsConstructor
public class CompanyStaffServiceImpl
        extends BaseDataImpl<CompanyStaffApiParam, CompanyStaffMapper, CompanyStaff, CompanyStaffReportParam>
        implements ICompanyStaffService {

    private final CompanyStaffMapper companyStaffMapper;

    final static String CACHE_NAME = "basis:companyStaff";

    @Cacheable(key = "'id='+#id")
    @Override
    public CompanyStaff getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(CompanyStaff entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(CompanyStaff entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(CompanyStaff entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<CompanyStaffVo> pages(CompanyStaffPageParam pageParam) {
        Page<CompanyStaff> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), CompanyStaff::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), CompanyStaff::getEnterpriseId, pageParam.getEnterpriseId())
                .likeRight(isNotEmpty(pageParam.getDwmc()), CompanyStaff::getDwmc, pageParam.getDwmc())
                .eq(isNotEmpty(pageParam.getStockHouseId()), CompanyStaff::getStockHouseId, pageParam.getStockHouseId())
                .likeRight(isNotEmpty(pageParam.getXm()), CompanyStaff::getXm, pageParam.getXm())
                .likeRight(isNotEmpty(pageParam.getSfzhm()), CompanyStaff::getSfzhm, pageParam.getSfzhm())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), CompanyStaff::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), CompanyStaff::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(CompanyStaff::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CompanyStaffVo.class);
    }

    @DataBindFieldConvert
    @Override
    public CompanyStaffVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), CompanyStaffVo.class);
    }

    @DataBindService(strategy = DataBindCompanyStaff.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, companyStaffMapper);
    }

    @Override
    public DataApiEnum api() {
        return COMPANY_STAFF;
    }
}
