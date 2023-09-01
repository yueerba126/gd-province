package com.sydata.reserve.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.manage.param.PlanManageAuditPageParam;
import com.sydata.reserve.manage.param.PlanManageAuditParam;
import com.sydata.reserve.manage.service.IPlanManageAuditService;
import com.sydata.reserve.manage.vo.PlanManageAuditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 轮换计划审核Controller
 *
 * @author fuql
 * @date 2023-05-31
 */
@Api(tags = "轮换计划审核")
@Validated
@RestController
@RequestMapping("/manage/audit")
public class PlanManageAuditController {

    @Resource
    private IPlanManageAuditService planManageAuditService;

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public Page<PlanManageAuditVo> list(@RequestBody @Valid PlanManageAuditPageParam param) {
        return planManageAuditService.pageData(param);
    }

    @ApiOperation("审核计划管理")
    @PostMapping("/audit")
    public Boolean audit(@RequestBody PlanManageAuditParam param) {
        return planManageAuditService.audit(param);
    }

}
