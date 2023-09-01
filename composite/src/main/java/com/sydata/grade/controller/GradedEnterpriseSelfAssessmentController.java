/**
 * @filename:GradedEnterpriseSelfAssessmentController 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.grade.dto.XmDto;
import com.sydata.grade.service.IGradedEnterpriseSelfAssessmentService;
import com.sydata.grade.param.GradedEnterpriseSelfAssessmentPageParam;
import com.sydata.grade.param.GradedEnterpriseSelfAssessmentSaveParam;
import com.sydata.grade.vo.GradedEnterpriseSelfAssessmentVo;
import com.sydata.grade.vo.GradedReturnSelfAssessmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import io.swagger.annotations.Api;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 等级粮库评定管理-企业申报自评表API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time 2023年05月22日
 *
 */
@Api(tags = "等级粮库评定管理-企业申报自评表", value = "等级粮库评定管理-企业申报自评表")
@Validated
@RestController
@RequestMapping("/graded/gradedEnterpriseSelfAssessment")
public class GradedEnterpriseSelfAssessmentController {

    @Resource
    private IGradedEnterpriseSelfAssessmentService gradedEnterpriseSelfAssessmentService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GradedEnterpriseSelfAssessmentVo> page(@RequestBody @Valid GradedEnterpriseSelfAssessmentPageParam pageParam) {
        return gradedEnterpriseSelfAssessmentService.pages(pageParam);
    }

    @ApiOperation("列表")
    @PostMapping("/list")
    public List<XmDto> list(@RequestParam("qyid") String qyid) {
        return gradedEnterpriseSelfAssessmentService.listDto(qyid);
    }

    @ApiOperation("获取实地评分的参数")
    @PostMapping("/return")
    public List<GradedReturnSelfAssessmentVo> list(@RequestParam("qyid") String qyid, @RequestParam("sdpf") Boolean sdpf) {
        return gradedEnterpriseSelfAssessmentService.getGradedReturnSelfAssessmentList(qyid,sdpf);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GradedEnterpriseSelfAssessmentVo detail(@RequestParam("id") String id) {
        return gradedEnterpriseSelfAssessmentService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid GradedEnterpriseSelfAssessmentSaveParam param) {
        return gradedEnterpriseSelfAssessmentService.saveData(param);
    }

    @ApiOperation("批量新增或修改")
    @PostMapping("/saveOrUpdateBatchData")
    public Boolean saveOrUpdateBatchData(@RequestBody @Valid List<GradedEnterpriseSelfAssessmentSaveParam> param) {
        return gradedEnterpriseSelfAssessmentService.saveOrUpdateBatchData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid GradedEnterpriseSelfAssessmentSaveParam param) {
        return gradedEnterpriseSelfAssessmentService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return gradedEnterpriseSelfAssessmentService.removeData(ids);
    }

}