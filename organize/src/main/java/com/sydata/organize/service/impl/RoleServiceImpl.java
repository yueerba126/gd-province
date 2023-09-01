package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.organize.annotation.DataBindRole;
import com.sydata.organize.config.DataPermissionConfig;
import com.sydata.organize.domain.Role;
import com.sydata.organize.domain.UserRole;
import com.sydata.organize.mapper.RoleMapper;
import com.sydata.organize.param.RolePageParam;
import com.sydata.organize.param.RoleRemoveParam;
import com.sydata.organize.param.UserRoleSetUpParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IRoleMenuService;
import com.sydata.organize.service.IRoleService;
import com.sydata.organize.service.IUserRoleService;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 组织架构-角色Service业务层处理
 *
 * @author lzq
 * @date 2022-06-28
 */
@Service("roleService")
@CacheConfig(cacheNames = RoleServiceImpl.CACHE_NAME)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    final static String CACHE_NAME = "organize:role";

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private DataPermissionConfig dataPermissionConfig;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Cacheable(key = "'id='+#id")
    @Override
    public Role getById(Serializable id) {
        return super.getById(id);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'name='+#entity.name"),
    })
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean save(Role entity) {
        RoleServiceImpl roleService = SpringUtil.getBean(this.getClass());
        Assert.isNull(roleService.getByName(entity.getName()), String.format("角色名%s已存在", entity.getName()));

        super.save(entity);

        // 设置用户角色
        UserRoleSetUpParam setUpParam = new UserRoleSetUpParam()
                .setRoleIds(Collections.singleton(entity.getId()))
                .setUserIds(Collections.singleton(UserSecurity.userId()));
        return userRoleService.setUp(setUpParam);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'name='+#entity.name"),
    })
    @Override
    public boolean updateById(Role entity) {
        RoleServiceImpl roleService = SpringUtil.getBean(this.getClass());

        Role nameRole = roleService.getByName(entity.getName());
        boolean nameState = nameRole == null || nameRole.getId().equals(entity.getId());
        Assert.state(nameState, String.format("角色名%s已存在", entity.getName()));

        return super.updateById(entity);
    }

    @Override
    public Page<Role> pages(RolePageParam pageParam) {
        // 判断是否需要控制数据权限 -- 只能看自己拥有的角色（超管除外）
        List<UserRole> roles = userRoleService.listByUserId(UserSecurity.userId());
        Set<Long> roleIds = StreamEx.of(roles).map(UserRole::getRoleId).toSet();
        boolean isAdmin = roleIds.contains(dataPermissionConfig.getAdminRoleId());

        return super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getName()), Role::getName, pageParam.getName())
                .eq(isNotEmpty(pageParam.getPermission()), Role::getPermission, pageParam.getPermission())
                .in(!isAdmin && isNotEmpty(roleIds), Role::getId, roleIds)
                .orderByAsc(Role::getOrderNum)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
    }

    @Cacheable(key = "'name='+#name")
    @Override
    public Role getByName(String name) {
        return super.lambdaQuery().eq(Role::getName, name).one();
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#removeParam.id"),
            @CacheEvict(key = "'name='+#removeParam.name"),
    })
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean remove(RoleRemoveParam removeParam) {
        Set<Long> roleIds = new HashSet<>();
        Set<String> fieldNames = ClassFieldMapUtil.mapByClass(DataPermissionConfig.class).keySet();
        fieldNames.forEach(fieldName -> {
            Object roleId = ClassFieldMapUtil.getFieldVal(dataPermissionConfig, fieldName);
            if (roleId instanceof Long) {
                roleIds.add((Long) roleId);
            }
        });
        Assert.state(!roleIds.contains(removeParam.getId()), "系统内置角色不可删除");

        boolean state = super.removeById(removeParam.getId());
        if (state) {
            userRoleService.removeByRoleId(removeParam.getId());
            roleMenuService.removeByRoleId(removeParam.getId());
        }
        return state;
    }

    @DataBindService(strategy = DataBindRole.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, roleMapper);
    }
}
