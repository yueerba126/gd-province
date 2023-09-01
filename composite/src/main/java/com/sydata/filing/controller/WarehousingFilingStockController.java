package com.sydata.filing.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.filing.service.IWarehousingFilingStockService;
import com.sydata.filing.param.WarehousingFilingStockPageParam;
import com.sydata.filing.param.WarehousingFilingStockSaveParam;
import com.sydata.filing.vo.WarehousingFilingStockVo;
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
 * @Description:TODO(仓储备案-仓储库点API接口层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@Api(tags = "仓储备案-仓储库点",value = "仓储备案-仓储库点")
@Validated
@RestController
@RequestMapping("/filing/warehousingFilingStock")
public class WarehousingFilingStockController {

    @Resource
    private IWarehousingFilingStockService warehousingFilingStockService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<WarehousingFilingStockVo> page(@RequestBody @Valid WarehousingFilingStockPageParam pageParam) {
        return warehousingFilingStockService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public WarehousingFilingStockVo detail(@RequestParam("id") String id) {
        return warehousingFilingStockService.detail(id);
    }

    @ApiOperation("根据主表ID，查询从表列表")
    @PostMapping("/listByMainId")
    public List<WarehousingFilingStockVo> listByMainId(@RequestParam("companyId") String companyId) {
        return warehousingFilingStockService.listByMainId(companyId);
    }

    @ApiOperation("根据主表ID，删除从表")
    @PostMapping("/deleteByMainId")
    public Boolean deleteByMainId(@RequestParam("companyId") String companyId) {
        return warehousingFilingStockService.deleteByMainId(companyId);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid WarehousingFilingStockSaveParam param) {
        return warehousingFilingStockService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid WarehousingFilingStockSaveParam param) {
        return warehousingFilingStockService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return warehousingFilingStockService.removeData(ids);
    }

    @ApiOperation("批量新增或修改")
    @PostMapping("/saveOrUpdateBatchData")
    public Boolean saveOrUpdateBatchData(@RequestBody @Valid List<WarehousingFilingStockSaveParam> param) {
        return warehousingFilingStockService.saveOrUpdateBatchData(param);
    }

    @ApiOperation("导入")
    @PostMapping("/import")
    public void importData(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
        warehousingFilingStockService.importData(file);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid WarehousingFilingStockPageParam pageParam) {
        warehousingFilingStockService.exportData(pageParam);
    }
                
}