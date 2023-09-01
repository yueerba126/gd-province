package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.WebcamLabel;
import com.sydata.basis.param.WebcamLabelPageParam;
import com.sydata.basis.service.IWebcamLabelService;
import com.sydata.basis.vo.WebcamLabelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * @author lzq
 * @description 基础信息-库区图视频监控设备点位标注
 * @date 2022/10/11 18:09
 */
@Api(tags = "基础信息-库区图视频监控设备点位标注")
@Validated
@RestController
@RequestMapping("/basis/webcam/label")
public class WebcamLabelController {

    @Resource
    private IWebcamLabelService webcamLabelService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<WebcamLabelVo> page(@RequestBody @Valid WebcamLabelPageParam pageParam) {
        return webcamLabelService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public WebcamLabelVo detail(@RequestParam("id") String id) {
        return webcamLabelService.detail(id);
    }

    @ApiOperation("根据库区获取设备点位标注列表")
    @PostMapping("/stock/houses")
    public List<WebcamLabel> listByStockHouseId(@RequestParam("stockHouseId") String stockHouseId) {
        return webcamLabelService.listByStockHouseId(stockHouseId);
    }
}
