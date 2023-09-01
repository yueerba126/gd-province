package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.organize.domain.Region;
import com.sydata.organize.mapper.RegionMapper;
import com.sydata.organize.param.RegionPageParam;
import com.sydata.organize.param.RegionRemoveParam;
import com.sydata.organize.service.IRegionService;
import com.sydata.organize.vo.RegionTreeVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sydata.organize.enums.RegionTypeEnum.PROVINCE;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


/**
 * 组织架构-行政区域Service业务层处理
 *
 * @author lzq
 * @date 2022-06-28
 */
@Service("regionService")
@CacheConfig(cacheNames = RegionServiceImpl.CACHE_NAME)
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    final static String CACHE_NAME = "organize:region";

    @Resource
    private RegionMapper regionMapper;

    /**
     * 国id
     */
    final static String CACHE_COUNTRY = "000156";

    /**
     * 省id
     */
    final static String CACHE_PROVINCE = "440000";

    @Cacheable(key = "'list'")
    @Override
    public List<Region> list() {
        return super.list();
    }

    @Cacheable(key = "'id='+#id")
    @Override
    public Region getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<Region> listByIds(Collection<? extends Serializable> idList) {
        return MultiCacheBatchHelp
                .apply(CACHE_NAME).<String, Region, Region>composeExecute()
                .fields(Region::getId)
                .params((Collection<String>) idList)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(Region::getId, Function.identity()))
                .get()
                .stream()
                .collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'parentId='+#entity.parentId"),
            @CacheEvict(key = "'name='+#entity.name"),
            @CacheEvict(key = "'list'"),
    })
    @Override
    public boolean save(Region entity) {
        RegionServiceImpl regionService = SpringUtil.getBean(this.getClass());
        Assert.isNull(regionService.getById(entity.getId()), String.format("行政区域代码%s已存在", entity.getId()));

        return super.save(entity);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'parentId='+#entity.parentId"),
            @CacheEvict(key = "'name='+#entity.name"),
            @CacheEvict(key = "'list'"),
    })
    @Override
    public boolean updateById(Region entity) {
        // 清除旧父区域ID缓存
        RegionServiceImpl regionService = SpringUtil.getBean(this.getClass());
        Region region = regionService.getById(entity.getId());
        if (!region.getParentId().equals(entity.getParentId())) {
            MultiCacheBatchHelp.apply(CACHE_NAME).<String, Region, Region>composeExecute()
                    .fields(Region::getParentId)
                    .params(Collections.singleton(region.getParentId()))
                    .remove();
        }

        return super.updateById(entity);
    }

    @Override
    public Page<Region> pages(RegionPageParam pageParam) {
        return super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getParentId()), Region::getParentId, pageParam.getParentId())
                .eq(isNotEmpty(pageParam.getProvinceId()), Region::getProvinceId, pageParam.getProvinceId())
                .eq(isNotEmpty(pageParam.getCityId()), Region::getCityId, pageParam.getCityId())
                .eq(isNotEmpty(pageParam.getAreaId()), Region::getAreaId, pageParam.getAreaId())
                .eq(isNotEmpty(pageParam.getType()), Region::getType, pageParam.getType())
                .likeRight(isNotEmpty(pageParam.getName()), Region::getName, pageParam.getName())
                .likeRight(isNotEmpty(pageParam.getId()), Region::getId, pageParam.getId())
                .orderByAsc(Region::getRegionSort)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
    }

    @Override
    public List<RegionTreeVo> tree() {
        List<Region> list = SpringUtil.getBean(this.getClass()).lambdaQuery().orderByAsc(Region::getRegionSort).list();
        List<RegionTreeVo> vos = BeanUtils.copyToList(list, RegionTreeVo.class);
        return TreeUtils.toTree(vos, RegionTreeVo::getId, RegionTreeVo::getParentId, RegionTreeVo::setChild, "0");
    }

    @Cacheable(key = "'parentId='+#parentId")
    @Override
    public List<Region> listByParentId(String parentId) {
        return super.lambdaQuery().eq(Region::getParentId, parentId).orderByAsc(Region::getRegionSort).list();
    }

    @Cacheable(key = "'name='+#name")
    @Override
    public List<Region> listByName(String name) {
        return super.lambdaQuery().eq(Region::getName, name).list();
    }


    @Caching(evict = {
            @CacheEvict(key = "'id='+#removeParam.id"),
            @CacheEvict(key = "'parentId='+#removeParam.id"),
            @CacheEvict(key = "'parentId='+#removeParam.parentId"),
            @CacheEvict(key = "'name='+#removeParam.name"),
            @CacheEvict(key = "'list'"),
    })
    @Override
    public Boolean remove(RegionRemoveParam removeParam) {
        List<Region> regions = SpringUtil.getBean(this.getClass()).listByParentId(removeParam.getId());
        Assert.state(CollectionUtils.isEmpty(regions), "存在子节点不允许删除");
        return super.removeById(removeParam.getId());
    }

    @DataBindService(strategy = DataBindRegion.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, regionMapper);
    }

    @Override
    public List<Region> tabulation(String id) {
        Region region = SpringUtil.getBean(this.getClass()).getById(id);
        return super.lambdaQuery()
                .eq(isNotEmpty(region.getCountryId()), Region::getCountryId, region.getCountryId())
                .eq(isNotEmpty(region.getProvinceId()), Region::getProvinceId, region.getProvinceId())
                .eq(isNotEmpty(region.getCityId()), Region::getCityId, region.getCityId())
                .eq(isNotEmpty(region.getAreaId()), Region::getAreaId, region.getAreaId())
                .orderByAsc(Region::getRegionSort)
                .list();
    }

    @Override
    public List<Region> sonsAll(String parentId) {
        if (CACHE_COUNTRY.equals(parentId)) {
            parentId = CACHE_PROVINCE;
        }
        String finalParentId = parentId;
        List<Region> list = super.lambdaQuery()
                .and(wrapper -> wrapper.eq(Region::getParentId, finalParentId).or().eq(Region::getId, finalParentId))
                .orderByAsc(Region::getRegionSort)
                .list();

        StreamEx.of(list).groupingBy(Region::getType)
                .getOrDefault(PROVINCE.getType(), emptyList())
                .forEach(province -> province.setName("省级"));
        return list;
    }
}