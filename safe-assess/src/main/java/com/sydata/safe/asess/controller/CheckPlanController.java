package com.sydata.safe.asess.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.service.ICheckPlanDtlService;
import com.sydata.safe.asess.service.ICheckPlanService;
import com.sydata.safe.asess.vo.CheckPlanDtlVo;
import com.sydata.safe.asess.vo.CheckPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 粮安-实地抽查人员管理Controller
 *
 * @author system
 * @date 2023-02-10
 */
@Api(tags = "粮食安全考核-抽查计划")
@Validated
@RestController
@RequestMapping("/safe/assess/check/plan")
public class CheckPlanController {

    @Resource
    private ICheckPlanService checkPlanService;

    @Resource
    private ICheckPlanDtlService checkPlanDtlService;

    @ApiOperation("列表")
    @PostMapping("/page")
    public Page<CheckPlanVo> page(@RequestBody CheckPlanPageParam pageParam) {
        return checkPlanService.pages(pageParam);
    }

    @ApiOperation("抽查计划明细")
    @PostMapping("/dtls")
    public List<CheckPlanDtlVo> listByPlanId(@RequestParam("planId") String planId) {
        return checkPlanDtlService.listByPlanId(planId);
    }

    @ApiOperation("根据单位考核ID列表查询单位考核ID列表")
    @PostMapping("/org/assess/ids")
    public List<String> orgAssessIds(@RequestBody List<String> orgAssessIds) {
        return checkPlanDtlService.orgAssessIds(orgAssessIds);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid CheckPlanSaveParam saveParam) {
        return checkPlanService.save(saveParam);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid CheckPlanUpdateParam updateParam) {
        return checkPlanService.update(updateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return checkPlanService.removeById(id);
    }

    @ApiOperation("发布")
    @PostMapping("/push")
    public Boolean push(@RequestParam("id") String id) {
        return checkPlanService.push(id);
    }

    @ApiOperation("查看当前登录人是否有抽查计划的权限")
    @PostMapping("/check/auth")
    public Boolean checkAuth(@RequestParam("id") String id) {
        return checkPlanService.checkAuth(id);
    }

    @ApiOperation("抽查")
    @PostMapping("/check")
    public Boolean check(@RequestBody @Valid CheckPlanCheckParam checkParam) {
        return checkPlanService.check(checkParam);
    }

    @ApiOperation("撤回")
    @PostMapping("/revoke")
    public Boolean revoke(@RequestParam("planId") String planId, @RequestParam("dtlId") String dtlId) {
        return checkPlanService.revoke(planId, dtlId);
    }

    @ApiOperation("操作权限")
    @PostMapping("/operation/auth")
    public Boolean operationAuth(@RequestParam("id") String id) {
        return checkPlanService.operationAuth(id);
    }

    @ApiOperation("设置抽查附件ID")
    @PostMapping("/upload/check")
    public Boolean uploadCheckFiles(@RequestBody @Valid CheckPlanUploadFileParam uploadFileParam) {
        return checkPlanService.uploadCheckFiles(uploadFileParam);
    }
}
