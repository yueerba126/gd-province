package com.sydata.reserve.layout.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.CompanyStaffRecord;
import com.sydata.reserve.layout.mapper.CompanyStaffRecordMapper;
import com.sydata.reserve.layout.param.CompanyStaffRecordPageParam;
import com.sydata.reserve.layout.param.CompanyStaffRecordSaveParam;
import com.sydata.reserve.layout.param.CompanyStaffRecordUpdateParam;
import com.sydata.reserve.layout.service.ICompanyStaffRecordService;
import com.sydata.reserve.layout.vo.CompanyStaffRecordVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-廒间信息备案Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = CompanyStaffRecordServiceImpl.CACHE_NAME)
@Service("companyStaffRecordService")
public class CompanyStaffRecordServiceImpl
        extends ServiceImpl<CompanyStaffRecordMapper, CompanyStaffRecord>
        implements ICompanyStaffRecordService {

    final static String CACHE_NAME = "reserveLayout:CompanyStaffRecord";


    @Override
    public boolean removeById(CompanyStaffRecord entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<CompanyStaffRecordVo> pages(CompanyStaffRecordPageParam pageParam) {
        Page<CompanyStaffRecord> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getName()), CompanyStaffRecord::getXm, pageParam.getName())
                .eq(isNotEmpty(pageParam.getStatus()), CompanyStaffRecord::getStatus, pageParam.getStatus())
                .eq(isNotEmpty(pageParam.getDwdm()), CompanyStaffRecord::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getKqdm()), CompanyStaffRecord::getKqdm, pageParam.getKqdm())
                .orderByDesc(CompanyStaffRecord::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CompanyStaffRecordVo.class);
    }



    @DataBindFieldConvert
    @Override
    public CompanyStaffRecordVo detail(String id) {
        ICompanyStaffRecordService CompanyStaffRecordService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(CompanyStaffRecordService.getById(id), CompanyStaffRecordVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(CompanyStaffRecordSaveParam CompanyStaffRecordSaveParam) {
        CompanyStaffRecord companyStaffRecord = BeanUtils.copyByClass(CompanyStaffRecordSaveParam, CompanyStaffRecord.class);
        companyStaffRecord.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return super.save(companyStaffRecord);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(CompanyStaffRecordUpdateParam CompanyStaffRecordUpdateParam) {
        CompanyStaffRecord companyStaffRecord = this.getById(CompanyStaffRecordUpdateParam.getId());
        Assert.notNull(companyStaffRecord, "储备布局地理信息-廒间信息备案不存在");
        return super.updateById(BeanUtils.copyByClass(CompanyStaffRecordUpdateParam, CompanyStaffRecord.class));
    }

    @Override
    public Boolean updateStatus(String id, String status) {
        CompanyStaffRecord companyStaffRecord = this.getById(id);
        Assert.notNull(companyStaffRecord, "储备布局地理信息-廒间信息备案不存在");
        companyStaffRecord.setStatus(status);
        return super.updateById(companyStaffRecord);
    }


}