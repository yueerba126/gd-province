/**
 * @filename:GradedExpertManageController 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.grade.param.GradedEnterpriseStockExportParam;
import com.sydata.grade.param.GradedExpertManageExportParam;
import com.sydata.grade.service.IGradedExpertManageService;
import com.sydata.grade.param.GradedExpertManagePageParam;
import com.sydata.grade.param.GradedExpertManageSaveParam;
import com.sydata.grade.vo.GradedExpertManageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * <p>说明： 等级粮库评定管理-专家管理API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time    2023年05月25日
 *
 */
@Api(tags = "等级粮库评定管理-专家管理",value = "等级粮库评定管理-专家管理")
@Validated
@RestController
@RequestMapping("/graded/gradedExpertManage")
public class GradedExpertManageController {

     @Resource
     private IGradedExpertManageService gradedExpertManageService;

     @ApiOperation("分页列表")
     @PostMapping("/page")
     public Page<GradedExpertManageVo> page(@RequestBody @Valid GradedExpertManagePageParam pageParam) {
       return gradedExpertManageService.pagesMerge(pageParam);
     }

     @ApiOperation("查询详情")
     @PostMapping("/detail")
     public GradedExpertManageVo detail(@RequestParam("id") String id) {
       return gradedExpertManageService.detail(id);
     }

     @ApiOperation("新增")
     @PostMapping("/save")
     public String save(@RequestBody @Valid GradedExpertManageSaveParam param) {
       return gradedExpertManageService.saveData(param);
     }

     @ApiOperation("修改")
     @PostMapping("/update")
     public String update(@RequestBody @Valid GradedExpertManageSaveParam param) {
       return gradedExpertManageService.updateData(param);
     }

     @ApiOperation("导入")
     @PostMapping("/import")
     public void importData(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
          gradedExpertManageService.importData(file);
     }

     @ApiOperation("导出")
     @PostMapping("/export")
     public void export(@RequestBody @Valid GradedExpertManagePageParam pageParam) {
          gradedExpertManageService.exportData(pageParam);
     }

     @ApiOperation("删除")
     @PostMapping("/remove")
     public Boolean remove(@RequestBody List<String> ids) {
       return gradedExpertManageService.removeData(ids);
     }

}