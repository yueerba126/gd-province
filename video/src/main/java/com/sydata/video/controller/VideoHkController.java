package com.sydata.video.controller;

import com.alibaba.fastjson2.JSONObject;
import com.sydata.video.param.PlaybackParam;
import com.sydata.video.service.IVideoHkService;
import com.sydata.video.vo.RegionEquipmentCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 海康视频监控API
 * @date 2022/10/21 19:41
 */
@Api(tags = "海康视频监控API")
@Validated
@RestController
@RequestMapping("/video/hk")
public class VideoHkController {

    @Resource
    private IVideoHkService videoHkService;

    @ApiOperation("获取区域设备统计信息")
    @PostMapping("/region/equipment/count")
    public RegionEquipmentCountVo regionEquipmentCount(@RequestParam("hkRegionId") String hkRegionId) {
        return videoHkService.regionEquipmentCount(hkRegionId);
    }

    @ApiOperation("查询监控点列表")
    @PostMapping("/camera/list")
    public JSONObject cameraList(@RequestParam("hkRegionId") String hkRegionId) {
        return videoHkService.cameraList(hkRegionId);
    }

    @ApiOperation("预览取流URL")
    @PostMapping("/preview")
    public String previewUrl(@RequestParam("cameraIndexCode") String cameraIndexCode) {
        return videoHkService.previewUrl(cameraIndexCode);
    }

    @ApiOperation("回放取流URL")
    @PostMapping("/playback")
    public String playbackUrl(@RequestBody @Valid PlaybackParam playbackParam) {
        return videoHkService.playbackUrl(playbackParam);
    }
}
