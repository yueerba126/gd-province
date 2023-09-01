package com.sydata.organize.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.domain.Menu;
import com.sydata.organize.domain.RoleMenu;
import com.sydata.organize.domain.UserRole;
import com.sydata.organize.mapper.RoleMenuMapper;
import com.sydata.organize.param.RoleMenuSetUpParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IRoleMenuService;
import com.sydata.organize.service.IUserRoleService;
import com.sydata.organize.vo.RoleMenuAuthorizeTreeVo;
import com.sydata.organize.vo.RoleMenuMenuVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

/**
 * 组织架构-角色菜单Service业务层处理
 *
 * @author lzq
 * @date 2022-06-28
 */
@Service("roleMenuService")
@CacheConfig(cacheNames = RoleMenuServiceImpl.CACHE_NAME)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    final static String CACHE_NAME = "organize:roleMenu";


    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IUserRoleService userRoleService;

    @Override
    public boolean setUp(RoleMenuSetUpParam setUpParam) {
        // 组装数据-批量新增
        List<RoleMenu> roleMenus = new ArrayList<>();
        setUpParam.getRoleIds().forEach(roleId -> {
            Set<Long> menuIds = setUpParam.getMenuIds();
            menuIds.forEach(menuId -> {
                long id = IdUtil.getSnowflakeNextId();
                RoleMenu roleMenu = new RoleMenu().setId(id).setMenuId(menuId).setRoleId(roleId);
                roleMenus.add(roleMenu);
            });
        });
        boolean state = roleMenuMapper.insertBatchIgnore(roleMenus);

        // 清理缓存
        MultiCacheBatchHelp.apply(CACHE_NAME)
                .<Long, RoleMenu, RoleMenu>composeExecute()
                .fields(RoleMenu::getRoleId)
                .params(setUpParam.getRoleIds())
                .remove();
        return state;
    }

    @Override
    public boolean cancel(RoleMenuSetUpParam setUpParam) {
        boolean state = super.lambdaUpdate()
                .in(RoleMenu::getRoleId, setUpParam.getRoleIds())
                .in(RoleMenu::getMenuId, setUpParam.getMenuIds())
                .remove();

        // 清理缓存
        MultiCacheBatchHelp.apply(CACHE_NAME)
                .<Long, RoleMenu, RoleMenu>composeExecute()
                .fields(RoleMenu::getRoleId)
                .params(setUpParam.getRoleIds())
                .remove();
        return state;
    }

    @Override
    public boolean removeByMenuId(Long menuId) {
        Set<Long> roleIds = super.lambdaQuery().select(RoleMenu::getRoleId)
                .eq(RoleMenu::getMenuId, menuId)
                .list()
                .stream()
                .map(RoleMenu::getRoleId)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(roleIds)) {
            return Boolean.FALSE;
        }

        // 取消角色菜单
        RoleMenuSetUpParam setUpParam = new RoleMenuSetUpParam()
                .setRoleIds(roleIds)
                .setMenuIds(Collections.singleton(menuId));
        return cancel(setUpParam);
    }

    @Override
    public boolean removeByRoleId(Long roleId) {
        Set<Long> menuIds = super.lambdaQuery().select(RoleMenu::getMenuId)
                .eq(RoleMenu::getRoleId, roleId)
                .list()
                .stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(menuIds)) {
            return Boolean.FALSE;
        }

        // 取消角色菜单
        RoleMenuSetUpParam setUpParam = new RoleMenuSetUpParam()
                .setRoleIds(Collections.singleton(roleId))
                .setMenuIds(menuIds);
        return cancel(setUpParam);
    }

    @Cacheable(key = "'roleId='+#roleId")
    @Override
    public List<RoleMenu> listByRoleId(Long roleId) {
        return super.lambdaQuery().eq(RoleMenu::getRoleId, roleId).list();
    }

    @Override
    public List<Menu> menusByRoleIds(List<Long> roleIds) {
        RoleMenuServiceImpl roleMenuService = SpringUtil.getBean(this.getClass());

        // 获取这些角色的菜单ID（去重）
        List<RoleMenuMenuVo> menuVos = StreamEx.of(roleIds)
                .map(roleMenuService::listByRoleId)
                .flatMap(Collection::stream)
                .map(RoleMenu::getMenuId)
                .distinct()
                .map(menuId -> new RoleMenuMenuVo().setMenuId(menuId))
                .toList();
        DataBindHandleBootstrap.dataHandConvert(menuVos);

        return StreamEx.of(menuVos).map(RoleMenuMenuVo::getMenu).nonNull().toList();
    }

    @Override
    public List<RoleMenuAuthorizeTreeVo> roleMenuAuthorizeTree(Long roleId) {
        // 获取当前登录人的菜单列表，并且转换为树列表
        RoleMenuServiceImpl roleMenuService = SpringUtil.getBean(this.getClass());
        List<UserRole> roles = userRoleService.listByUserId(UserSecurity.userId());
        List<Menu> menus = StreamEx.of(roles).map(UserRole::getRoleId).toListAndThen(roleMenuService::menusByRoleIds);

        List<RoleMenuAuthorizeTreeVo> trees = BeanUtils.copyToList(menus, RoleMenuAuthorizeTreeVo.class);
        Map<Long, RoleMenuAuthorizeTreeVo> treeMap = StreamEx.of(trees).toMap(RoleMenuAuthorizeTreeVo::getId, t -> t);

        // 获取需要被授权角色的菜单列表，比较是否拥有该权限
        List<RoleMenu> roleMenus = roleMenuService.listByRoleId(roleId);
        roleMenus.forEach(roleMenu -> {
            RoleMenuAuthorizeTreeVo menu = treeMap.get(roleMenu.getMenuId());
            if (menu != null) {
                menu.setChecked(TRUE);
            }
        });

        return TreeUtils.toTree(trees, RoleMenuAuthorizeTreeVo::getId,
                RoleMenuAuthorizeTreeVo::getParentId, RoleMenuAuthorizeTreeVo::setChild, 0L);
    }
}