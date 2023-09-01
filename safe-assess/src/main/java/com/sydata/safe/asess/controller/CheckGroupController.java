package com.sydata.safe.asess.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.safe.asess.param.CheckGroupPageParam;
import com.sydata.safe.asess.param.CheckGroupSaveParam;
import com.sydata.safe.asess.param.CheckGroupUpdateParam;
import com.sydata.safe.asess.service.ICheckGroupService;
import com.sydata.safe.asess.vo.CheckGroupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 粮安-实地抽查小组管理Controller
 *
 * @author system
 * @date 2023-02-10
 */
@Api(tags = "粮食安全考核-实地抽查小组管理")
@Validated
@RestController
@RequestMapping("/safe/assess/check/group")
public class CheckGroupController {

    @Resource
    private ICheckGroupService checkGroupService;

    @ApiOperation("查询列表")
    @PostMapping("/page")
    public Page<CheckGroupVo> page(@RequestBody CheckGroupPageParam pageParam) {
        return checkGroupService.pages(pageParam);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/detail")
    public CheckGroupVo detail(@RequestParam("id") String id) {
        return checkGroupService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid CheckGroupSaveParam checkGroupSaveParam) {
        return checkGroupService.save(checkGroupSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid CheckGroupUpdateParam checkGroupUpdateParam) {
        return checkGroupService.update(checkGroupUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return checkGroupService.removeById(id);
    }

    @ApiOperation("操作权限")
    @PostMapping("/operation/auth")
    public Boolean operationAuth(@RequestParam("id") String id) {
        return checkGroupService.operationAuth(id);
    }
}