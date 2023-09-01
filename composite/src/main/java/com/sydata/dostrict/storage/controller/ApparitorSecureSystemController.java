/**
 * @filename:ApparitorSecureSystemBeanController 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.storage.param.ApparitorSecureSystemPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureSystemSaveParam;
import com.sydata.dostrict.storage.service.IApparitorSecureSystemService;
import com.sydata.dostrict.storage.vo.ApparitorSecureSystemVo;
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
 * <p>说明： 安全仓储-安全生产-生产制度API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time    2023年04月27日
 *
 */
@Api(tags = "安全仓储-安全生产-生产制度",value = "安全仓储-安全生产-生产制度")
@Validated
@RestController
@RequestMapping("/apparitor/apparitorsecuresystem")
public class ApparitorSecureSystemController {
    @Resource
    private IApparitorSecureSystemService apparitorSecureSystemService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorSecureSystemVo> page(@RequestBody @Valid ApparitorSecureSystemPageParam pageParam) {
        return apparitorSecureSystemService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorSecureSystemVo detail(@RequestParam("id") String id) {
        return apparitorSecureSystemService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorSecureSystemSaveParam param) {
        return apparitorSecureSystemService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorSecureSystemSaveParam param) {
        return apparitorSecureSystemService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorSecureSystemService.removeData(ids);
    }

    @ApiOperation("发布")
    @PostMapping(value = "/push")
    public boolean push(@RequestParam("id") String id) {
        return apparitorSecureSystemService.pushDataById(id);
    }

    @ApiOperation("下载附件")
    @PostMapping("/download")
    public void remove(@RequestParam("fileId") String fileId) {
        apparitorSecureSystemService.download(fileId);
    }
}