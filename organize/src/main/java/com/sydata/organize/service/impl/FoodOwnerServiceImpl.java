package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.framework.job.dto.LimitDto;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.domain.FoodOwner;
import com.sydata.organize.domain.Region;
import com.sydata.organize.mapper.FoodOwnerMapper;
import com.sydata.organize.param.FoodOwnerPageParam;
import com.sydata.organize.service.IFoodOwnerService;
import com.sydata.organize.service.IRegionService;
import com.sydata.organize.vo.FoodOwnerVo;
import com.xxl.job.core.handler.annotation.XxlJob;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 组织架构-粮权关系维护Service业务层处理
 *
 * @author lzq
 * @date 2022-11-14
 */
@CacheConfig(cacheNames = FoodOwnerServiceImpl.CACHE_NAME)
@Service("foodOwnerService")
public class FoodOwnerServiceImpl extends ServiceImpl<FoodOwnerMapper, FoodOwner> implements IFoodOwnerService {

    final static String CACHE_NAME = "organize:foodOwner";

    private static final String SAVE_BUFFER = CACHE_NAME + ":saveBuffer";

    @Resource
    private FoodOwnerMapper foodOwnerMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IRegionService regionService;

    @Cacheable(key = "'id='+#id")
    @Override
    public FoodOwner getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<FoodOwner> listByIds(Collection<? extends Serializable> idList) {
        return MultiCacheBatchHelp
                .apply(CACHE_NAME).<String, FoodOwner, FoodOwner>composeExecute()
                .fields(FoodOwner::getId)
                .params((Collection<String>) idList)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(FoodOwner::getId, Function.identity()))
                .get()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        // 清理缓存
        MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, FoodOwner, FoodOwner>composeExecute()
                .fields(FoodOwner::getId)
                .params((Collection<String>) list)
                .remove();
        return super.removeByIds(list);
    }

    @Override
    public boolean saveBatch(Collection<FoodOwner> entityList) {
        // 清理缓存粮权单位缓存
        List<String> companyIds = StreamEx.of(entityList).map(FoodOwner::getCompanyId).distinct().toList();
        if (CollectionUtils.isNotEmpty(companyIds)) {
            MultiCacheBatchHelp.apply(CACHE_NAME)
                    .<String, FoodOwner, FoodOwner>composeExecute()
                    .fields(FoodOwner::getCompanyId)
                    .params(companyIds)
                    .remove();
        }

        // 清理缓存行政区域缓存
        List<String> regionIds = StreamEx.of(entityList).map(FoodOwner::getRegionId).distinct().toList();
        if (CollectionUtils.isNotEmpty(regionIds)) {
            MultiCacheBatchHelp.apply(CACHE_NAME)
                    .<String, FoodOwner, FoodOwner>composeExecute()
                    .fields(FoodOwner::getRegionId)
                    .params(regionIds)
                    .remove();
        }

        return super.saveBatch(entityList);
    }

    @DataBindFieldConvert
    @Override
    public Page<FoodOwnerVo> pages(FoodOwnerPageParam pageParam) {
        Page<FoodOwner> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getRegionId()), FoodOwner::getRegionId, pageParam.getRegionId())
                .eq(isNotEmpty(pageParam.getCountryId()), FoodOwner::getCountryId, pageParam.getCountryId())
                .eq(isNotEmpty(pageParam.getProvinceId()), FoodOwner::getProvinceId, pageParam.getProvinceId())
                .eq(isNotEmpty(pageParam.getCityId()), FoodOwner::getCityId, pageParam.getCityId())
                .eq(isNotEmpty(pageParam.getAreaId()), FoodOwner::getAreaId, pageParam.getAreaId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), FoodOwner::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), FoodOwner::getStockHouseId, pageParam.getStockHouseId())
                .orderByAsc(FoodOwner::getId)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, FoodOwnerVo.class);
    }

    @Cacheable(key = "'companyId='+#companyId")
    @Override
    public Set<String> stockHouseIdsByCompanyId(String companyId) {
        return super.lambdaQuery()
                .select(FoodOwner::getStockHouseId)
                .eq(FoodOwner::getCompanyId, companyId)
                .groupBy(FoodOwner::getStockHouseId)
                .list()
                .stream()
                .map(FoodOwner::getStockHouseId)
                .collect(Collectors.toSet());
    }

    @Cacheable(key = "'regionId='+#regionId")
    @Override
    public Set<String> stockHouseIdsByRegionId(String regionId) {
        return super.lambdaQuery()
                .select(FoodOwner::getStockHouseId)
                .eq(FoodOwner::getRegionId, regionId)
                .groupBy(FoodOwner::getStockHouseId)
                .list()
                .stream()
                .map(FoodOwner::getStockHouseId)
                .collect(Collectors.toSet());
    }

    @Override
    public List<FoodOwner> regionStockHousesByRegionId(String regionId) {
        Region region = regionService.getById(regionId);
        return super.lambdaQuery()
                .select(FoodOwner::getProvinceId, FoodOwner::getCityId, FoodOwner::getAreaId,
                        FoodOwner::getRegionId, FoodOwner::getStockHouseId)
                .eq(isNotEmpty(region.getCountryId()), FoodOwner::getCountryId, region.getCountryId())
                .eq(isNotEmpty(region.getProvinceId()), FoodOwner::getProvinceId, region.getProvinceId())
                .eq(isNotEmpty(region.getCityId()), FoodOwner::getCityId, region.getCityId())
                .eq(isNotEmpty(region.getAreaId()), FoodOwner::getAreaId, region.getAreaId())
                .groupBy(FoodOwner::getRegionId, FoodOwner::getStockHouseId)
                .list();
    }

    @Override
    public void saveByBuffer(String regionId, String companyId, String enterpriseId, String stockHouseId) {
        String saveBuffer = SpringUtil.getApplicationName() + COLON + SAVE_BUFFER;
        FoodOwner foodOwner = new FoodOwner()
                .setRegionId(regionId)
                .setCompanyId(companyId)
                .setEnterpriseId(enterpriseId)
                .setStockHouseId(stockHouseId);
        redisTemplate.opsForList().leftPush(saveBuffer, foodOwner);
    }

    @XxlJob("handleSaveFoodOwnerBuffer")
    public void handleSaveFoodOwnerBuffer() {
        // 总数分片分页处理
        String saveBuffer = SpringUtil.getApplicationName() + COLON + SAVE_BUFFER;
        ListOperations listOperations = redisTemplate.opsForList();
        List<LimitDto> limits = FrameworkJobHelper.shardLimitTotalCount(() -> listOperations.size(saveBuffer));
        for (LimitDto limit : limits) {
            // 获取不到分区数据退出本次处理工作
            List<FoodOwner> list = listOperations.rightPop(saveBuffer, limit.getSize());
            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            // 过滤数据-相同主键取其一
            Map<String, FoodOwner> idMap = StreamEx.of(list)
                    .groupingBy(this::buildId, collectingAndThen(toList(), t -> t.get(0)));

            // 过滤数据-已存在数据库中的主键则丢弃
            super.lambdaQuery()
                    .select(FoodOwner::getId)
                    .in(FoodOwner::getId, idMap.keySet())
                    .list()
                    .stream()
                    .map(FoodOwner::getId)
                    .map(idMap::remove)
                    .collect(Collectors.toList());
            if (MapUtils.isEmpty(idMap)) {
                continue;
            }

            // 设置主键ID，设置实体冗余信息后批量插入数据库《先删后加》 -- 批量插入时静默处理,异常也没关系
            Collection<FoodOwner> values = idMap.values();
            List<String> regionIds = values.stream().map(FoodOwner::getRegionId).distinct().collect(toList());
            List<Region> regions = regionService.listByIds(regionIds);
            Map<String, Region> regionIdMap = StreamEx.of(regions).toMap(Region::getId, Function.identity());
            idMap.forEach((id, t) -> {
                Region region = regionIdMap.get(t.getRegionId());
                t.setId(id).setCountryId(region.getCountryId()).setProvinceId(region.getProvinceId())
                        .setCityId(region.getCityId()).setAreaId(region.getAreaId());
            });

            try {
                this.saveBatch(values);
            } catch (Throwable e) {
                FrameworkJobHelper.log("粮权关系维护插入数据库发生异常", e);
            }
        }
    }


    /**
     * 构建ID
     *
     * @param foodOwner 粮权关系维护
     * @return 主键ID
     */
    private String buildId(FoodOwner foodOwner) {
        return new StringJoiner(DASH)
                .add(foodOwner.getEnterpriseId())
                .add(foodOwner.getStockHouseId())
                .add(foodOwner.getCompanyId())
                .add(foodOwner.getRegionId())
                .toString();
    }
}