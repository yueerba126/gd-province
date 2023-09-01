package com.sydata.collect.api.controller;

import com.sydata.organize.param.AppLoginParam;
import com.sydata.organize.service.ITokenService;
import com.sydata.organize.vo.AppLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 数据收集-应用登录API(开放对接)
 * @date 2022/10/21 18:43
 */
@Api(tags = "数据收集-应用登录API(开放对接)")
@RestController
@Validated
@RequestMapping("/api/v2022")
public class AppLoginController {

    @Resource
    private ITokenService tokenService;

    @ApiOperation("应用登录")
    @PostMapping("/sfrz")
    public AppLoginVo loginByApp(@RequestBody @Valid AppLoginParam appLoginParam) {
        return tokenService.loginByApp(appLoginParam);
    }
}
