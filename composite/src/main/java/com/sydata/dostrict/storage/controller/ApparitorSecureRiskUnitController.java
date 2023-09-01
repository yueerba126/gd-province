/**
 * @filename:ApparitorSecureRiskUnitBeanController 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskUnitPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskUnitSaveParam;
import com.sydata.dostrict.storage.service.IApparitorSecureRiskUnitService;
import com.sydata.dostrict.storage.vo.ApparitorSecureRiskUnitVo;
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
 * <p>说明： 安全仓储-安全风险台账API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time    2023年04月27日
 *
 */
@Api(tags = "安全仓储-安全风险台账",value = "安全仓储-安全风险台账")
@Validated
@RestController
@RequestMapping("/apparitor/apparitorsecureriskunit")
public class ApparitorSecureRiskUnitController {
    @Resource
    private IApparitorSecureRiskUnitService apparitorSecureRiskUnitService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorSecureRiskUnitVo> page(@RequestBody @Valid ApparitorSecureRiskUnitPageParam pageParam) {
        return apparitorSecureRiskUnitService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorSecureRiskUnitVo detail(@RequestParam("id") String id) {
        return apparitorSecureRiskUnitService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorSecureRiskUnitSaveParam param) {
        return apparitorSecureRiskUnitService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorSecureRiskUnitSaveParam param) {
        return apparitorSecureRiskUnitService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorSecureRiskUnitService.removeData(ids);
    }
}