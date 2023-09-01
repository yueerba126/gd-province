package com.sydata.video.service;

import com.alibaba.fastjson2.JSONObject;
import com.sydata.video.param.PlaybackParam;
import com.sydata.video.vo.RegionEquipmentCountVo;

/**
 * @author lzq
 * @description 海康视频监控Service
 * @date 2022/12/26 11:23
 */
public interface IVideoHkService {

    /**
     * 获取区域设备统计信息
     *
     * @param hkRegionId 绑定行政区域的海康区域ID
     * @return 区域设备数统计VO
     */
    RegionEquipmentCountVo regionEquipmentCount(String hkRegionId);

    /**
     * 查询监控点列表
     *
     * @param hkRegionId 绑定库区的海康区域ID
     * @return 海康返回值
     */
    JSONObject cameraList(String hkRegionId);

    /**
     * 获取监控点预览取流URL
     *
     * @param cameraIndexCode 监控点唯一标识
     * @return 预览取流URL
     */
    String previewUrl(String cameraIndexCode);

    /**
     * 获取监控点回放取流URL
     *
     * @param playbackParam 回放取流参数
     * @return 回放取流URL
     */
    String playbackUrl(PlaybackParam playbackParam);
}
