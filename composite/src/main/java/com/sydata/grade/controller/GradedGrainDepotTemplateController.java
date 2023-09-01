/**
 * @filename:GradedGrainDepotTemplateController 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.grade.domain.GradedGrainDepotTemplate;
import com.sydata.grade.dto.XmDto;
import com.sydata.grade.param.FileStorageUploadParam;
import com.sydata.grade.param.GradedGrainDepotTemplatePageParam;
import com.sydata.grade.param.GradedGrainDepotTemplateSaveParam;
import com.sydata.grade.service.IGradedGrainDepotStandardService;
import com.sydata.grade.service.IGradedGrainDepotTemplateService;
import com.sydata.grade.vo.GradedGrainDepotTemplateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 等级粮库评定管理-等级粮库评定模板API接口层</P>
 *
 * @version: V1.0
 * @author: lzq
 * @time 2023年05月17日
 */
@Api(tags = "等级粮库评定管理-等级粮库评定模板", value = "等级粮库评定管理-等级粮库评定模板")
@Validated
@RestController
@RequestMapping("/graded/gradedGrainDepotTemplate")
public class GradedGrainDepotTemplateController {

    @Resource
    private IGradedGrainDepotTemplateService gradedGrainDepotTemplateService;

    @Resource
    private IGradedGrainDepotStandardService gradedGrainDepotStandardService;

    @ApiOperation("导入模板")
    @PostMapping(value = "/inside/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<XmDto> upload(@Valid FileStorageUploadParam file) {
        return gradedGrainDepotStandardService.uploadUse(file);
    }

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GradedGrainDepotTemplateVo> page(@RequestBody @Valid GradedGrainDepotTemplatePageParam pageParam) {
        return gradedGrainDepotTemplateService.pages(pageParam);
    }

    @ApiOperation("获取当前模板和指标信息")
    @PostMapping("/now/template")
    public GradedGrainDepotTemplate getNowYearGradedGrainDepotTemplate() {
        return gradedGrainDepotTemplateService.getNowYearGradedGrainDepotTemplate();
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GradedGrainDepotTemplateVo detail(@RequestParam("id") String id) {
        return gradedGrainDepotTemplateService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid GradedGrainDepotTemplateSaveParam param) {
        return gradedGrainDepotTemplateService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid GradedGrainDepotTemplateSaveParam param) {
        return gradedGrainDepotTemplateService.updateData(param);
    }

    @ApiOperation("发布")
    @PostMapping("/push")
    public Boolean push(@RequestParam("id") String id) {
        return gradedGrainDepotTemplateService.push(id);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return gradedGrainDepotTemplateService.removeData(ids);
    }

    @ApiOperation("导出模板")
    @PostMapping("/export")
    public void export(@RequestParam("id") String id) {
        gradedGrainDepotTemplateService.export(id);
    }

}