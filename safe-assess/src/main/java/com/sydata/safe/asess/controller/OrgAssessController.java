package com.sydata.safe.asess.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.safe.asess.domain.OrgAssessIndex;
import com.sydata.safe.asess.param.OrgAssessAllotParam;
import com.sydata.safe.asess.param.OrgAssessExportParam;
import com.sydata.safe.asess.param.OrgAssessPageParam;
import com.sydata.safe.asess.param.OrgAssessUploadFileParam;
import com.sydata.safe.asess.service.IOrgAssessIndexService;
import com.sydata.safe.asess.service.IOrgAssessService;
import com.sydata.safe.asess.vo.OrgAssessIndexTreeVo;
import com.sydata.safe.asess.vo.OrgAssessVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author lzq
 * @description 粮食安全考核-单位考核API
 * @date 2023/2/13 15:11
 */
@Api(tags = "粮食安全考核-单位考核API")
@Validated
@RestController
@RequestMapping("/safe/assess/org/assess")
public class OrgAssessController {

    @Resource
    private IOrgAssessService orgAssessService;

    @Resource
    private IOrgAssessIndexService orgAssessIndexService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<OrgAssessVo> page(@RequestBody OrgAssessPageParam pageParam) {
        return orgAssessService.page(pageParam);
    }

    @ApiOperation("详情")
    @PostMapping("/details")
    public OrgAssessVo details(@RequestParam("id") String id) {
        return orgAssessService.details(id);
    }

    @ApiOperation("指标树形结构")
    @PostMapping("/index/tree")
    public List<OrgAssessIndexTreeVo> tree(@RequestParam("orgAssessId") String orgAssessId) {
        return orgAssessIndexService.treeByOrgAssessId(orgAssessId);
    }

    @ApiOperation("批量获取指标树形结构")
    @PostMapping("/index/tree/map")
    public Map<String, List<OrgAssessIndexTreeVo>> treeMap(@RequestBody @NotEmpty(message = "单位考核ID列表不能为空")
                                                           @Valid List<String> orgAssessIds) {
        return orgAssessIndexService.treeMapByOrgAssessIds(orgAssessIds);
    }

    @ApiOperation("指标map结构")
    @PostMapping("/index/map")
    public Map<String, OrgAssessIndex> map(@RequestParam("orgAssessId") String orgAssessId) {
        return orgAssessIndexService.mapByOrgAssessId(orgAssessId);
    }

    @ApiOperation("考核模板指标idMap结构")
    @PostMapping("/template/index/map")
    public Map<String, OrgAssessIndex> templateIndexMap(@RequestParam("orgAssessId") String orgAssessId) {
        return orgAssessIndexService.templateIndexMapByOrgAssessId(orgAssessId);
    }

    @ApiOperation("分配")
    @PostMapping("/allot")
    public Boolean allot(@RequestBody @Valid OrgAssessAllotParam allotParam) {
        return orgAssessService.allot(allotParam);
    }

    @ApiOperation("设置附件ID")
    @PostMapping("/upload")
    public Boolean uploadFiles(@RequestBody @Valid OrgAssessUploadFileParam uploadFileParam) {
        return orgAssessService.uploadFiles(uploadFileParam);
    }

    @ApiOperation("提交")
    @PostMapping("/submit")
    public Boolean submit(@RequestParam("id") String id) {
        return orgAssessService.submit(id);
    }

    @ApiOperation("退回")
    @PostMapping("/reset")
    public Boolean reset(@RequestParam("id") String id) {
        return orgAssessService.reset(id);
    }

    @ApiOperation("撤回")
    @PostMapping("/revoke")
    public Boolean revoke(@RequestParam("id") String id) {
        return orgAssessService.revoke(id);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid OrgAssessExportParam exportParam) {
        orgAssessService.export(exportParam);
    }

    @ApiOperation("操作权限")
    @PostMapping("/operation/auth")
    public Boolean operationAuth(@RequestParam("id") String id) {
        return orgAssessService.operationAuth(id);
    }
}