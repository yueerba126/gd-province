package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.param.RolePageParam;
import com.sydata.organize.param.UserPageParam;
import com.sydata.organize.param.UserRoleSetUpParam;
import com.sydata.organize.service.IUserRoleService;
import com.sydata.organize.vo.UserRoleAuthorizeRoleVo;
import com.sydata.organize.vo.UserRoleAuthorizeUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 组织架构-用户角色Controller
 *
 * @author lzq
 * @date 2022-06-28
 */
@Api(tags = "组织架构-用户角色")
@Validated
@RestController
@RequestMapping("/org/user/role")
public class UserRoleController {

    @Resource
    private IUserRoleService userRoleService;

    @ApiOperation("设置用户角色(增量)")
    @PostMapping("/set/up")
    public Boolean setUp(@RequestBody @Valid UserRoleSetUpParam setUpParam) {
        return userRoleService.setUp(setUpParam);
    }

    @ApiOperation("取消用户角色(增量)")
    @PostMapping("/cancel")
    public Boolean cancelUp(@RequestBody @Valid UserRoleSetUpParam setUpParam) {
        return userRoleService.cancel(setUpParam);
    }

    @ApiOperation("查询用户的角色授权列表")
    @PostMapping("/authorize/role/page/{userId}")
    public Page<UserRoleAuthorizeRoleVo> authorizeRolePage(@RequestBody @Valid RolePageParam pageParam,
                                                           @PathVariable("userId") String userId) {
        return userRoleService.authorizeRolePage(pageParam, userId);
    }

    @ApiOperation("查询角色的用户授权列表")
    @PostMapping("/authorize/user/page/{roleId}")
    public Page<UserRoleAuthorizeUserVo> authorizeUserPage(@RequestBody @Valid UserPageParam pageParam,
                                                           @PathVariable("roleId") Long roleId) {
        return userRoleService.authorizeUserPage(pageParam, roleId);
    }
}