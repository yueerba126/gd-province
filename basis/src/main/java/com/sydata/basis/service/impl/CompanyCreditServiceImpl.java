package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.CargoLabel;
import com.sydata.basis.domain.CompanyCredit;
import com.sydata.basis.mapper.CompanyCreditMapper;
import com.sydata.basis.param.CompanyCreditPageParam;
import com.sydata.basis.service.ICompanyCreditService;
import com.sydata.basis.vo.CompanyCreditVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.CompanyCreditApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.basis.annotation.DataBindCompanyCredit;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.CompanyCreditReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.COMPANY_CREDIT;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 企业信用信息Service业务层处理
 *
 * @author lzq
 * @date 2022-10-11
 */
@CacheConfig(cacheNames = CompanyCreditServiceImpl.CACHE_NAME)
@Service("companyCreditService")
public class CompanyCreditServiceImpl
        extends BaseDataImpl<CompanyCreditApiParam, CompanyCreditMapper, CompanyCredit, CompanyCreditReportParam>
        implements ICompanyCreditService {

    final static String CACHE_NAME = "basis:companyCredit";

    @Resource
    private CompanyCreditMapper companyCreditMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public CompanyCredit getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(CompanyCredit entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(CompanyCredit entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(CompanyCredit entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<CompanyCreditVo> pages(CompanyCreditPageParam pageParam) {
        Page<CompanyCredit> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), CompanyCredit::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), CompanyCredit::getDwdm, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getXydj()), CompanyCredit::getXydj, pageParam.getXydj())
                .eq(isNotEmpty(pageParam.getPdnf()), CompanyCredit::getPdnf, pageParam.getPdnf())
                .likeRight(isNotEmpty(pageParam.getPddw()), CompanyCredit::getPddw, pageParam.getPddw())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), CompanyCredit::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), CompanyCredit::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(CompanyCredit::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CompanyCreditVo.class);
    }

    @DataBindFieldConvert
    @Override
    public CompanyCreditVo detail(String id) {
        ICompanyCreditService companyCreditService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(companyCreditService.getById(id), CompanyCreditVo.class);
    }

    @DataBindService(strategy = DataBindCompanyCredit.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, companyCreditMapper);
    }

    @Override
    public DataApiEnum api() {
        return COMPANY_CREDIT;
    }
}