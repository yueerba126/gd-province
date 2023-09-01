package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.domain.AppApiStockHouse;
import com.sydata.organize.domain.Region;
import com.sydata.organize.mapper.AppApiStockHouseMapper;
import com.sydata.organize.param.AppApiStockHousePageParam;
import com.sydata.organize.param.AppApiStockHouseRemoveParam;
import com.sydata.organize.service.IAppApiStockHouseService;
import com.sydata.organize.service.IRegionService;
import com.sydata.organize.vo.AppApiStockHouseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @author lzq
 * @description 组织架构-app对接应用关联库区Service业务层处理
 * @date 2023/5/30 11:35
 */
@Service("appApiStockHouseService")
@CacheConfig(cacheNames = AppApiStockHouseServiceImpl.CACHE_NAME)
@Slf4j
public class AppApiStockHouseServiceImpl extends ServiceImpl<AppApiStockHouseMapper, AppApiStockHouse>
        implements IAppApiStockHouseService {

    final static String CACHE_NAME = "organize:appApiStockHouse";

    @Resource
    private IRegionService regionService;

    @Cacheable(key = "'id='+#id")
    @Override
    public AppApiStockHouse getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'appid='+#entity.appid+'&stockHouseId='+#entity.stockHouseId")
    @Override
    public boolean save(AppApiStockHouse entity) {
        // 校验库区代码必须以单位代码开始
        Assert.state(entity.getStockHouseId().startsWith(entity.getEnterpriseId()), "库区代码必须以单位代码开始");

        // 校验应用库区不可重复设置
        String appid = entity.getAppid();
        String stockHouseId = entity.getStockHouseId();
        AppApiStockHouse appStockHouse = SpringUtil.getBean(this.getClass()).getByAppStockHouse(appid, stockHouseId);
        Assert.isNull(appStockHouse, String.format("该应用已授权%s库区,不可重复设置", stockHouseId));

        // 设置国省市区
        Region region = regionService.getById(entity.getRegionId());
        entity.setCountryId(region.getCountryId()).setProvinceId(region.getProvinceId())
                .setCityId(region.getCityId())
                .setAreaId(region.getAreaId());
        return super.save(entity);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'appid='+#entity.appid+'&stockHouseId='+#entity.stockHouseId")
    })
    @Override
    public boolean updateById(AppApiStockHouse entity) {
        // 校验库区代码必须以单位代码开始
        Assert.state(entity.getStockHouseId().startsWith(entity.getEnterpriseId()), "库区代码必须以单位代码开始");

        // 校验应用库区不可重复设置
        String appid = entity.getAppid();
        String stockHouseId = entity.getStockHouseId();
        AppApiStockHouse appStockHouse = SpringUtil.getBean(this.getClass()).getByAppStockHouse(appid, stockHouseId);
        boolean state = appStockHouse == null || entity.getId().equals(appStockHouse.getId());
        Assert.state(state, String.format("该应用已授权%s库区,不可重复设置", stockHouseId));

        // 设置国省市区
        Region region = regionService.getById(entity.getRegionId());
        entity.setCountryId(region.getCountryId()).setProvinceId(region.getProvinceId())
                .setCityId(region.getCityId())
                .setAreaId(region.getAreaId());
        return super.updateById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<AppApiStockHouseVo> pages(AppApiStockHousePageParam pageParam) {
        Page<AppApiStockHouse> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getAppid()), AppApiStockHouse::getAppid, pageParam.getAppid())
                .likeRight(isNotEmpty(pageParam.getEnterpriseId()), AppApiStockHouse::getEnterpriseId, pageParam.getEnterpriseId())
                .likeRight(isNotEmpty(pageParam.getStockHouseId()), AppApiStockHouse::getStockHouseId, pageParam.getStockHouseId())
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, AppApiStockHouseVo.class);
    }

    @DataBindFieldConvert
    @Override
    public AppApiStockHouseVo detail(String id) {
        IAppApiStockHouseService appApiStockHouseService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(appApiStockHouseService.getById(id), AppApiStockHouseVo.class);
    }


    @Caching(evict = {
            @CacheEvict(key = "'id='+#removeParam.id"),
            @CacheEvict(key = "'appid='+#removeParam.appid+'&stockHouseId='+#removeParam.stockHouseId")
    })
    @Override
    public Boolean remove(AppApiStockHouseRemoveParam removeParam) {
        return super.removeById(removeParam.getId());
    }

    @Cacheable(key = "'appid='+#appid+'&stockHouseId='+#stockHouseId")
    @Override
    public AppApiStockHouse getByAppStockHouse(String appid, String stockHouseId) {
        return super.lambdaQuery()
                .eq(AppApiStockHouse::getAppid, appid)
                .eq(AppApiStockHouse::getStockHouseId, stockHouseId)
                .one();
    }
}
