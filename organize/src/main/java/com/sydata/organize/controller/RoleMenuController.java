package com.sydata.organize.controller;

import com.sydata.organize.param.RoleMenuSetUpParam;
import com.sydata.organize.service.IRoleMenuService;
import com.sydata.organize.vo.RoleMenuAuthorizeTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 组织架构-角色菜单Controller
 *
 * @author lzq
 * @date 2022-06-28
 */
@Api(tags = "组织架构-角色菜单")
@Validated
@RestController
@RequestMapping("/org/role/menu")
public class RoleMenuController {

    @Resource
    private IRoleMenuService roleMenuService;

    @ApiOperation("设置角色菜单(增量)")
    @PostMapping("/set/up")
    public Boolean setUp(@RequestBody @Valid RoleMenuSetUpParam setUpParam) {
        return roleMenuService.setUp(setUpParam);
    }

    @ApiOperation("取消角色菜单(减量)")
    @PostMapping("/cancel")
    public Boolean cancel(@RequestBody @Valid RoleMenuSetUpParam setUpParam) {
        return roleMenuService.cancel(setUpParam);
    }

    @ApiOperation("查询角色菜单授权树")
    @PostMapping("/authorize/tree")
    public List<RoleMenuAuthorizeTreeVo> roleMenuAuthorizeTree(@RequestParam("roleId") Long roleId) {
        return roleMenuService.roleMenuAuthorizeTree(roleId);
    }

}