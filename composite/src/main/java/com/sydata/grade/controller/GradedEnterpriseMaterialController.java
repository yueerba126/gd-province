/**
 * @filename:GradedEnterpriseMaterialController 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.core.global.WebResult;
import com.sydata.grade.param.GradedEnterpriseMaterialPageParam;
import com.sydata.grade.param.GradedEnterpriseMaterialSaveParam;
import com.sydata.grade.service.IGradedEnterpriseMaterialService;
import com.sydata.grade.vo.GradedEnterpriseMaterialVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.InputStream;
import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 等级粮库评定管理-企业申报证明材料API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time 2023年05月22日
 *
 */
@Api(tags = "等级粮库评定管理-企业申报证明材料", value = "等级粮库评定管理-企业申报证明材料")
@Validated
@RestController
@RequestMapping("/graded/gradedEnterpriseMaterial")
public class GradedEnterpriseMaterialController {

    @Resource
    private IGradedEnterpriseMaterialService gradedEnterpriseMaterialService;

    @ApiOperation("上传证明材料")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@Valid MultipartFile file) {
        return gradedEnterpriseMaterialService.uploadUse(file);
    }

    @ApiOperation("下载证明材料")
    @PostMapping("/download")
    public void download(@RequestParam("fileStorageId") String fileStorageId) {
        gradedEnterpriseMaterialService.download(fileStorageId);
    }

    @ApiOperation("批量下载证明材料,id用逗号隔开")
    @PostMapping("/downloadToZip")
    public void downloadToZip(@RequestParam("fileStorageIds") String fileStorageIds) {
        gradedEnterpriseMaterialService.downloadToZip(fileStorageIds);
    }

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GradedEnterpriseMaterialVo> page(@RequestBody @Valid GradedEnterpriseMaterialPageParam pageParam) {
        return gradedEnterpriseMaterialService.pages(pageParam);
    }

    @ApiOperation("列表")
    @PostMapping("/list")
    public List<GradedEnterpriseMaterialVo> list(@RequestParam("qyid") String qyid) {
        return gradedEnterpriseMaterialService.list(qyid);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GradedEnterpriseMaterialVo detail(@RequestParam("id") String id) {
        return gradedEnterpriseMaterialService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid GradedEnterpriseMaterialSaveParam param) {
        return gradedEnterpriseMaterialService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid GradedEnterpriseMaterialSaveParam param) {
        return gradedEnterpriseMaterialService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return gradedEnterpriseMaterialService.removeData(ids);
    }

}