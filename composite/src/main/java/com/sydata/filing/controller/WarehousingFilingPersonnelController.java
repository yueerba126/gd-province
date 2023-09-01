package com.sydata.filing.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.filing.service.IWarehousingFilingPersonnelService;
import com.sydata.filing.param.WarehousingFilingPersonnelPageParam;
import com.sydata.filing.param.WarehousingFilingPersonnelSaveParam;
import com.sydata.filing.vo.WarehousingFilingPersonnelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.sydata.filing.vo.FileVo;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:TODO(仓储备案-仓储人员API接口层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@Api(tags = "仓储备案-仓储人员",value = "仓储备案-仓储人员")
@Validated
@RestController
@RequestMapping("/filing/warehousingFilingPersonnel")
public class WarehousingFilingPersonnelController {

    @Resource
    private IWarehousingFilingPersonnelService warehousingFilingPersonnelService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<WarehousingFilingPersonnelVo> page(@RequestBody @Valid WarehousingFilingPersonnelPageParam pageParam) {
        return warehousingFilingPersonnelService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public WarehousingFilingPersonnelVo detail(@RequestParam("id") String id) {
        return warehousingFilingPersonnelService.detail(id);
    }

    @ApiOperation("根据主表ID，查询从表列表")
    @PostMapping("/listByMainId")
    public List<WarehousingFilingPersonnelVo> listByMainId(@RequestParam("companyId") String companyId) {
        return warehousingFilingPersonnelService.listByMainId(companyId);
    }

    @ApiOperation("根据主表ID，删除从表")
    @PostMapping("/deleteByMainId")
    public Boolean deleteByMainId(@RequestParam("companyId") String companyId) {
        return warehousingFilingPersonnelService.deleteByMainId(companyId);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid WarehousingFilingPersonnelSaveParam param) {
        return warehousingFilingPersonnelService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid WarehousingFilingPersonnelSaveParam param) {
        return warehousingFilingPersonnelService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return warehousingFilingPersonnelService.removeData(ids);
    }

    @ApiOperation("批量新增或修改")
    @PostMapping("/saveOrUpdateBatchData")
    public Boolean saveOrUpdateBatchData(@RequestBody @Valid List<WarehousingFilingPersonnelSaveParam> param) {
        return warehousingFilingPersonnelService.saveOrUpdateBatchData(param);
    }

    @ApiOperation("导入")
    @PostMapping("/import")
    public void importData(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
        warehousingFilingPersonnelService.importData(file);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid WarehousingFilingPersonnelPageParam pageParam) {
        warehousingFilingPersonnelService.exportData(pageParam);
    }
                
}