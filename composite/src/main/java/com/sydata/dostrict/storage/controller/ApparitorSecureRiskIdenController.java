/**
 * @filename:ApparitorSecureRiskIdenBeanController 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskIdenPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskIdenSaveParam;
import com.sydata.dostrict.storage.service.IApparitorSecureRiskIdenService;
import com.sydata.dostrict.storage.vo.ApparitorSecureRiskIdenVo;
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
 * <p>说明： 安全仓储-风险智能识别API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time    2023年04月27日
 *
 */
@Api(tags = "安全仓储-风险智能识别",value = "安全仓储-风险智能识别")
@Validated
@RestController
@RequestMapping("/apparitor/apparitorsecureriskiden")
public class ApparitorSecureRiskIdenController {
    @Resource
    private IApparitorSecureRiskIdenService apparitorSecureRiskIdenService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorSecureRiskIdenVo> page(@RequestBody @Valid ApparitorSecureRiskIdenPageParam pageParam) {
        return apparitorSecureRiskIdenService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorSecureRiskIdenVo detail(@RequestParam("id") String id) {
        return apparitorSecureRiskIdenService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorSecureRiskIdenSaveParam param) {
        return apparitorSecureRiskIdenService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorSecureRiskIdenSaveParam param) {
        return apparitorSecureRiskIdenService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorSecureRiskIdenService.removeData(ids);
    }
}