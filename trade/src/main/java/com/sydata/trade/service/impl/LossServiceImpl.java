package com.sydata.trade.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.LossApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.trade.annotation.DataBindLoss;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.LossReportParam;
import com.sydata.trade.domain.Loss;
import com.sydata.trade.mapper.LossMapper;
import com.sydata.trade.param.LossPageParam;
import com.sydata.trade.service.ILossService;
import com.sydata.trade.vo.LossVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-损益单Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@CacheConfig(cacheNames = LossServiceImpl.CACHE_NAME)
@Service("lossService")
public class LossServiceImpl extends BaseDataImpl<LossApiParam, LossMapper, Loss, LossReportParam>
        implements ILossService {

    final static String CACHE_NAME = "trade:loss";

    @Resource
    private LossMapper lossMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Loss getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Loss entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Loss entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Loss entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<LossVo> pages(LossPageParam pageParam) {
        Page<Loss> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Loss::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Loss::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), Loss::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getSydh()), Loss::getSydh, pageParam.getSydh())
                .eq(isNotEmpty(pageParam.getYwrq()), Loss::getYwrq, pageParam.getYwrq())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Loss::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Loss::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Loss::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, LossVo.class);
    }

    @DataBindFieldConvert
    @Override
    public LossVo detail(String id) {
        ILossService lossService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(lossService.getById(id), LossVo.class);
    }

    @DataBindService(strategy = DataBindLoss.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, lossMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.LOSS;
    }
}
