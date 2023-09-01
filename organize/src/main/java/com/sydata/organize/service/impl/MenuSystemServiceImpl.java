package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.annotation.DataBindMenu;
import com.sydata.organize.annotation.DataBindMenuSystem;
import com.sydata.organize.domain.MenuSystem;
import com.sydata.organize.mapper.MenuSystemMapper;
import com.sydata.organize.param.MenuSystemPageParam;
import com.sydata.organize.param.MenuSystemRemoveParam;
import com.sydata.organize.param.MenuSystemSaveParam;
import com.sydata.organize.param.MenuSystemUpdateParam;
import com.sydata.organize.service.IMenuSystemService;
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

import static com.sydata.organize.service.impl.MenuSystemServiceImpl.CACHE_NAME;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @author lzq
 * @description 组织架构-菜单系统Service业务层处理
 * @date 2023/5/22 15:02
 */
@Service("menuSystemService")
@CacheConfig(cacheNames = CACHE_NAME)
public class MenuSystemServiceImpl extends ServiceImpl<MenuSystemMapper, MenuSystem> implements IMenuSystemService {

    final static String CACHE_NAME = "organize:menuSystem";

    @Resource
    private MenuSystemMapper menuSystemMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public MenuSystem getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<MenuSystem> listByIds(Collection<? extends Serializable> idList) {
        return CollectionUtils.isEmpty(idList) ? Collections.emptyList() : MultiCacheBatchHelp
                .apply(CACHE_NAME).<String, MenuSystem, MenuSystem>composeExecute()
                .fields(MenuSystem::getId)
                .params((Collection<String>) idList)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(MenuSystem::getId, Function.identity()))
                .get()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Page<MenuSystem> pages(MenuSystemPageParam pageParam) {
        return super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getName()), MenuSystem::getName, pageParam.getName())
                .likeRight(isNotEmpty(pageParam.getName()), MenuSystem::getShortName, pageParam.getShortName())
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
    }

    @Cacheable(key = "'name='+#name")
    @Override
    public MenuSystem getByName(String name) {
        return super.lambdaQuery().eq(MenuSystem::getName, name).one();
    }

    @Cacheable(key = "'shortName='+#shortName")
    @Override
    public MenuSystem getByShortName(String shortName) {
        return super.lambdaQuery().eq(MenuSystem::getShortName, shortName).one();
    }

    @Caching(evict = {
            @CacheEvict(key = "'name='+#saveParam.name"),
            @CacheEvict(key = "'shortName='+#saveParam.shortName"),
    })
    @Override
    public Boolean save(MenuSystemSaveParam saveParam) {
        MenuSystemServiceImpl bean = SpringUtil.getBean(this.getClass());
        Assert.isNull(bean.getByName(saveParam.getName()), "系统名称已存在");
        Assert.isNull(bean.getByShortName(saveParam.getShortName()), "系统简称已存在");

        MenuSystem menuSystem = BeanUtils.copyByClass(saveParam, MenuSystem.class);
        return super.save(menuSystem);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#updateParam.id"),
            @CacheEvict(key = "'name='+#updateParam.name"),
            @CacheEvict(key = "'shortName='+#updateParam.shortName"),
    })
    @Override
    public Boolean update(MenuSystemUpdateParam updateParam) {
        MenuSystemServiceImpl bean = SpringUtil.getBean(this.getClass());

        MenuSystem nameMenuSystem = bean.getByName(updateParam.getName());
        boolean nameState = nameMenuSystem == null || nameMenuSystem.getId().equals(updateParam.getId());
        Assert.state(nameState, "系统名称已存在");

        MenuSystem shortNameMenuSystem = bean.getByShortName(updateParam.getShortName());
        boolean shortState = shortNameMenuSystem == null || shortNameMenuSystem.getId().equals(updateParam.getId());
        Assert.state(shortState, "系统简称已存在");

        // 清除旧缓存
        clearCache(bean.getById(updateParam.getId()));

        MenuSystem menuSystem = BeanUtils.copyByClass(updateParam, MenuSystem.class);
        return super.updateById(menuSystem);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#removeParam.id"),
            @CacheEvict(key = "'name='+#removeParam.name"),
            @CacheEvict(key = "'shortName='+#removeParam.shortName"),
    })
    @Override
    public Boolean remove(MenuSystemRemoveParam removeParam) {
        return super.removeById(removeParam.getId());
    }

    @DataBindService(strategy = DataBindMenuSystem.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, menuSystemMapper);
    }

    /**
     * 清除缓存
     *
     * @param menuSystem 菜单系统
     */
    private void clearCache(MenuSystem menuSystem) {
        MultiCacheBatchHelp
                .apply(CACHE_NAME).<String, MenuSystem, MenuSystem>composeExecute()
                .fields(MenuSystem::getName)
                .params(Collections.singletonList(menuSystem.getName()))
                .remove();

        MultiCacheBatchHelp
                .apply(CACHE_NAME).<String, MenuSystem, MenuSystem>composeExecute()
                .fields(MenuSystem::getShortName)
                .params(Collections.singletonList(menuSystem.getShortName()))
                .remove();
    }
}
