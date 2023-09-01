package com.sydata.reserve.layout.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.basis.mapper.CompanyMapper;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.composite.annotation.DataBindReserveCompany;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.ReserveCompany;
import com.sydata.reserve.layout.mapper.ReserveCompanyMapper;
import com.sydata.reserve.layout.param.ReserveCompanyPageParam;
import com.sydata.reserve.layout.param.ReserveCompanySaveParam;
import com.sydata.reserve.layout.param.ReserveCompanyUpdateParam;
import com.sydata.reserve.layout.service.IReserveCompanyService;
import com.sydata.reserve.layout.utils.RegionUtil;
import com.sydata.reserve.layout.vo.ReserveCompanyVo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-企业 Service业务层处理
 *
 * @author lzq
 * @date 2022-08-19
 */
@Service("reserveCompanyService")
@CacheConfig(cacheNames = ReserveCompanyServiceImpl.CACHE_NAME)
@RequiredArgsConstructor
public class ReserveCompanyServiceImpl extends ServiceImpl<ReserveCompanyMapper, ReserveCompany>
        implements IReserveCompanyService {

    private final ReserveCompanyMapper reserveCompanyMapper;

    final static String CACHE_NAME = "reserveLayout:company";

    @Cacheable(key = "'id='+#id")
    @Override
    public ReserveCompany getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(ReserveCompany entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(ReserveCompany entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(ReserveCompany entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<ReserveCompanyVo> pages(ReserveCompanyPageParam pageParam) {
        pageParam.setXzqhdm(RegionUtil.regionQuery(pageParam.getXzqhdm()));
        Page<ReserveCompany> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getDwmc()), ReserveCompany::getDwmc, pageParam.getDwmc())
                .likeRight(isNotEmpty(pageParam.getXzqhdm()), ReserveCompany::getXzqhdm, pageParam.getXzqhdm())
                .eq(isNotEmpty(pageParam.getStatus()), ReserveCompany::getStatus, pageParam.getStatus())
                .eq(isNotEmpty(pageParam.getId()), ReserveCompany::getId, pageParam.getId())
                .orderByDesc(ReserveCompany::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ReserveCompanyVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ReserveCompanyVo detail(String id) {
        IReserveCompanyService companyService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(companyService.getById(id), ReserveCompanyVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(ReserveCompanySaveParam companySaveParam) {
        ReserveCompany reserveCompany = BeanUtils.copyByClass(companySaveParam, ReserveCompany.class);
        return super.save(reserveCompany.setId(reserveCompany.getDwdm()));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(ReserveCompanyUpdateParam companyUpdateParam) {
        ReserveCompany reserveCompany = this.getById(companyUpdateParam.getId());
        Assert.notNull(reserveCompany, "储备布局企业信息不存在");
        return super.updateById(BeanUtils.copyByClass(companyUpdateParam, ReserveCompany.class));
    }

    @Override
    public Boolean updateStatus(String id, String status) {
        ReserveCompany reserveCompany = this.getById(id);
        Assert.notNull(reserveCompany, "储备布局企业信息不存在");
        return super.updateById(reserveCompany.setStatus(status));
    }

    @DataBindService(strategy = DataBindReserveCompany.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, reserveCompanyMapper);
    }

}
