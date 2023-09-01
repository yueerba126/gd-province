package com.sydata.organize.service;

import com.sydata.organize.domain.User;
import com.sydata.organize.enums.LoginDeviceEnum;
import com.sydata.organize.param.AppLoginParam;
import com.sydata.organize.param.GdsLoginParam;
import com.sydata.organize.param.LoginParam;
import com.sydata.organize.vo.AppLoginVo;
import com.sydata.organize.vo.CaptchaVo;
import com.sydata.organize.vo.GdsUnifiedIdentityLoginVo;
import com.sydata.organize.vo.LoginVo;

import java.util.Set;

/**
 * @author lzq
 * @describe 组织架构-tokenService接口
 * @date 2022-06-29 09:46
 */
public interface ITokenService {

    /**
     * 获取验证码
     *
     * @return 验证码VO
     */
    CaptchaVo captcha();

    /**
     * 登录
     *
     * @param loginParam 登录参数
     * @return 登录VO
     */
    LoginVo login(LoginParam loginParam);

    /**
     * 广东省统一身份认证登录
     *
     * @param loginParam 广东省统一身份认证登录参数
     * @return 广东省统一身份认证登录VO
     */
    GdsUnifiedIdentityLoginVo gdsUnifiedIdentityLogin(GdsLoginParam loginParam);

    /**
     * 注销登录
     */
    void logout();

    /**
     * 根据用户ID登录
     *
     * @param userId     用户ID
     * @param deviceEnum 登录设备枚举
     * @return 登录VO
     */
    LoginVo loginByUserId(Long userId, LoginDeviceEnum deviceEnum);

    /**
     * 根据用户登录
     *
     * @param user       用户
     * @param deviceEnum 登录设备枚举
     * @return 登录VO
     */
    LoginVo loginByUser(User user, LoginDeviceEnum deviceEnum);

    /**
     * app应用登录
     *
     * @param appLoginParam app应用登录参数
     * @return app应用登录响应VO
     */
    AppLoginVo loginByApp(AppLoginParam appLoginParam);

    /**
     * 封禁账号
     *
     * @param loginId 登录ID
     */
    void disable(String loginId);

    /**
     * 解除封禁
     *
     * @param loginId 登录ID
     */
    void untieDisable(String loginId);

    /**
     * 切换数据视图
     *
     * @param dataView 数据视图
     */
    void switchView(String dataView);

    /**
     * 选择库区ID列表
     *
     * @param stockHouseIds 库区ID列表
     */
    void selectStockHouseIds(Set<String> stockHouseIds);
}
