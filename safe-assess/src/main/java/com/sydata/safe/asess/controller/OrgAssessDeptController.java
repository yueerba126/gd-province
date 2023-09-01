package com.sydata.safe.asess.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
import com.sydata.safe.asess.param.OrgAssessDeptAllotParam;
import com.sydata.safe.asess.param.OrgAssessDeptPageParam;
import com.sydata.safe.asess.param.OrgAssessDeptSubmitParam;
import com.sydata.safe.asess.service.IOrgAssessDeptIndexService;
import com.sydata.safe.asess.service.IOrgAssessDeptService;
import com.sydata.safe.asess.vo.OrgAssessDeptIndexTreeVo;
import com.sydata.safe.asess.vo.OrgAssessDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author lzq
 * @description 粮食安全考核-部门考核API
 * @date 2023/2/20 10:01
 */
@Api(tags = "粮食安全考核-部门考核API")
@Validated
@RestController
@RequestMapping("/safe/assess/org/assess/dept")
public class OrgAssessDeptController {

    @Resource
    private IOrgAssessDeptService orgAssessDeptService;

    @Resource
    private IOrgAssessDeptIndexService orgAssessDeptIndexService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<OrgAssessDeptVo> page(@RequestBody OrgAssessDeptPageParam pageParam) {
        return orgAssessDeptService.page(pageParam);
    }

    @ApiOperation("指标树形结构")
    @PostMapping("/index/tree")
    public List<OrgAssessDeptIndexTreeVo> tree(@RequestParam("orgAssessDeptId") String orgAssessDeptId) {
        return orgAssessDeptIndexService.treeByAssessDeptId(orgAssessDeptId);
    }

    @ApiOperation("分配")
    @PostMapping("/allot")
    public Boolean allot(@RequestBody @Valid OrgAssessDeptAllotParam allotParam) {
        return orgAssessDeptService.allot(allotParam);
    }

    @ApiOperation("评分")
    @PostMapping("/submit")
    public Boolean submit(@RequestBody @Valid OrgAssessDeptSubmitParam submitParam) {
        return orgAssessDeptService.submit(submitParam);
    }

    @ApiOperation("退回")
    @PostMapping("/reset")
    public Boolean reset(@RequestParam("id") String id) {
        return orgAssessDeptService.reset(id);
    }

    @ApiOperation("撤回")
    @PostMapping("/revoke")
    public Boolean revoke(@RequestParam("id") String id) {
        return orgAssessDeptService.revoke(id);
    }

    @ApiOperation("根据单位考核ID查询部门考核映射map<部门ID,map<单位考核指标ID,部门考核指标>>")
    @PostMapping("/index/map")
    public Map<Long, Map<String, OrgAssessDeptIndex>> indexMap(@RequestParam("orgAssessId") String orgAssessId) {
        return orgAssessDeptService.indexMap(orgAssessId);
    }

    @ApiOperation("操作权限")
    @PostMapping("/operation/auth")
    public Boolean operationAuth(@RequestParam("id") String id) {
        return orgAssessDeptService.operationAuth(id);
    }
}
