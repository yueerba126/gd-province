package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.FilePageParam;
import com.sydata.basis.service.IFileService;
import com.sydata.basis.vo.FileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础信息-文件信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-文件信息")
@Validated
@RestController
@RequestMapping("/basis/file")
public class FileController {

    @Resource
    private IFileService fileService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<FileVo> page(@RequestBody @Valid FilePageParam pageParam) {
        return fileService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public FileVo detail(@RequestParam("id") String id) {
        return fileService.detail(id);
    }

    @ApiOperation("下载库区平面图")
    @PostMapping("/download/pmt")
    public void downloadPmt(@RequestParam("stockHouseId") String stockHouseId) {
        fileService.downloadPmt(stockHouseId);
    }
}
