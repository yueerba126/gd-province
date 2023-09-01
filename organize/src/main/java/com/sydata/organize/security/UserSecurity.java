package com.sydata.organize.security;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.sydata.framework.core.util.RsaUtil;
import com.sydata.framework.databind.service.DataBindParamInterceptor;
import com.sydata.organize.config.LoginConfig;
import com.sydata.organize.domain.Dept;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author lzq
 * @describe 用户安全
 * @date 2022-06-30 18:00
 */
@Component
public final class UserSecurity implements StpInterface, DataBindParamInterceptor {

    private static final String SA_SESSION = "saSession";
    private static final String LOGIN_USER = "loginUser";
    private static final String DATA_VIEW = "dataView";
    private static final String SELECT_STOCK_HOUSE_IDS = "selectStockHouseIds";


    private static LoginConfig loginConfig;

    private static RSA rsa;
    private static String passwordLengthErrMsg;

    public UserSecurity(LoginConfig loginConfig) {
        UserSecurity.loginConfig = loginConfig;
        rsa = new RSA(loginConfig.getPrivateKey(), loginConfig.getPublicKey());

        int min = loginConfig.getPasswordMinLength();
        int max = loginConfig.getPasswordMaxLength();
        UserSecurity.passwordLengthErrMsg = String.format("密码长度必须在%s-%s位之间", min, max);
    }


    /**
     * 密码格式校验
     *
     * @param password 密码
     */
    public static void passwordCheck(String password) {
        // 长度校验
        int length = password.length();
        int min = loginConfig.getPasswordMinLength();
        int max = loginConfig.getPasswordMaxLength();
        Assert.state(length >= min && length <= max, passwordLengthErrMsg);
    }

    /**
     * 公钥加密
     *
     * @param content 明文
     * @return 密文
     */
    public static String encryptByPublic(String content) {
        return RsaUtil.encryptToBase64(rsa, content, KeyType.PublicKey);
    }

    /**
     * 私钥加密
     *
     * @param content 明文
     * @return 密文
     */
    public static String encryptByPrivate(String content) {
        return RsaUtil.encryptToBase64(rsa, content, KeyType.PrivateKey);
    }

    /**
     * 公钥解密
     *
     * @param ciphertext 密文
     * @return 明文
     */
    public static String decryptByPublic(String ciphertext) {
        try {
            return RsaUtil.decryptByBase64Str(rsa, ciphertext, KeyType.PublicKey);
        } catch (Exception e) {
            throw new SaTokenException("解密失败");
        }
    }

    /**
     * 私钥解密
     *
     * @param ciphertext 密文
     * @return 明文
     */
    public static String decryptByPrivate(String ciphertext) {
        try {
            return RsaUtil.decryptByBase64Str(rsa, ciphertext, KeyType.PrivateKey);
        } catch (Exception e) {
            throw new SaTokenException("解密失败");
        }
    }


    /**
     * 设置当前登录用户
     *
     * @param loginUser 登录用户
     */
    public static void setLoginUser(LoginUser loginUser) {
        getSession().set(LOGIN_USER, loginUser);
    }

    /**
     * 设置数据视图
     *
     * @param dataView 数据视图
     */
    public static void setDataView(String dataView) {
        getSession().set(DATA_VIEW, dataView);
    }

    /**
     * 设置库区
     *
     * @param selectStockHouseIds 库区列表
     */
    public static void setStockHouseIds(Set<String> selectStockHouseIds) {
        getSession().set(SELECT_STOCK_HOUSE_IDS, selectStockHouseIds);
    }


    /**
     * 获取当前线程的SaSession
     *
     * @return SaSession
     */
    public static SaSession getSession() {
        if (!SpringMVCUtil.isWeb()) {
            return null;
        }

        HttpServletRequest request = SpringMVCUtil.getRequest();
        SaSession saSession = (SaSession) request.getAttribute(SA_SESSION);
        if (saSession == null) {
            Object loginId = StpUtil.getLoginIdDefaultNull();
            if (loginId != null) {
                saSession = StpUtil.getSessionByLoginId(loginId);
                request.setAttribute(SA_SESSION, saSession);
            }
        }
        return saSession;
    }

    /**
     * 获取当前登录用户
     *
     * @return 登录用户
     */
    public static LoginUser loginUser() {
        return Optional.ofNullable(getSession())
                .map(saSession -> saSession.getModel(LOGIN_USER, LoginUser.class))
                .orElse(null);
    }

    /**
     * 获取数据视图
     *
     * @return 数据视图
     */
    public static String getDataView() {
        return Optional.ofNullable(getSession())
                .map(saSession -> saSession.getString(DATA_VIEW))
                .orElse(null);
    }

    /**
     * 获取库区
     *
     * @return 选择的库区列表
     */
    public static Set<String> getStockHouseIds() {
        return Optional.ofNullable(getSession())
                .map(saSession -> (Set<String>) saSession.get(SELECT_STOCK_HOUSE_IDS))
                .orElse(Collections.emptySet());
    }

    /**
     * 获取当前登录用户名
     *
     * @return 登录用户名
     */
    public static String userName() {
        return Optional.ofNullable(loginUser()).map(LoginUser::getName).orElse(null);
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 用户身份证号码
     */
    public static String userId() {
        return Optional.ofNullable(loginUser()).map(LoginUser::getId).orElse(null);
    }

    /**
     * 获取当前登录用户组织ID
     *
     * @return 组织ID
     */
    public static String organizeId() {
        return Optional.ofNullable(loginUser()).map(LoginUser::getOrganizeId).orElse(null);
    }

    /**
     * 获取当前用户的部门ID
     *
     * @return 部门ID
     */
    public static Long deptId() {
        return Optional.ofNullable(loginUser()).map(LoginUser::getDept).map(Dept::getId).orElse(null);
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return StpUtil.getSessionByLoginId(loginId).getModel(LOGIN_USER, LoginUser.class).getMenuPermissions();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return StpUtil.getSessionByLoginId(loginId).getModel(LOGIN_USER, LoginUser.class).getRolePermissions();
    }

    @Override
    public void apply(Map<String, Object> cacheMap) {

    }

    @Override
    public Object getTransmitObject() {
        getSession();
        return RequestContextHolder.getRequestAttributes();
    }

    @Override
    public void setTransmitObjectToThread(Object object) {
        RequestContextHolder.setRequestAttributes((RequestAttributes) object);
    }

    @Override
    public void resetTransmitObject() {
        RequestContextHolder.resetRequestAttributes();
    }
}
