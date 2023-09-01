package com.sydata.trade.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.basis.config.BasisConfig;
import com.sydata.collect.api.param.trade.StockGrainApiParam;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.trade.domain.StockGrain;
import com.sydata.trade.domain.StockGrainNewest;
import com.sydata.trade.mapper.StockGrainNewestMapper;
import com.sydata.trade.param.StockGrainDetailPageParam;
import com.sydata.trade.param.StockGrainPageParam;
import com.sydata.trade.service.IStockGrainNewestService;
import com.sydata.trade.vo.StockGrainDetailVo;
import com.sydata.trade.vo.StockGrainNewestVo;
import com.sydata.trade.vo.StockGrainStatisticsVo;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @author lzq
 * @description 粮油购销-粮食库存最新数据表Service接口实现类
 * @date 2023/4/21 10:28
 */
@CacheConfig(cacheNames = StockGrainNewestServiceImpl.CACHE_NAME)
@Service("stockGrainNewestService")
public class StockGrainNewestServiceImpl extends ServiceImpl<StockGrainNewestMapper, StockGrainNewest>
        implements IStockGrainNewestService {

    final static String CACHE_NAME = "trade:stockGrainNewest";

    @Resource
    private StockGrainNewestMapper stockGrainNewestMapper;

    @Resource
    private BasisConfig basisConfig;

    @Cacheable(key = "'id='+#id")
    @Override
    public StockGrainNewest getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(StockGrainNewest entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(StockGrainNewest entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#id")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @DataBindFieldConvert
    @Override
    public Page<StockGrainNewestVo> pages(StockGrainPageParam pageParam) {
        Page<StockGrainNewest> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), StockGrainNewest::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getLqgsdwdm()), StockGrainNewest::getLqgsdwdm, pageParam.getLqgsdwdm())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), StockGrainNewest::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), StockGrainNewest::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getLspzdm()), StockGrainNewest::getLspzdm, pageParam.getLspzdm())
                .eq(isNotEmpty(pageParam.getLsxzdm()), StockGrainNewest::getLsxzdm, pageParam.getLsxzdm())
                .ge(isNotEmpty(pageParam.getBeginrcsj()), StockGrainNewest::getRcsj, pageParam.getBeginrcsj())
                .le(isNotEmpty(pageParam.getEndrcsj()), StockGrainNewest::getRcsj, pageParam.getEndrcsj())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), StockGrainNewest::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), StockGrainNewest::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(StockGrainNewest::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, StockGrainNewestVo.class);
    }

    @DataBindFieldConvert
    @Override
    public StockGrainNewestVo detail(String id) {
        IStockGrainNewestService stockGrainNewestService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(stockGrainNewestService.getById(id), StockGrainNewestVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void stockSyn(StockGrainApiParam param, StockGrain stockGrain) {
        StockGrainNewestServiceImpl bean = SpringUtil.getBean(this.getClass());

        super.lambdaQuery()
                .select(StockGrainNewest::getId)
                .eq(StockGrainNewest::getHwdm, param.getHwdm())
                .eq(StockGrainNewest::getLspzdm, param.getLspzdm())
                .eq(StockGrainNewest::getRcsj, param.getRcsj())
                .oneOpt()
                .map(StockGrainNewest::getId)
                .map(bean::removeById)
                .isPresent();

        if (stockGrain != null) {
            bean.save(BeanUtils.copyByClass(stockGrain, StockGrainNewest.class).setLspzlb(param.getLspzlb()));
        }
    }

    @Override
    public List<StockGrainStatisticsVo> reserveStatistics(List<String> stockHouseIds) {
        Set<String> codes = basisConfig.getReservesFoodNatureCodes();
        return isEmpty(stockHouseIds) ? emptyList() : stockGrainNewestMapper.reserveStatistics(stockHouseIds, codes);
    }

    @DataBindFieldConvert
    @Override
    public Page<StockGrainDetailVo> pageDetail(StockGrainDetailPageParam pageParam) {
        Page<StockGrainNewest> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), StockGrainNewest::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getLqgsdwdm()), StockGrainNewest::getLqgsdwdm, pageParam.getLqgsdwdm())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), StockGrainNewest::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), StockGrainNewest::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getLspzdm()), StockGrainNewest::getLspzdm, pageParam.getLspzdm())
                .eq(isNotEmpty(pageParam.getLsxzdm()), StockGrainNewest::getLsxzdm, pageParam.getLsxzdm())

                .eq(isNotEmpty(pageParam.getCityId()), StockGrainNewest::getCityId, pageParam.getCityId())
                .eq(isNotEmpty(pageParam.getKddm()), StockGrainNewest::getStockHouseId, pageParam.getKddm())
                .eq(isNotEmpty(pageParam.getLsdjdm()), StockGrainNewest::getLsdjdm, pageParam.getLsdjdm())
                .eq(isNotEmpty(pageParam.getShnd()), StockGrainNewest::getShnd, pageParam.getShnd())
                .ge(isNotEmpty(pageParam.getBeginrcsj()), StockGrainNewest::getRcsj, pageParam.getBeginrcsj())
                .le(isNotEmpty(pageParam.getEndrcsj()), StockGrainNewest::getRcsj, pageParam.getEndrcsj())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), StockGrainNewest::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), StockGrainNewest::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(StockGrainNewest::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        Page<StockGrainDetailVo> stockGrainNewestVoPage = BeanUtils.copyToPage(page, StockGrainDetailVo.class);
        stockGrainNewestVoPage.getRecords().forEach(iter -> {
            iter.setCfdm(stockGrainNewestMapper.getCfdm(iter.getHwdm()));
        });
        return stockGrainNewestVoPage;
    }

    @Override
    public void stockDetailExport(StockGrainDetailPageParam pageParam) {
        List<StockGrainNewest> list = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), StockGrainNewest::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getLqgsdwdm()), StockGrainNewest::getLqgsdwdm, pageParam.getLqgsdwdm())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), StockGrainNewest::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), StockGrainNewest::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getLspzdm()), StockGrainNewest::getLspzdm, pageParam.getLspzdm())
                .eq(isNotEmpty(pageParam.getLsxzdm()), StockGrainNewest::getLsxzdm, pageParam.getLsxzdm())

                .eq(isNotEmpty(pageParam.getCityId()), StockGrainNewest::getCityId, pageParam.getCityId())
                .eq(isNotEmpty(pageParam.getKddm()), StockGrainNewest::getStockHouseId, pageParam.getKddm())
                .eq(isNotEmpty(pageParam.getLsdjdm()), StockGrainNewest::getLsdjdm, pageParam.getLsdjdm())
                .eq(isNotEmpty(pageParam.getShnd()), StockGrainNewest::getShnd, pageParam.getShnd())
                .ge(isNotEmpty(pageParam.getBeginrcsj()), StockGrainNewest::getRcsj, pageParam.getBeginrcsj())
                .le(isNotEmpty(pageParam.getEndrcsj()), StockGrainNewest::getRcsj, pageParam.getEndrcsj())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), StockGrainNewest::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), StockGrainNewest::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(StockGrainNewest::getUpdateTime)
                .list();
        List<StockGrainNewestVo> stockGrainNewestVos = BeanUtils.copyToList(list, StockGrainNewestVo.class);
        DataBindHandleBootstrap.dataHandConvert(stockGrainNewestVos, 2);
        ZtExcelBuildUtil.buildExcelExport(StockGrainNewestVo.class, "粮食库存明细报表").setData(stockGrainNewestVos).doWebExport();
    }
}
