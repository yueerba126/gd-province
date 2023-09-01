package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.domain.Role;
import com.sydata.organize.param.RolePageParam;
import com.sydata.organize.param.RoleRemoveParam;
import com.sydata.organize.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * 组织架构-角色Controller
 *
 * @author lzq
 * @date 2022-06-28
 */
@Api(tags = "组织架构-角色")
@Validated
@RestController
@RequestMapping("/org/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<Role> page(@RequestBody @Valid RolePageParam pageParam) {
        return roleService.pages(pageParam);
    }

    @ApiOperation("根据ID查询")
    @PostMapping("/get/id")
    public Role getById(@RequestParam("id") Long id) {
        return roleService.getById(id);
    }

    @ApiOperation("根据名称查询")
    @PostMapping("/get/name")
    public Role getByName(@RequestParam("name") String name) {
        return roleService.getByName(name);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid Role role) {
        return roleService.save(role);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid Role role) {
        return roleService.updateById(role);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid RoleRemoveParam removeParam) {
        return roleService.remove(removeParam);
    }
}