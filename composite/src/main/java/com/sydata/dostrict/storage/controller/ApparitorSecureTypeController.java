/**
 * @filename:ApparitorSecureTypeBeanController 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.storage.param.ApparitorSecureTypePageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureTypeSaveParam;
import com.sydata.dostrict.storage.service.IApparitorSecureTypeService;
import com.sydata.dostrict.storage.vo.ApparitorSecureTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 安全仓储-安全生产-制度类别API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time    2023年04月27日
 *
 */
@Api(tags = "安全仓储-安全生产-制度类别",value = "安全仓储-安全生产-制度类别")
@Validated
@RestController
@RequestMapping("/apparitor/apparitorsecuretype")
public class ApparitorSecureTypeController {
    @Resource
    private IApparitorSecureTypeService apparitorSecureTypeService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorSecureTypeVo> page(@RequestBody @Valid ApparitorSecureTypePageParam pageParam) {
        return apparitorSecureTypeService.pages(pageParam);
    }

    @ApiOperation("查询列表")
    @PostMapping("/lists")
    public List<ApparitorSecureTypeVo> lists() {
        return apparitorSecureTypeService.lists();
    }

    @ApiOperation("查询树结构")
    @PostMapping("/tree")
    public List<KuNode> treeList() {
        return apparitorSecureTypeService.treeList();
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorSecureTypeVo detail(@RequestParam("id") String id) {
        return apparitorSecureTypeService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorSecureTypeSaveParam param) {
        return apparitorSecureTypeService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorSecureTypeSaveParam param) {
        return apparitorSecureTypeService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorSecureTypeService.removeData(ids);
    }
}