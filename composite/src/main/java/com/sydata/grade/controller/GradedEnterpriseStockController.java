/**
 * @filename:GradedEnterpriseStockController 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.grade.domain.GradedEnterpriseStock;
import com.sydata.grade.param.*;
import com.sydata.grade.service.IGradedEnterpriseStockService;
import com.sydata.grade.vo.GradedEnterpriseStockVo;
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
 * <p>说明： 等级粮库评定管理-企业等级库点API接口层</P>
 * @version: V1.0
 * @author: lzq
 * @time    2023年05月25日
 *
 */
@Api(tags = "等级粮库评定管理-企业等级库点",value = "等级粮库评定管理-企业等级库点")
@Validated
@RestController
@RequestMapping("/graded/gradedEnterpriseStock")
public class GradedEnterpriseStockController {

     @Resource
     private IGradedEnterpriseStockService gradedEnterpriseStockService;

     @ApiOperation("全省A级粮库分页列表")
     @PostMapping("/page")
     public Page<GradedEnterpriseStockVo> page(@RequestBody @Valid GradedEnterpriseStockPageParam pageParam) {
       return gradedEnterpriseStockService.pages(pageParam);
     }

     @ApiOperation("AA及以下粮库分页列表")
     @PostMapping("/page/two")
     public Page<GradedEnterpriseStockVo> pagesTwo(@RequestBody @Valid GradedEnterpriseStockPageParam pageParam) {
          return gradedEnterpriseStockService.pagesTwo(pageParam);
     }

     @ApiOperation("AAA粮库分页列表")
     @PostMapping("/page/three")
     public Page<GradedEnterpriseStockVo> pagesThree(@RequestBody @Valid GradedEnterpriseStockPageParam pageParam) {
          return gradedEnterpriseStockService.pagesThree(pageParam);
     }

     @ApiOperation("导出3A以上的")
     @PostMapping("/export3A")
     public void export3A(@RequestBody @Valid GradedEnterpriseStockPageParam pageParam) {
          gradedEnterpriseStockService.exportData3A(pageParam);
     }

     @ApiOperation("导出2A以下的")
     @PostMapping("/export2A")
     public void export2A(@RequestBody @Valid GradedEnterpriseStockPageParam pageParam) {
          gradedEnterpriseStockService.exportData2A(pageParam);
     }

     @ApiOperation("导出全部的")
     @PostMapping("/exportAll")
     public void exportAll(@RequestBody @Valid GradedEnterpriseStockPageParam pageParam) {
          gradedEnterpriseStockService.exportDataAll(pageParam);
     }

     @ApiOperation("查询详情")
     @PostMapping("/detail")
     public GradedEnterpriseStockVo detail(@RequestParam("id") String id) {
       return gradedEnterpriseStockService.detail(id);
     }

     @ApiOperation("新增")
     @PostMapping("/save")
     public String save(@RequestBody @Valid GradedEnterpriseStockSaveParam param) {
       return gradedEnterpriseStockService.saveData(param);
     }

     @ApiOperation("修改")
     @PostMapping("/update")
     public String update(@RequestBody @Valid GradedEnterpriseStockSaveParam param) {
       return gradedEnterpriseStockService.updateData(param);
     }

     @ApiOperation("摘除")
     @PostMapping("/pick")
     public Boolean pickRankData(@RequestBody GradedEnterpriseStockParam param) {
          return gradedEnterpriseStockService.pickRankData(param);
     }

     @ApiOperation("降级")
     @PostMapping("/reduce")
     public Boolean reduceRankData(@RequestBody GradedEnterpriseStockParam param) {
          return gradedEnterpriseStockService.reduceRankData(param);
     }

     @ApiOperation("获取等级粮库信息")
     @PostMapping("/getunx")
     public GradedEnterpriseStock getByUnx(@RequestParam("qydm") String qydm, @RequestParam("kqdm") String kqdm) {
          return gradedEnterpriseStockService.getByUnx(qydm,kqdm);
     }

     @ApiOperation("删除")
     @PostMapping("/remove")
     public Boolean remove(@RequestBody List<String> ids) {
       return gradedEnterpriseStockService.removeData(ids);
     }

}