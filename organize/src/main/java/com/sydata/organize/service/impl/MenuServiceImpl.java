package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.annotation.DataBindMenu;
import com.sydata.organize.config.DataPermissionConfig;
import com.sydata.organize.domain.Menu;
import com.sydata.organize.domain.UserRole;
import com.sydata.organize.mapper.MenuMapper;
import com.sydata.organize.param.MenuRemoveParam;
import com.sydata.organize.param.RoleMenuSetUpParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IMenuService;
import com.sydata.organize.service.IRoleMenuService;
import com.sydata.organize.service.IUserRoleService;
import com.sydata.organize.vo.MenuTreeVo;
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

/**
 * 组织架构-菜单Service业务层处理
 *
 * @author lzq
 * @date 2022-06-28
 */
@Service("menuService")
@CacheConfig(cacheNames = MenuServiceImpl.CACHE_NAME)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    final static String CACHE_NAME = "organize:menu";

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private DataPermissionConfig dataPermissionConfig;

    @Resource
    private IUserRoleService userRoleService;


    @Cacheable(key = "'id='+#id")
    @Override
    public Menu getById(Serializable id) {
        return super.getById(id);
    }


    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'parentId='+#entity.parentId"),
    })
    @Override
    public boolean save(Menu entity) {
        // 设置超管角色菜单
        super.save(entity);
        RoleMenuSetUpParam setUpParam = new RoleMenuSetUpParam()
                .setRoleIds(Collections.singleton(dataPermissionConfig.getAdminRoleId()))
                .setMenuIds(Collections.singleton(entity.getId()));
        return roleMenuService.setUp(setUpParam);
    }


    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'parentId='+#entity.parentId"),
    })
    @Override
    public boolean updateById(Menu entity) {
        // 清除旧父菜单ID缓存
        Menu menu = SpringUtil.getBean(this.getClass()).getById(entity.getId());
        if (!menu.getParentId().equals(entity.getParentId())) {
            MultiCacheBatchHelp.apply(CACHE_NAME).<Long, Menu, Menu>composeExecute()
                    .fields(Menu::getParentId)
                    .params(Collections.singleton(menu.getParentId()))
                    .remove();
        }
        return super.updateById(entity);
    }

    @DataBindFieldConvert
    @Override
    public List<MenuTreeVo> tree() {
        // 查询当前登录用户的角色列表 -- 过滤菜单列表
        List<UserRole> roles = userRoleService.listByUserId(UserSecurity.userId());
        List<Long> roleIds = StreamEx.of(roles).map(UserRole::getRoleId).toList();
        List<Menu> menus = roleMenuService.menusByRoleIds(roleIds);

        // 转换为树结构
        List<MenuTreeVo> vos = BeanUtils.copyToList(menus, MenuTreeVo.class);
        return TreeUtils.toTree(vos, MenuTreeVo::getId, MenuTreeVo::getParentId, MenuTreeVo::setChild, 0L);
    }

    @Cacheable(key = "'parentId='+#parentId")
    @Override
    public List<Menu> listByParentId(Long parentId) {
        return super.lambdaQuery().eq(Menu::getParentId, parentId).list();
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#removeParam.id"),
            @CacheEvict(key = "'parentId='+#removeParam.id"),
            @CacheEvict(key = "'parentId='+#removeParam.parentId"),
    })
    @Override
    public Boolean remove(MenuRemoveParam removeParam) {
        List<Menu> menus = SpringUtil.getBean(this.getClass()).listByParentId(removeParam.getId());
        Assert.state(CollectionUtils.isEmpty(menus), "存在下级菜单，不能删除");

        boolean state = super.removeById(removeParam.getId());
        if (state) {
            roleMenuService.removeByMenuId(removeParam.getId());
        }
        return state;
    }

    @DataBindService(strategy = DataBindMenu.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, menuMapper);
    }
}