package com.sydata.organize.service.impl;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.util.AesUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.config.GdsUnifiedIdentityConfig;
import com.sydata.organize.config.LoginConfig;
import com.sydata.organize.domain.*;
import com.sydata.organize.dto.CaptchaVerifyDto;
import com.sydata.organize.enums.DataViewEnum;
import com.sydata.organize.enums.LoginDeviceEnum;
import com.sydata.organize.interfaces.IGdsUnifiedIdentityApi;
import com.sydata.organize.interfaces.vo.OauthTokenVo;
import com.sydata.organize.interfaces.vo.UserInfoDetailVo;
import com.sydata.organize.param.AppLoginParam;
import com.sydata.organize.param.GdsLoginParam;
import com.sydata.organize.param.LoginParam;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.*;
import com.sydata.organize.vo.*;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static com.sydata.organize.enums.LoginDeviceEnum.API;
import static java.math.BigDecimal.ZERO;

/**
 * @author lzq
 * @describe 组织架构-tokenService接口实现类
 * @date 2022-06-29 09:46
 */
@Service("tokenService")
@Slf4j
public class TokenServiceImpl implements ITokenService {

    private static final String APP_LOGIN_ERR_MSG = "appid/appkey错误";

    @Resource
    private IUserService userService;

    @Resource
    private SaTokenConfig saTokenConfig;

    @Resource
    private LoginConfig loginConfig;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private IOrganizeService organizeService;

    @Resource
    private IDeptService deptService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IAppApiService appApiService;

    @Resource
    private IRegionService regionService;

    @Resource
    private IMenuSystemService menuSystemService;

    @Resource
    private IGdsUnifiedIdentityApi gdsUnifiedIdentityApi;

    @Resource
    private GdsUnifiedIdentityConfig gdsUnifiedIdentityConfig;

    @Override
    public CaptchaVo captcha() {
        // 生成圆圈干扰验证码转换为Base64的Data URI形式
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        String verify = captcha.getCode();
        String data = captcha.getImageBase64Data();
        String verifyId = IdWorker.getIdStr();

        // 将验证码存入redis
        LocalDateTime generationTime = LocalDateTime.now();
        String captchaCacheKey = new StringJoiner(COLON)
                .add(saTokenConfig.getTokenName())
                .add(loginConfig.getCaptchaCacheKey())
                .add(verifyId).toString();
        CaptchaVerifyDto captchaVerifyDto = new CaptchaVerifyDto().setGenTime(generationTime).setVerify(verify);
        Duration timeout = Duration.ofSeconds(loginConfig.getCaptchaCacheTime());
        redisTemplate.opsForValue().set(captchaCacheKey, captchaVerifyDto, timeout);

        return new CaptchaVo().setCode(data).setVerifyId(verifyId);
    }

    @Override
    public LoginVo login(LoginParam loginParam) {
        LoginDeviceEnum deviceEnum = LoginDeviceEnum.getByDevice(loginParam.getDevice());
        Assert.notNull(deviceEnum, "登录设备错误");

        // 校验验证码
        verifyCaptcha(loginParam.getVerify(), loginParam.getVerifyId());

        // 根据登录账号查询用户
        User user = userService.getByAccount(loginParam.getAccount());
        Assert.notNull(user, "账号密码错误");
        checkDisableLogin(deviceEnum, user.getId());

        // 校验密码
        String inputPassword = UserSecurity.decryptByPrivate(loginParam.getPassword());
        String userPassword = UserSecurity.decryptByPublic(user.getPassword());
        Assert.state(Objects.equals(userPassword, inputPassword), () -> {
            triggerLoginDisable(user.getId());
            return "账号密码错误";
        });

        // 绑定广东省统一身份认证用户ID
        String gdsUnifiedIdentityUserId = loginParam.getGdsUnifiedIdentityUserId();
        if (ObjectUtils.isNotEmpty(gdsUnifiedIdentityUserId)) {
            userService.bindGdsUnifiedIdentityUserId(user.getId(), gdsUnifiedIdentityUserId);
        }
        return loginByUser(user, deviceEnum);
    }

    @Override
    public GdsUnifiedIdentityLoginVo gdsUnifiedIdentityLogin(GdsLoginParam loginParam) {
        LoginDeviceEnum deviceEnum = LoginDeviceEnum.getByDevice(loginParam.getDevice());
        Assert.notNull(deviceEnum, "登录设备错误");

        //  获取广东省身份认证访问令牌
        OauthTokenVo oauthTokenVo = gdsUnifiedIdentityApi.oauthToken(gdsUnifiedIdentityConfig.getClientId(),
                        gdsUnifiedIdentityConfig.getGrantType(), loginParam.getRedirectUri(), loginParam.getCode(),
                        gdsUnifiedIdentityConfig.getClientSecret())
                .checkData();

        // 获取广东省身份认证登录账号详细信息
        UserInfoDetailVo userInfo = gdsUnifiedIdentityApi.getUserInfoDetail(oauthTokenVo.getAccessToken()).checkData();

        // 根据广东省统一身份认证用户ID查询用户
        User user = userService.getByGdsUnifiedIdentityUserId(userInfo.getUserId());
        if (user == null) {
            return new GdsUnifiedIdentityLoginVo().setGdsUnifiedIdentityUserId(userInfo.getUserId());
        }
        return new GdsUnifiedIdentityLoginVo().setLoginVo(loginByUser(user, deviceEnum));
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public LoginVo loginByUserId(Long userId, LoginDeviceEnum deviceEnum) {
        User user = userService.getById(userId);
        return loginByUser(user, deviceEnum);
    }

    @Override
    public LoginVo loginByUser(User user, LoginDeviceEnum deviceEnum) {
        // 获取用户角色列表
        List<Role> roles = userRoleService.rolesByUserId(user.getId());
        List<String> rolePermissions = StreamEx.of(roles).map(Role::getPermission).filter(StringUtils::isNotEmpty).toList();

        // 获取角色菜单列表
        List<Menu> menus = StreamEx.of(roles).map(Role::getId).toListAndThen(roleMenuService::menusByRoleIds);
        List<String> menuPermissions = StreamEx.of(menus).map(Menu::getPermission).filter(StringUtils::isNotEmpty).toList();
        List<MenuTreeVo> menuTrees = TreeUtils.toTree(BeanUtils.copyToList(menus, MenuTreeVo.class)
                , MenuTreeVo::getId, MenuTreeVo::getParentId, MenuTreeVo::setChild, 0L);

        // 获取组织
        Organize organize = organizeService.getById(user.getOrganizeId());
        String regionType = regionService.getById(organize.getRegionId()).getType();

        // 获取部门
        Dept dept = user.getDeptId() == null ? null : deptService.getById(user.getDeptId());

        // 获取系统
        MenuSystem menuSystem = menuSystemService.getById(organize.getMenuSystemId());

        // 登录成功 -- 写入登录用户信息
        StpUtil.login(user.getId(), deviceEnum.getDevice());
        clearLoginDisableState(user.getId());

        LoginUser loginUser = new LoginUser()
                .setId(user.getId())
                .setName(user.getName())
                .setAccount(user.getAccount())
                .setOrganizeId(organize.getId())
                .setOrganizeName(organize.getName())
                .setOrganizeParentId(organize.getParentId())
                .setOrganizeParentIds(organize.getParentIds())
                .setOrganizeKind(organize.getKind())
                .setOrganizeBusType(organize.getBusType())
                .setRegionId(organize.getRegionId())
                .setRegionType(regionType)
                .setCountryId(organize.getCountryId())
                .setProvinceId(organize.getProvinceId())
                .setCityId(organize.getCityId())
                .setAreaId(organize.getAreaId())
                .setRoles(roles)
                .setDept(dept)
                .setMenus(menus)
                .setMenuTrees(menuTrees)
                .setMenuPermissions(menuPermissions)
                .setRolePermissions(rolePermissions)
                .setLoginDevice(deviceEnum.getDevice());

        UserSecurity.setLoginUser(loginUser);
        UserSecurity.setDataView(DataViewEnum.ASCRIPTION.getView());

        return new LoginVo()
                .setTokenName(saTokenConfig.getTokenName())
                .setTokenPrefix(saTokenConfig.getTokenPrefix())
                .setToken(StpUtil.getTokenValue())
                .setMenuSystem(menuSystem)
                .setLoginUser(loginUser);
    }

    @Override
    public AppLoginVo loginByApp(AppLoginParam appLoginParam) {
        // 根据appid查询APP应用
        AppApi appApi = appApiService.getById(appLoginParam.getAppid());
        Assert.notNull(appApi, APP_LOGIN_ERR_MSG);
        Assert.state(appApi.getState(), "您的账号已禁用");
        checkDisableLogin(API, appApi.getAppid());

        // 校验appKey
        String appKey;
        String appSecret;
        try {
            appSecret = UserSecurity.decryptByPublic(appApi.getAppSecret());
            appKey = AesUtil.decryptBase64(appSecret, appLoginParam.getAppKey());
        } catch (Exception e) {
            log.error("APP登录,appKey解密失败", e);
            throw new IllegalStateException(APP_LOGIN_ERR_MSG);
        }

        Assert.state(Objects.equals(appApi.getAppKey(), appKey), () -> {
            triggerLoginDisable(appApi.getAppid());
            return APP_LOGIN_ERR_MSG;
        });

        // 查询组织是否存在
        Organize organize = organizeService.getById(appApi.getOrganizeId());
        String regionType = regionService.getById(organize.getRegionId()).getType();
        Assert.notNull(organize, "企业信息丢失");

        // 登录成功 -- 写入登录APP信息（不需要权限,登录即可）
        StpUtil.login(appApi.getAppid(), API.getDevice());
        clearLoginDisableState(appApi.getAppid());

        LoginUser loginUser = new LoginUser()
                .setId(appApi.getAppid())
                .setName(appApi.getAppName())
                .setOrganizeId(organize.getId())
                .setOrganizeName(organize.getName())
                .setOrganizeKind(organize.getKind())
                .setOrganizeBusType(organize.getBusType())
                .setRegionId(appApi.getRegionId())
                .setCountryId(appApi.getCountryId())
                .setProvinceId(appApi.getProvinceId())
                .setCityId(appApi.getCityId())
                .setAreaId(appApi.getAreaId())
                .setRegionType(regionType)
                .setAppSecret(appSecret)
                .setStockHouseId(appApi.getStockHouseId())
                .setLoginDevice(API.getDevice());
        UserSecurity.setLoginUser(loginUser);
        UserSecurity.setDataView(DataViewEnum.ASCRIPTION.getView());

        return new AppLoginVo(appApi.getAppid(), StpUtil.getTokenValue(), organize.getId(), organize.getName());
    }

    @Override
    public void disable(String loginId) {
        StpUtil.disable(loginId, loginConfig.getDisableTime());
    }

    @Override
    public void untieDisable(String loginId) {
        StpUtil.untieDisable(loginId);
    }

    @Override
    public void switchView(String dataView) {
        Assert.notNull(DataViewEnum.getByView(dataView), "不存在该数据视图：" + dataView);
        selectStockHouseIds(Collections.emptySet());
        UserSecurity.setDataView(dataView);
    }

    @Override
    public void selectStockHouseIds(Set<String> stockHouseIds) {
        UserSecurity.setStockHouseIds(stockHouseIds);
    }

    /**
     * 校验验证码
     *
     * @param verify   验证码
     * @param verifyId 验证码ID
     */
    private void verifyCaptcha(String verify, String verifyId) {
        String captchaCacheKey = null;
        try {
            LocalDateTime now = LocalDateTime.now();

            // 校验验证码是否存在
            captchaCacheKey = new StringJoiner(COLON)
                    .add(saTokenConfig.getTokenName())
                    .add(loginConfig.getCaptchaCacheKey())
                    .add(verifyId).toString();
            CaptchaVerifyDto captchaVerifyDto = (CaptchaVerifyDto) redisTemplate.opsForValue().get(captchaCacheKey);
            Assert.notNull(captchaVerifyDto, "验证码未找到");

            // 校验验证码是否准确
            Assert.state(verify.equals(captchaVerifyDto.getVerify()), "验证码不正确");

            // 校验验证码是否已过期
            long time = Duration.between(captchaVerifyDto.getGenTime(), now).getSeconds();
            Assert.state(loginConfig.getCaptchaValidTime() > time, "验证码已过期");
        } finally {
            redisTemplate.delete(captchaCacheKey);
        }
    }

    /**
     * 校验账号是否已被封禁
     *
     * @param loginDeviceEnum 登录设备枚举
     * @param loginId         登录ID
     */
    private void checkDisableLogin(LoginDeviceEnum loginDeviceEnum, String loginId) {
        if (StpUtil.isDisable(loginId)) {
            throw new DisableLoginException(loginDeviceEnum.getDevice(), loginId, StpUtil.getDisableTime(loginId));
        }
    }

    /**
     * 触发封禁
     *
     * @param loginId 登录ID
     */
    private void triggerLoginDisable(String loginId) {
        // 设置登录失败次数,达到限制后会根据封禁时间进行封禁账号
        String loginFailCacheKey = new StringJoiner(COLON)
                .add(saTokenConfig.getTokenName())
                .add(loginConfig.getLoginFailCacheKey())
                .add(loginId)
                .toString();
        Duration timeout = Duration.ofSeconds(loginConfig.getLoginFailCacheTime());
        redisTemplate.opsForValue().setIfAbsent(loginFailCacheKey, ZERO.intValue(), timeout);

        Long count = redisTemplate.opsForValue().increment(loginFailCacheKey);
        if (count >= loginConfig.getLoginFailOnDisableCount()) {
            disable(loginId);
        }
    }

    /**
     * 清理登录失败状态
     *
     * @param loginId 登录ID
     */
    private void clearLoginDisableState(String loginId) {
        String loginFailCacheKey = new StringJoiner(COLON)
                .add(saTokenConfig.getTokenName())
                .add(loginConfig.getLoginFailCacheKey())
                .add(loginId)
                .toString();
        redisTemplate.delete(loginFailCacheKey);
    }
}
