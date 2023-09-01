package com.sydata.organize.controller;

import com.sydata.organize.param.GdsLoginParam;
import com.sydata.organize.param.LoginParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.ITokenService;
import com.sydata.organize.vo.CaptchaVo;
import com.sydata.organize.vo.GdsUnifiedIdentityLoginVo;
import com.sydata.organize.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author lzq
 * @describe tokenAPI
 * @date 2022-06-29 09:11
 */
@Api(tags = "组织架构-tokenAPI")
@Validated
@RestController
@RequestMapping("/org/token")
public class TokenController {

    @Resource
    private ITokenService tokenService;

    @ApiOperation("获取验证码")
    @PostMapping(value = "/captcha")
    public CaptchaVo captcha() {
        return tokenService.captcha();
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public LoginVo login(@RequestBody @Valid LoginParam loginParam) {
        return tokenService.login(loginParam);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public void logout() {
        tokenService.logout();
    }

    @ApiOperation("广东省统一身份认证登录")
    @PostMapping("/gds/unified/identity/login")
    public GdsUnifiedIdentityLoginVo gdsUnifiedIdentityLogin(@RequestBody @Valid GdsLoginParam loginParam) {
        return tokenService.gdsUnifiedIdentityLogin(loginParam);
    }

    @ApiOperation("封禁")
    @PostMapping("/disable")
    public void disable(@RequestParam("loginId") String loginId) {
        tokenService.disable(loginId);
    }

    @ApiOperation("解除封禁")
    @PostMapping("/untie/disable")
    public void untieDisable(@RequestParam("loginId") String loginId) {
        tokenService.untieDisable(loginId);
    }

    @ApiOperation("切换数据视图")
    @PostMapping("/switch/view")
    public void switchView(@RequestParam("dataView") String dataView) {
        tokenService.switchView(dataView);
    }

    @ApiOperation("获取数据视图")
    @PostMapping("/data/view")
    public String dataView() {
        return UserSecurity.getDataView();
    }

    @ApiOperation("选择库区")
    @PostMapping("/select/stock/house")
    public void selectStockHouseIds(@RequestBody @Valid @NotNull(message = "库区不能为空") Set<String> stockHouseIds) {
        tokenService.selectStockHouseIds(stockHouseIds);
    }

    @ApiOperation("获取已选择的库区列表")
    @PostMapping("/stock/house")
    public Set<String> stockHouseIds() {
        return UserSecurity.getStockHouseIds();
    }
}
