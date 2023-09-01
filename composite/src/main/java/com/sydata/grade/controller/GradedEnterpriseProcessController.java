/**
 * @filename:GradedEnterpriseProcessController 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.grade.param.GradedEnterpriseProcessPageParam;
import com.sydata.grade.param.GradedEnterpriseProcessSaveParam;
import com.sydata.grade.service.IGradedEnterpriseProcessService;
import com.sydata.grade.vo.GradedEnterpriseProcessVo;
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
 * <p>说明： 等级粮库评定管理-企业申报审核详情API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time 2023年05月22日
 *
 */
@Api(tags = "等级粮库评定管理-企业申报审核详情", value = "等级粮库评定管理-企业申报审核详情")
@Validated
@RestController
@RequestMapping("/graded/gradedEnterpriseProcess")
public class GradedEnterpriseProcessController {

    @Resource
    private IGradedEnterpriseProcessService gradedEnterpriseProcessService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GradedEnterpriseProcessVo> page(@RequestBody @Valid GradedEnterpriseProcessPageParam pageParam) {
        return gradedEnterpriseProcessService.pages(pageParam);
    }

    @ApiOperation("列表")
    @PostMapping("/list")
    public List<GradedEnterpriseProcessVo> list(@RequestParam("qyid") String qyid) {
        return gradedEnterpriseProcessService.list(qyid);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GradedEnterpriseProcessVo detail(@RequestParam("id") String id) {
        return gradedEnterpriseProcessService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid GradedEnterpriseProcessSaveParam param) {
        return gradedEnterpriseProcessService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid GradedEnterpriseProcessSaveParam param) {
        return gradedEnterpriseProcessService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return gradedEnterpriseProcessService.removeData(ids);
    }

}