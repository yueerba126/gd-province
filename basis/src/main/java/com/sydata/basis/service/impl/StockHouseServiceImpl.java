package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.dto.StockHouseQualityCheckDto;
import com.sydata.basis.mapper.StockHouseMapper;
import com.sydata.basis.param.StockHousePageParam;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.basis.vo.StockHouseVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.StockHouseApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.permission.rewrite.StockHouseSqlRewrite;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.report.api.param.basis.StockHouseReportParam;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sydata.collect.api.enums.DataApiEnum.STOCK_HOUSE;
import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 基础信息-库区信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = StockHouseServiceImpl.CACHE_NAME)
@Service("stockHouseService")
public class StockHouseServiceImpl
        extends BaseDataImpl<StockHouseApiParam, StockHouseMapper, StockHouse, StockHouseReportParam>
        implements IStockHouseService {

    final static String CACHE_NAME = "basis:stockHouse";

    @Resource
    private StockHouseMapper stockHouseMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public StockHouse getById(Serializable id) {
        return super.getById(id);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'regionId='+#entity.xzqhdm")
    })
    @Override
    public boolean save(StockHouse entity) {
        return super.save(entity);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'regionId='+#entity.xzqhdm")
    })
    @Override
    public boolean updateById(StockHouse entity) {
        return super.updateById(entity);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'regionId='+#entity.xzqhdm")
    })
    @Override
    public boolean removeById(StockHouse entity) {
        return super.removeById(entity);
    }


    @Override
    public List<StockHouse> listByIds(Collection<? extends Serializable> idList) {
        return MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, StockHouse, StockHouse>composeExecute()
                .fields(StockHouse::getId)
                .params((Collection<String>) idList)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(StockHouse::getId, Function.identity()))
                .get()
                .stream()
                .collect(Collectors.toList());
    }

    @DataBindFieldConvert
    @Override
    public Page<StockHouseVo> pages(StockHousePageParam pageParam) {
        Page<StockHouse> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), StockHouse::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), StockHouse::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), StockHouse::getStockHouseId, pageParam.getStockHouseId())
                .likeRight(isNotEmpty(pageParam.getKqmc()), StockHouse::getKqmc, pageParam.getKqmc())
                .eq(isNotEmpty(pageParam.getCountryId()), StockHouse::getCountryId, pageParam.getCountryId())
                .eq(isNotEmpty(pageParam.getProvinceId()), StockHouse::getProvinceId, pageParam.getProvinceId())
                .eq(isNotEmpty(pageParam.getCityId()), StockHouse::getCityId, pageParam.getCityId())
                .eq(isNotEmpty(pageParam.getAreaId()), StockHouse::getAreaId, pageParam.getAreaId())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), StockHouse::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), StockHouse::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(StockHouse::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, StockHouseVo.class);
    }

    @DataBindFieldConvert
    @Override
    public Page<StockHouseVo> dataViewPage(StockHousePageParam pageParam) {
        try {
            StockHouseSqlRewrite.refuseSelect();
            return this.pages(pageParam);
        } finally {
            StockHouseSqlRewrite.clear();
        }
    }

    @DataBindFieldConvert
    @Override
    public StockHouseVo detail(String id) {
        IStockHouseService stockHouseService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(stockHouseService.getById(id), StockHouseVo.class);
    }

    @Cacheable(key = "'regionId='+#regionId")
    @Override
    public List<StockHouse> listByRegionId(String regionId) {
        return super.lambdaQuery()
                .select(StockHouse::getId, StockHouse::getKqdm, StockHouse::getKqmc,
                        StockHouse::getJd, StockHouse::getWd, StockHouse::getHkRegionId)
                .eq(StockHouse::getRegionId, regionId)
                .list();
    }

    @DataBindService(strategy = DataBindStockHouse.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, stockHouseMapper);
    }

    @Override
    public DataApiEnum api() {
        return STOCK_HOUSE;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, StockHouseApiParam param) {
        Assert.state(param.getYgs() > 0 || param.getCfs() > 0, "仓房和油罐数不能同时为0");
        if (param.getYgs() > 0) {
            dataIssueDto.append(param.getYxgr().compareTo(ZERO) > 0,
                    StockHouseApiParam::getYxgr, "油罐数不为0时，有效罐容不能为0");
        }
        if (param.getCfs() > 0) {
            dataIssueDto.append(param.getYxcr().compareTo(ZERO) > 0,
                    StockHouseApiParam::getYxcr, "仓房数不为0时，有效仓容不能为0");
        }

        // 校验库区行政区划代码和应用行政区划代码必须一致
        LoginUser loginUser = UserSecurity.loginUser();
        if (loginUser != null) {
            String regionId = loginUser.getRegionId();
            dataIssueDto.append(param.getXzqhdm().equals(regionId),
                    StockHouseApiParam::getXzqhdm, "库区行政区划代码和应用行政区划代码不一致");
        }


        // 校验油罐数不为0有效罐容也不能为0,油罐数为0有效罐容应为0
        if (param.getYgs() != 0) {
            dataIssueDto.append(param.getYxgr().compareTo(ZERO) > 0,
                    StockHouseApiParam::getYxgr, "油罐数不为0有效罐容也不能为0");
        } else {
            dataIssueDto.append(param.getYxgr().compareTo(ZERO) == 0,
                    StockHouseApiParam::getYxgr, "油罐数为0有效罐容应为0");
        }

        // 校验仓房数不为0有效仓容也不能为0,仓房数为0有效仓容应为0
        if (param.getCfs() != 0) {
            dataIssueDto.append(param.getYxcr().compareTo(ZERO) > 0,
                    StockHouseApiParam::getYxcr, "仓房数不为0有效仓容也不能为0");
        } else {
            dataIssueDto.append(param.getYxcr().compareTo(ZERO) == 0,
                    StockHouseApiParam::getYxcr, "仓房数为0有效仓容应为0");
        }
    }

    @Override
    public void dataQualityCustomCheck(DataQualityProcessContext context) {
        // 数据质量校验：是否上传了仓房/油罐信息、所属仓房数与上传仓房数是否一致、所属油罐数与上传油罐数是否一致、是否已上传库区平面图
        List<StockHouseQualityCheckDto> checks = stockHouseMapper.qualityCheck(context.getDataIds());
        checks.forEach(check -> {
            DataIssueDto dataIssue = context.issueRecord(check.getId());

            boolean state = check.getSccfs() + check.getYgs() > 0;
            dataIssue.append(state, StockHouseApiParam::getCfs, "该库区未上传仓库或油罐信息");

            dataIssue.append(check.getCfs() == check.getSccfs(),
                    StockHouseApiParam::getCfs, "所属仓房数与上传仓房数不一致");

            dataIssue.append(check.getYgs() == check.getScygs(),
                    StockHouseApiParam::getYgs, "所属油罐数与上传油罐数不一致");

            dataIssue.append(isNotEmpty(check.getFileId()), StockHouseApiParam::getKqmc, "未上传库区平面图");
        });
    }
}