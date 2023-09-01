package com.sydata.trade.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.TransferBarnApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.trade.annotation.DataBindTransferBarn;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.TransferBarnReportParam;
import com.sydata.trade.domain.TransferBarn;
import com.sydata.trade.mapper.TransferBarnMapper;
import com.sydata.trade.param.TransferBarnPageParam;
import com.sydata.trade.service.ITransferBarnService;
import com.sydata.trade.vo.TransferBarnVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


/**
 * 粮油购销-倒仓单信息Service业务层处理
 *
 * @author lzq
 * @date 2022/8/18 17:48
 */
@CacheConfig(cacheNames = TransferBarnServiceImpl.CACHE_NAME)
@Service("transferBarnService")
public class TransferBarnServiceImpl
        extends BaseDataImpl<TransferBarnApiParam, TransferBarnMapper, TransferBarn, TransferBarnReportParam>
        implements ITransferBarnService {

    final static String CACHE_NAME = "trade:transferBarn";

    @Resource
    private TransferBarnMapper transferBarnMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public TransferBarn getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(TransferBarn entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(TransferBarn entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(TransferBarn entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<TransferBarnVo> pages(TransferBarnPageParam pageParam) {
        Page<TransferBarn> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), TransferBarn::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), TransferBarn::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getDcdh()), TransferBarn::getDcdh, pageParam.getDcdh())
                .eq(isNotEmpty(pageParam.getDchwdm()), TransferBarn::getDchwdm, pageParam.getDchwdm())
                .eq(isNotEmpty(pageParam.getDrhwdm()), TransferBarn::getDrhwdm, pageParam.getDrhwdm())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), TransferBarn::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), TransferBarn::getUpdateTime, pageParam.getEndUpdateTime())
                .ge(isNotEmpty(pageParam.getDcrqkssj()), TransferBarn::getDcrq, pageParam.getDcrqkssj())
                .le(isNotEmpty(pageParam.getDcrqjssj()), TransferBarn::getDcrq, pageParam.getDcrqjssj())
                .orderByDesc(TransferBarn::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, TransferBarnVo.class);
    }


    @DataBindFieldConvert
    @Override
    public TransferBarnVo detail(String id) {
        ITransferBarnService transferBarnService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(transferBarnService.getById(id), TransferBarnVo.class);
    }

    @DataBindService(strategy = DataBindTransferBarn.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, transferBarnMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.TRANSFER_BARN;
    }

}
