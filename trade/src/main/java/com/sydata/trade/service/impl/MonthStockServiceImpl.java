package com.sydata.trade.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.MonthStockApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.trade.annotation.DataBindMonthStock;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.MonthStockReportParam;
import com.sydata.trade.domain.MonthStock;
import com.sydata.trade.mapper.MonthStockMapper;
import com.sydata.trade.param.MonthStockPageParam;
import com.sydata.trade.service.IMonthStockService;
import com.sydata.trade.vo.MonthStockVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-账面库存Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@CacheConfig(cacheNames = MonthStockServiceImpl.CACHE_NAME)
@Service("monthStockService")
public class MonthStockServiceImpl
        extends BaseDataImpl<MonthStockApiParam, MonthStockMapper, MonthStock, MonthStockReportParam>
        implements IMonthStockService {

    final static String CACHE_NAME = "trade:monthStock";

    @Resource
    private MonthStockMapper monthStockMapper;


    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(MonthStock entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(MonthStock entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(MonthStock entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<MonthStockVo> pages(MonthStockPageParam pageParam) {
        Page<MonthStock> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), MonthStock::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), MonthStock::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), MonthStock::getStockHouseId, pageParam.getStockHouseId())
                .eq(isNotEmpty(pageParam.getLspzdm()), MonthStock::getLspzdm, pageParam.getLspzdm())
                .eq(isNotEmpty(pageParam.getNd()), MonthStock::getNd, pageParam.getNd())
                .eq(isNotEmpty(pageParam.getYf()), MonthStock::getYf, pageParam.getYf())
                .eq(isNotEmpty(pageParam.getYjbz()), MonthStock::getYjbz, pageParam.getYjbz())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), MonthStock::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), MonthStock::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(MonthStock::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, MonthStockVo.class);
    }

    @DataBindFieldConvert
    @Override
    public MonthStockVo detail(String id) {
        IMonthStockService monthStockService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(monthStockService.getById(id), MonthStockVo.class);
    }

    @DataBindService(strategy = DataBindMonthStock.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, monthStockMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.MONTH_STOCK;
    }
}