package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.domain.User;
import com.sydata.organize.param.*;
import com.sydata.organize.service.IUserService;
import com.sydata.organize.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 组织架构-用户Controller
 *
 * @author lzq
 * @date 2022-06-28
 */
@Api(tags = "组织架构-用户")
@Validated
@RestController
@RequestMapping("/org/user")
public class UserController {

    @Resource
    private IUserService userService;


    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<UserVo> page(@RequestBody @Valid UserPageParam pageParam) {
        return userService.pages(pageParam);
    }

    @ApiOperation("根据账号查询")
    @PostMapping("/get/account")
    public User getByAccount(@RequestParam("account") String account) {
        return userService.getByAccount(account);
    }

    @ApiOperation("根据ID查询")
    @PostMapping("/get/id")
    public UserVo details(@RequestParam("id") String id) {
        return userService.details(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid User user) {
        return userService.save(user);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid UserUpdateParam updateParam) {
        return userService.update(updateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid UserRemoveParam removeParam) {
        return userService.remove(removeParam);
    }

    @ApiOperation("修改密码")
    @PostMapping("/update/password")
    public Boolean updatePassword(@RequestBody @Valid UserUpdatePasswordParam passwordParam) {
        return userService.updatePassword(passwordParam);
    }

    @ApiOperation("设置部门")
    @PostMapping("/set/up/dept")
    public Boolean setUpDept(@RequestBody @Valid UserSetUpDeptParam userSetUpDeptParam) {
        return userService.setUpDept(userSetUpDeptParam);
    }

    @ApiOperation("解绑广东省统一身份认证用户ID")
    @PostMapping("/un/bind/gds/unified/identity/user/id")
    public Boolean unBindGdsUnifiedIdentityUserId() {
        return userService.unBindGdsUnifiedIdentityUserId();
    }
}