package com.sydata.safe.asess.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.safe.asess.param.OrgAssessReviewPageParam;
import com.sydata.safe.asess.param.OrgAssessReviewSubmitParam;
import com.sydata.safe.asess.service.IOrgAssessReviewIndexService;
import com.sydata.safe.asess.service.IOrgAssessReviewService;
import com.sydata.safe.asess.vo.OrgAssessReviewIndexTreeVo;
import com.sydata.safe.asess.vo.OrgAssessReviewVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lzq
 * @description 部门自评API
 * @date 2023/2/21 10:05
 */
@Api(tags = "粮食安全考核-部门自评API")
@Validated
@RestController
@RequestMapping("/safe/assess/org/assess/review")
public class OrgAssessReviewController {

    @Resource
    private IOrgAssessReviewService orgAssessReviewService;

    @Resource
    private IOrgAssessReviewIndexService orgAssessReviewIndexService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<OrgAssessReviewVo> page(@RequestBody OrgAssessReviewPageParam pageParam) {
        return orgAssessReviewService.page(pageParam);
    }

    @ApiOperation("指标树形结构")
    @PostMapping("/index/tree")
    public List<OrgAssessReviewIndexTreeVo> tree(@RequestParam("orgAssessDeptReviewId") String orgAssessDeptReviewId) {
        return orgAssessReviewIndexService.treeByAssessDeptReviewId(orgAssessDeptReviewId);
    }

    @ApiOperation("自评")
    @PostMapping("/submit")
    public Boolean submit(@RequestBody @Valid OrgAssessReviewSubmitParam submitParam) {
        return orgAssessReviewService.submit(submitParam);
    }

    @ApiOperation("退回")
    @PostMapping("/reset")
    public Boolean reset(@RequestParam("id") String id) {
        return orgAssessReviewService.reset(id);
    }

    @ApiOperation("操作权限")
    @PostMapping("/operation/auth")
    public Boolean operationAuth(@RequestParam("id") String id) {
        return orgAssessReviewService.operationAuth(id);
    }
}
