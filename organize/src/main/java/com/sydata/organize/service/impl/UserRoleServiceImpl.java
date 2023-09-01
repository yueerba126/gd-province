package com.sydata.organize.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.cache.batch.MultiCacheBatch;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.domain.Role;
import com.sydata.organize.domain.User;
import com.sydata.organize.domain.UserRole;
import com.sydata.organize.mapper.UserRoleMapper;
import com.sydata.organize.param.RolePageParam;
import com.sydata.organize.param.UserPageParam;
import com.sydata.organize.param.UserRoleSetUpParam;
import com.sydata.organize.service.IRoleService;
import com.sydata.organize.service.IUserRoleService;
import com.sydata.organize.service.IUserService;
import com.sydata.organize.vo.UserRoleAuthorizeRoleVo;
import com.sydata.organize.vo.UserRoleAuthorizeUserVo;
import com.sydata.organize.vo.UserRoleRoleVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

/**
 * 组织架构-用户角色Service业务层处理
 *
 * @author lzq
 * @date 2022-06-28
 */
@Service("userRoleService")
@CacheConfig(cacheNames = UserRoleServiceImpl.CACHE_NAME)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    final static String CACHE_NAME = "organize:userRole";

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Override
    public boolean setUp(UserRoleSetUpParam setUpParam) {
        // 组装数据-批量新增
        List<UserRole> userRoles = new ArrayList<>();
        setUpParam.getRoleIds().forEach(roleId -> {
            Set<String> userIds = setUpParam.getUserIds();
            userIds.forEach(userId -> {
                long id = IdUtil.getSnowflakeNextId();
                UserRole userRole = new UserRole().setId(id).setUserId(userId).setRoleId(roleId);
                userRoles.add(userRole);
            });
        });

        boolean state = userRoleMapper.insertBatchIgnore(userRoles);

        // 清理缓存
        clearCache(setUpParam);
        return state;
    }

    @Override
    public boolean cancel(UserRoleSetUpParam setUpParam) {
        boolean state = super.lambdaUpdate()
                .in(UserRole::getUserId, setUpParam.getUserIds())
                .in(UserRole::getRoleId, setUpParam.getRoleIds())
                .remove();

        // 清理缓存
        clearCache(setUpParam);

        return state;
    }

    @Override
    public boolean removeByUserId(String userId) {
        Set<Long> roleIds = super.lambdaQuery().select(UserRole::getRoleId)
                .eq(UserRole::getUserId, userId)
                .list()
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(roleIds)) {
            return Boolean.FALSE;
        }

        // 取消用户角色
        UserRoleSetUpParam setUpParam = new UserRoleSetUpParam()
                .setRoleIds(roleIds)
                .setUserIds(Collections.singleton(userId));
        return cancel(setUpParam);
    }

    @Override
    public boolean removeByRoleId(Long roleId) {
        Set<String> userIds = super.lambdaQuery().select(UserRole::getUserId)
                .eq(UserRole::getRoleId, roleId)
                .list()
                .stream()
                .map(UserRole::getUserId)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(userIds)) {
            return Boolean.FALSE;
        }

        // 取消用户角色
        UserRoleSetUpParam setUpParam = new UserRoleSetUpParam()
                .setRoleIds(Collections.singleton(roleId))
                .setUserIds(userIds);
        return cancel(setUpParam);
    }

    @Cacheable(key = "'userId='+#userId")
    @Override
    public List<UserRole> listByUserId(String userId) {
        return super.lambdaQuery().eq(UserRole::getUserId, userId).list();
    }

    @Cacheable(key = "'roleId='+#roleId")
    @Override
    public List<UserRole> listByRoleId(Long roleId) {
        return super.lambdaQuery().eq(UserRole::getRoleId, roleId).list();
    }

    @Override
    public List<Role> rolesByUserId(String userId) {
        List<UserRole> userRoles = SpringUtil.getBean(this.getClass()).listByUserId(userId);
        List<UserRoleRoleVo> userRoleRoleVos = BeanUtils.copyToList(userRoles, UserRoleRoleVo.class);

        DataBindHandleBootstrap.dataHandConvert(userRoleRoleVos);
        return StreamEx.of(userRoleRoleVos).map(UserRoleRoleVo::getRole).toList();
    }

    @Override
    public Page<UserRoleAuthorizeRoleVo> authorizeRolePage(RolePageParam pageParam, String userId) {
        // 查询角色列表
        Page pages = roleService.pages(pageParam);

        // 查询指定用户的角色ID
        List<UserRole> userRoles = SpringUtil.getBean(this.getClass()).listByUserId(userId);
        Set<Long> roleIds = StreamEx.of(emptyIfNull(userRoles)).map(UserRole::getRoleId).toSet();

        // 组装数据
        List<UserRoleAuthorizeRoleVo> roleVos = StreamEx.of((List<Role>) pages.getRecords())
                .map(role -> BeanUtils.copyByClass(role, UserRoleAuthorizeRoleVo.class))
                .peek(vo -> vo.setChecked(roleIds.contains(vo.getId())))
                .toList();
        return pages.setRecords(roleVos);
    }

    @DataBindFieldConvert
    @Override
    public Page<UserRoleAuthorizeUserVo> authorizeUserPage(UserPageParam pageParam, Long roleId) {
        // 查询用户列表
        Page pages = userService.pages(pageParam);

        // 查询指定角色的用户ID
        List<UserRole> userRoles = SpringUtil.getBean(this.getClass()).listByRoleId(roleId);
        Set<String> userIds = StreamEx.of(emptyIfNull(userRoles)).map(UserRole::getUserId).toSet();

        // 组装数据
        List<UserRoleAuthorizeUserVo> userVos = StreamEx.of((List<User>) pages.getRecords())
                .map(role -> BeanUtils.copyByClass(role, UserRoleAuthorizeUserVo.class))
                .peek(vo -> vo.setChecked(userIds.contains(vo.getId())))
                .toList();

        return pages.setRecords(userVos);
    }

    /**
     * 清理缓存
     *
     * @param setUpParam 用户角色设置参数
     */
    private void clearCache(UserRoleSetUpParam setUpParam) {
        MultiCacheBatch cacheBatch = MultiCacheBatchHelp.apply(CACHE_NAME);

        cacheBatch.<String, UserRole, UserRole>composeExecute()
                .fields(UserRole::getUserId)
                .params(setUpParam.getUserIds())
                .remove();


        cacheBatch.<Long, UserRole, UserRole>composeExecute()
                .fields(UserRole::getRoleId)
                .params(setUpParam.getRoleIds())
                .remove();
    }

}