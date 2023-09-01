package com.sydata.trade.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.TransferNatureApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.trade.annotation.DataBindTransferNature;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.TransferNatureReportParam;
import com.sydata.trade.domain.TransferNature;
import com.sydata.trade.mapper.TransferNatureMapper;
import com.sydata.trade.param.TransferNaturePageParam;
import com.sydata.trade.service.ITransferNatureService;
import com.sydata.trade.vo.TransferNatureVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-性质转变单单信息Service业务层处理
 *
 * @author lzq
 * @date 2022/8/18 17:48
 */
@CacheConfig(cacheNames = TransferNatureServiceImpl.CACHE_NAME)
@Service("transferNatureService")
public class TransferNatureServiceImpl
        extends BaseDataImpl<TransferNatureApiParam, TransferNatureMapper, TransferNature, TransferNatureReportParam>
        implements ITransferNatureService {

    final static String CACHE_NAME = "trade:transferNature";

    @Resource
    private TransferNatureMapper transferNatureMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public TransferNature getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(TransferNature entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(TransferNature entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(TransferNature entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<TransferNatureVo> pages(TransferNaturePageParam pageParam) {
        Page<TransferNature> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), TransferNature::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), TransferNature::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), TransferNature::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getLsxzzbdh()), TransferNature::getLsxzzbdh, pageParam.getLsxzzbdh())
                .eq(isNotEmpty(pageParam.getLspzdm()), TransferNature::getLspzdm, pageParam.getLspzdm())
                .eq(isNotEmpty(pageParam.getHzqlsxzdm()), TransferNature::getHzqlsxzdm, pageParam.getHzqlsxzdm())
                .eq(isNotEmpty(pageParam.getHzhlsxzdm()), TransferNature::getHzhlsxzdm, pageParam.getHzhlsxzdm())
                .eq(isNotEmpty(pageParam.getHzrq()), TransferNature::getHzrq, pageParam.getHzrq())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), TransferNature::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), TransferNature::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(TransferNature::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, TransferNatureVo.class);
    }

    @DataBindFieldConvert
    @Override
    public TransferNatureVo detail(String id) {
        ITransferNatureService transferNatureService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(transferNatureService.getById(id), TransferNatureVo.class);
    }

    @DataBindService(strategy = DataBindTransferNature.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, transferNatureMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.TRANSFER_NATURE;
    }
}
