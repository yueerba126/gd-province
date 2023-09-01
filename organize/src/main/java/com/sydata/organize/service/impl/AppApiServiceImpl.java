package com.sydata.organize.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.AesUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.domain.AppApi;
import com.sydata.organize.domain.Region;
import com.sydata.organize.mapper.AppApiMapper;
import com.sydata.organize.param.AppApiPageParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IAppApiService;
import com.sydata.organize.service.IRegionService;
import com.sydata.organize.vo.AppApiVo;
import com.sydata.organize.vo.AppSecretGenerateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


/**
 * 组织架构-app对接应用Service业务层处理
 *
 * @author lzq
 * @date 2022-10-21
 */
@Service("appApiService")
@CacheConfig(cacheNames = AppApiServiceImpl.CACHE_NAME)
@Slf4j
public class AppApiServiceImpl extends ServiceImpl<AppApiMapper, AppApi> implements IAppApiService {

    final static String CACHE_NAME = "organize:appApi";

    @Resource
    private AppApiMapper appApiMapper;

    @Resource
    private IRegionService regionService;

    @Cacheable(key = "'appid='+#appid")
    @Override
    public AppApi getById(Serializable appid) {
        return super.getById(appid);
    }

    @CacheEvict(key = "'appid='+#entity.appid")
    @Override
    public boolean save(AppApi entity) {
        appApiCheck(entity);

        return super.save(entity);
    }

    @CacheEvict(key = "'appid='+#entity.appid")
    @Override
    public boolean updateById(AppApi entity) {
        appApiCheck(entity);

        return super.updateById(entity);
    }

    @CacheEvict(key = "'appid='+#appid")
    @Override
    public boolean removeById(Serializable appid) {
        return super.removeById(appid);
    }

    @DataBindFieldConvert
    @Override
    public Page<AppApiVo> pages(AppApiPageParam pageParam) {
        Page<AppApi> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getOrganizeId()), AppApi::getOrganizeId, pageParam.getOrganizeId())
                .likeRight(isNotEmpty(pageParam.getAppName()), AppApi::getAppName, pageParam.getAppName())
                .ge(nonNull(pageParam.getBeginUpdateTime()), AppApi::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(nonNull(pageParam.getEndUpdateTime()), AppApi::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(AppApi::getCreateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, AppApiVo.class);
    }

    @DataBindFieldConvert
    @Override
    public AppApiVo detail(String appid) {
        IAppApiService appApiService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(appApiService.getById(appid), AppApiVo.class);
    }

    @Override
    public AppSecretGenerateVo generate() {
        String appid = IdUtil.fastSimpleUUID().substring(0, 16);
        String appKey = IdUtil.fastSimpleUUID();
        String appSecret = AesUtil.generateSecret();
        return new AppSecretGenerateVo(appid, appKey, UserSecurity.encryptByPrivate(appSecret));
    }

    @CacheEvict(key = "'appid='+#appid")
    @Override
    public Boolean updateEod(Boolean state, String appid) {
        return super.lambdaUpdate().set(AppApi::getState, state).eq(AppApi::getAppid, appid).update();
    }

    /**
     * 应用校验
     *
     * @param entity app对接应用
     */
    private void appApiCheck(AppApi entity) {
        boolean state = entity.getStockHouseId() == null || entity.getStockHouseId().startsWith(entity.getOrganizeId());
        Assert.state(state, "库区代码必须以单位代码开始");

        Region region = regionService.getById(entity.getRegionId());
        entity.setCountryId(region.getCountryId()).setProvinceId(region.getProvinceId())
                .setCityId(region.getCityId())
                .setAreaId(region.getAreaId());

        // 使用公钥解密校验是否为私钥加密
        UserSecurity.decryptByPublic(entity.getAppSecret());
    }
}