package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.annotation.DataBindUser;
import com.sydata.organize.domain.User;
import com.sydata.organize.mapper.UserMapper;
import com.sydata.organize.param.*;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IUserRoleService;
import com.sydata.organize.service.IUserService;
import com.sydata.organize.vo.UserVo;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

/**
 * 组织架构-用户Service业务层处理
 *
 * @author lzq
 * @date 2022-06-28
 */
@Service("userService")
@CacheConfig(cacheNames = UserServiceImpl.CACHE_NAME)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    final static String CACHE_NAME = "organize:user";

    @Resource
    private UserMapper userMapper;

    @Resource
    private IUserRoleService userRoleService;

    @Cacheable(key = "'id='+#id")
    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<User> listByIds(Collection<? extends Serializable> idList) {
        return MultiCacheBatchHelp
                .apply(CACHE_NAME).<String, User, User>composeExecute()
                .fields(User::getId)
                .params((Collection<String>) idList)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(User::getId, Function.identity()))
                .get()
                .stream()
                .collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'account='+#entity.account"),
    })
    @Override
    public boolean save(User entity) {
        // 使用私钥解密后校验密码格式
        String password = UserSecurity.decryptByPrivate(entity.getPassword());
        UserSecurity.passwordCheck(password);

        // 校验账号是否重复
        UserServiceImpl userService = SpringUtil.getBean(this.getClass());
        Assert.isNull(userService.getByAccount(entity.getAccount()), String.format("账号%s已存在", entity.getAccount()));

        // 使用私钥加密存入数据库
        return super.save(entity.setPassword(UserSecurity.encryptByPrivate(password)));
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'account='+#entity.account"),
    })
    @Override
    public boolean updateById(User entity) {
        // 校验账号是否重复
        UserServiceImpl userService = SpringUtil.getBean(this.getClass());
        User accountUser = userService.getByAccount(entity.getAccount());
        boolean accountState = accountUser == null || accountUser.getId().equals(entity.getId());
        Assert.state(accountState, String.format("账号%s已存在", entity.getName()));

        return super.updateById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<UserVo> pages(UserPageParam pageParam) {
        Page page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return userMapper.pages(page, pageParam);
    }

    @DataBindFieldConvert
    @Override
    public UserVo details(String id) {
        User user = SpringUtil.getBean(this.getClass()).getById(id);
        return BeanUtils.copyByClass(user, UserVo.class);
    }

    @Cacheable(key = "'account='+#account")
    @Override
    public User getByAccount(String account) {
        return super.lambdaQuery().eq(User::getAccount, account).one();
    }

    @Cacheable(key = "'gdsUnifiedIdentityUserId='+#gdsUnifiedIdentityUserId")
    @Override
    public User getByGdsUnifiedIdentityUserId(String gdsUnifiedIdentityUserId) {
        return super.lambdaQuery().eq(User::getGdsUnifiedIdentityUserId, gdsUnifiedIdentityUserId).one();
    }

    @Override
    public Boolean update(UserUpdateParam updateParam) {
        User user = BeanUtils.copyByClass(updateParam, User.class);
        return SpringUtil.getBean(this.getClass()).updateById(user);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#removeParam.id"),
            @CacheEvict(key = "'account='+#removeParam.account")
    })
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean remove(UserRemoveParam removeParam) {
        // 清除广东省统一身份认证用户ID缓存
        User user = SpringUtil.getBean(this.getClass()).getById(removeParam.getId());
        if (ObjectUtils.isNotEmpty(user.getGdsUnifiedIdentityUserId())) {
            MultiCacheBatchHelp.apply(CACHE_NAME).<String, User, User>composeExecute()
                    .fields(User::getGdsUnifiedIdentityUserId)
                    .params(Collections.singletonList(user.getGdsUnifiedIdentityUserId()))
                    .remove();
        }

        boolean state = super.removeById(removeParam.getId());
        if (state) {
            userRoleService.removeByUserId(removeParam.getId());
        }
        return state;
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#passwordParam.id"),
            @CacheEvict(key = "'account='+#passwordParam.account"),
    })
    @Override
    public Boolean updatePassword(UserUpdatePasswordParam passwordParam) {
        // 使用私钥解密后校验密码格式
        String password = UserSecurity.decryptByPrivate(passwordParam.getPassword());
        UserSecurity.passwordCheck(password);

        // 使用私钥加密存入数据库
        return super.lambdaUpdate()
                .eq(User::getId, passwordParam.getId())
                .set(User::getPassword, UserSecurity.encryptByPrivate(password))
                .update();
    }

    @Override
    public Boolean setUpDept(UserSetUpDeptParam userSetUpDeptParam) {
        boolean update = super.lambdaUpdate()
                .set(User::getDeptId, userSetUpDeptParam.getDeptId())
                .in(User::getId, userSetUpDeptParam.getUserIds())
                .update();

        // 清除用户ID缓存
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, User, User>composeExecute()
                .fields(User::getId)
                .params(userSetUpDeptParam.getUserIds())
                .remove();

        // 清除用户账号缓存
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, User, User>composeExecute()
                .fields(User::getAccount)
                .params(userSetUpDeptParam.getAccounts())
                .remove();
        return update;
    }

    @Override
    public List<String> idsByOrganizeId(String organizeId) {
        return super.lambdaQuery()
                .select(User::getId)
                .eq(User::getOrganizeId, organizeId)
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> idsByDeptId(Long deptId) {
        return super.lambdaQuery()
                .select(User::getId)
                .eq(User::getDeptId, deptId)
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#userId"),
            @CacheEvict(key = "'gdsUnifiedIdentityUserId='+#gdsUnifiedIdentityUserId"),
    })
    @Override
    public boolean bindGdsUnifiedIdentityUserId(String userId, String gdsUnifiedIdentityUserId) {
        boolean state = super.lambdaUpdate()
                .set(User::getGdsUnifiedIdentityUserId, gdsUnifiedIdentityUserId)
                .isNull(User::getGdsUnifiedIdentityUserId)
                .eq(User::getId, userId)
                .update();
        Assert.state(state, "广东省统一身份认证绑定失败：当前用户已存在绑定关系");
        return TRUE;
    }

    @Override
    public boolean unBindGdsUnifiedIdentityUserId() {
        String userId = UserSecurity.userId();
        User user = SpringUtil.getBean(this.getClass()).getById(userId);
        String gdsUnifiedIdentityUserId = user.getGdsUnifiedIdentityUserId();
        Assert.hasLength(gdsUnifiedIdentityUserId, "广东省统一身份认证解除绑定失败：当前用户未存在绑定关系");

        boolean state = super.lambdaUpdate()
                .set(User::getGdsUnifiedIdentityUserId, null)
                .isNotNull(User::getGdsUnifiedIdentityUserId)
                .eq(User::getId, userId)
                .update();
        Assert.state(state, "广东省统一身份认证解除绑定失败：当前用户未存在绑定关系");

        // 清除用户ID缓存
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, User, User>composeExecute()
                .fields(User::getId)
                .params(Collections.singletonList(userId))
                .remove();

        // 清除广东省统一身份认证用户ID缓存
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, User, User>composeExecute()
                .fields(User::getGdsUnifiedIdentityUserId)
                .params(Collections.singletonList(gdsUnifiedIdentityUserId))
                .remove();

        return TRUE;
    }

    @DataBindService(strategy = DataBindUser.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, userMapper);
    }
}