package com.sydata.dostrict.plan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.plan.param.ApparitorProjectPageParam;
import com.sydata.dostrict.plan.param.ApparitorProjectSaveParam;
import com.sydata.dostrict.plan.service.IApparitorProjectService;
import com.sydata.dostrict.plan.vo.ApparitorProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 规划建设-项目管理 Controller
 *
 * @author lzq
 * @date 2023-04-24
 */
@Api(tags = "规划建设-项目管理")
@Validated
@RestController
@RequestMapping("/apparitor/project")
public class ApparitorProjectController {
    @Resource
    private IApparitorProjectService apparitorProjectService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorProjectVo> page(@RequestBody @Valid ApparitorProjectPageParam pageParam) {
        return apparitorProjectService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorProjectVo detail(@RequestParam("id") String id) {
        return apparitorProjectService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorProjectSaveParam param) {
        return apparitorProjectService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorProjectSaveParam param) {
        return apparitorProjectService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorProjectService.removeData(ids);
    }
}
