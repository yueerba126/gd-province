/**
 * @filename:GradedEnterpriseReviewController 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.grade.param.GradedEnterpriseReviewApproveParam;
import com.sydata.grade.param.GradedEnterpriseReviewPageParam;
import com.sydata.grade.param.GradedEnterpriseReviewSaveParam;
import com.sydata.grade.param.GradedEnterpriseStockExportParam;
import com.sydata.grade.service.IGradedEnterpriseReviewService;
import com.sydata.grade.vo.GradedEnterpriseReviewVo;
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
 * <p>说明： 等级粮库评定管理-企业申报审核API接口层</P>
 *
 * @version: V1.0
 * @author: lzq
 * @time 2023年05月22日
 */
@Api(tags = "等级粮库评定管理-企业申报审核", value = "等级粮库评定管理-企业申报审核")
@Validated
@RestController
@RequestMapping("/graded/gradedEnterpriseReview")
public class GradedEnterpriseReviewController {

    @Resource
    private IGradedEnterpriseReviewService gradedEnterpriseReviewService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GradedEnterpriseReviewVo> page(@RequestBody @Valid GradedEnterpriseReviewPageParam pageParam) {
        return gradedEnterpriseReviewService.pages(pageParam);
    }

    @ApiOperation("实地确认分页列表")
    @PostMapping("/sd/page")
    public Page<GradedEnterpriseReviewVo> sdPages(@RequestBody @Valid GradedEnterpriseReviewPageParam pageParam) {
        return gradedEnterpriseReviewService.sdPages(pageParam);
    }

    @ApiOperation("申报导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid GradedEnterpriseReviewPageParam pageParam) {
        gradedEnterpriseReviewService.exportData(pageParam);
    }

    @ApiOperation("实地导出")
    @PostMapping("/exportSd")
    public void exportSd(@RequestBody @Valid GradedEnterpriseReviewPageParam pageParam) {
        gradedEnterpriseReviewService.exportDataSd(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GradedEnterpriseReviewVo detail(@RequestParam("id") String id) {
        return gradedEnterpriseReviewService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid GradedEnterpriseReviewSaveParam param) {
        return gradedEnterpriseReviewService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid GradedEnterpriseReviewSaveParam param) {
        return gradedEnterpriseReviewService.updateData(param);
    }

    @ApiOperation("审批")
    @PostMapping("/approve")
    public Boolean approve(@RequestBody @Valid GradedEnterpriseReviewApproveParam param) {
        return gradedEnterpriseReviewService.approveData(param);
    }

    @ApiOperation("批量审批")
    @PostMapping("/batchApprove")
    public Boolean approve(@RequestBody @Valid List<GradedEnterpriseReviewApproveParam> params) {
        return gradedEnterpriseReviewService.batchApproveData(params);
    }

    @ApiOperation("授牌")
    @PostMapping("/awarding")
    public Boolean awarding(@RequestBody @Valid GradedEnterpriseReviewApproveParam param) {
        return gradedEnterpriseReviewService.awardingData(param);
    }

    @ApiOperation("实地确认")
    @PostMapping("/indeed")
    public Boolean indeed(@RequestBody @Valid GradedEnterpriseReviewApproveParam param) {
        return gradedEnterpriseReviewService.indeedData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return gradedEnterpriseReviewService.removeData(ids);
    }

}