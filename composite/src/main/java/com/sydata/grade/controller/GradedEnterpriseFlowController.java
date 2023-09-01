/**
 * @filename:GradedEnterpriseFlowController 2023年05月24日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.grade.domain.GradedEnterpriseFlow;
import com.sydata.grade.param.GradedEnterpriseFlowInitParam;
import com.sydata.grade.service.IGradedEnterpriseFlowService;
import com.sydata.grade.param.GradedEnterpriseFlowPageParam;
import com.sydata.grade.param.GradedEnterpriseFlowSaveParam;
import com.sydata.grade.vo.GradedEnterpriseFlowVo;
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
 * <p>说明： 等级粮库评定管理-企业申报流程表API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time    2023年05月24日
 *
 */
@Api(tags = "等级粮库评定管理-企业申报流程表",value = "等级粮库评定管理-企业申报流程表")
@Validated
@RestController
@RequestMapping("/graded/gradedEnterpriseFlow")
public class GradedEnterpriseFlowController {

     @Resource
     private IGradedEnterpriseFlowService gradedEnterpriseFlowService;

     @ApiOperation("分页列表")
     @PostMapping("/page")
     public Page<GradedEnterpriseFlowVo> page(@RequestBody @Valid GradedEnterpriseFlowPageParam pageParam) {
       return gradedEnterpriseFlowService.pages(pageParam);
     }

     @ApiOperation("列表")
     @PostMapping("/list")
     public List<GradedEnterpriseFlowVo> list(@RequestParam("qyid") String qyid) {
          return gradedEnterpriseFlowService.list(qyid);
     }

     @ApiOperation("查询详情")
     @PostMapping("/detail")
     public GradedEnterpriseFlowVo detail(@RequestParam("id") String id) {
       return gradedEnterpriseFlowService.detail(id);
     }

     @ApiOperation("初始化")
     @PostMapping("/initData")
     public Boolean initData(@RequestBody @Valid GradedEnterpriseFlowInitParam param) {
          return gradedEnterpriseFlowService.initData(param);
     }

     @ApiOperation("获取初始化流程信息")
     @PostMapping("/get/init")
     public List<GradedEnterpriseFlow> getInitData(@RequestBody @Valid GradedEnterpriseFlowInitParam param) {
          return gradedEnterpriseFlowService.getInitData(param);
     }

     @ApiOperation("新增")
     @PostMapping("/save")
     public String save(@RequestBody @Valid GradedEnterpriseFlowSaveParam param) {
       return gradedEnterpriseFlowService.saveData(param);
     }

     @ApiOperation("修改")
     @PostMapping("/update")
     public String update(@RequestBody @Valid GradedEnterpriseFlowSaveParam param) {
       return gradedEnterpriseFlowService.updateData(param);
     }

     @ApiOperation("删除")
     @PostMapping("/remove")
     public Boolean remove(@RequestBody List<String> ids) {
       return gradedEnterpriseFlowService.removeData(ids);
     }

}