package com.sydata.safe.asess.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.service.ITemplateService;
import com.sydata.safe.asess.vo.TemplateAllotDeptVo;
import com.sydata.safe.asess.vo.TemplateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lzq
 * @description 粮食安全考核-考核模板API
 * @date 2023/2/13 15:11
 */
@Api(tags = "粮食安全考核-考核模板API")
@Validated
@RestController
@RequestMapping("/safe/assess/template")
public class TemplateController {

    @Resource
    private ITemplateService templateService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<TemplateVo> page(@RequestBody TemplatePageParam pageParam) {
        return templateService.page(pageParam);
    }

    @ApiOperation("生成编号")
    @PostMapping("/generate/number")
    public String generateNumber() {
        return templateService.generateNumber();
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid TemplateSaveParam saveParam) {
        return templateService.add(saveParam);
    }

    @ApiOperation("复制新增")
    @PostMapping("/copy/save")
    public Boolean copySave(@RequestBody @Valid TemplateCopySaveParam copySaveParam) {
        return templateService.copyAdd(copySaveParam);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid TemplateUpdateParam updateParam) {
        return templateService.update(updateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return templateService.removeById(id);
    }

    @ApiOperation("获取模板分配部门列表")
    @PostMapping("/allot/dept")
    public List<TemplateAllotDeptVo> allotDept(@RequestParam("id") String id) {
        return templateService.allotDept(id);
    }

    @ApiOperation("分配")
    @PostMapping("/allot")
    public Boolean allot(@RequestParam("id") String id) {
        return templateService.allot(id);
    }

    @ApiOperation("发布")
    @PostMapping("/push")
    public Boolean push(@RequestBody @Valid TemplatePushParam pushParam) {
        return templateService.push(pushParam);
    }

    @ApiOperation("撤回")
    @PostMapping("/revoke")
    public Boolean revoke(@RequestParam("id") String id) {
        return templateService.revoke(id);
    }

    @ApiOperation("操作权限")
    @PostMapping("/operation/auth")
    public Boolean operationAuth(@RequestParam("id") String id) {
        return templateService.operationAuth(id);
    }
}
