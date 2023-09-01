/**
 * @filename:GradedGrainDepotStandardController 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.common.file.param.FileStorageUploadParam;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.grade.service.IGradedGrainDepotStandardService;
import com.sydata.grade.param.GradedGrainDepotStandardPageParam;
import com.sydata.grade.param.GradedGrainDepotStandardSaveParam;
import com.sydata.grade.vo.GradedGrainDepotStandardTreeVo;
import com.sydata.grade.vo.GradedGrainDepotStandardVo;
import com.sydata.organize.security.UserSecurity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 等级粮库评定管理-等级粮库评定标准API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time 2023年05月17日
 *
 */
@Api(tags = "等级粮库评定管理-等级粮库评定标准", value = "等级粮库评定管理-等级粮库评定标准")
@Validated
@RestController
@RequestMapping("/graded/standard")
public class GradedGrainDepotStandardController {

    @Resource
    private IGradedGrainDepotStandardService gradedGrainDepotStandardService;

    @ApiOperation("等级粮库评定标准树形结构")
    @PostMapping("/tree")
    public List<GradedGrainDepotStandardTreeVo> tree(@RequestParam("templateId") String templateId) {
        return gradedGrainDepotStandardService.treeByTemplateId(templateId);
    }

    @ApiOperation("等级粮库评定标准List")
    @PostMapping("/list")
    public List<GradedGrainDepotStandardVo> listByTemplateId(@RequestParam("templateId") String templateId) {
        return gradedGrainDepotStandardService.listByTemplateId(templateId);
    }

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GradedGrainDepotStandardVo> page(@RequestBody @Valid GradedGrainDepotStandardPageParam pageParam) {
        return gradedGrainDepotStandardService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GradedGrainDepotStandardVo detail(@RequestParam("id") String id) {
        return gradedGrainDepotStandardService.detail(id);
    }

    @ApiOperation("暂存")
    @PostMapping("/save")
    public String save(@RequestBody @Valid GradedGrainDepotStandardSaveParam param) {
        return gradedGrainDepotStandardService.saveData(param);
    }

    @ApiOperation("提交")
    @PostMapping("/submit")
    public Boolean submit(@RequestBody @Valid GradedGrainDepotStandardSaveParam param) {
        return gradedGrainDepotStandardService.submitData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid GradedGrainDepotStandardSaveParam param) {
        return gradedGrainDepotStandardService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return gradedGrainDepotStandardService.removeData(id);
    }
}