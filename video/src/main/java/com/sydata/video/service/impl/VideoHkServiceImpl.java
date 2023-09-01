package com.sydata.video.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.sydata.video.param.PlaybackParam;
import com.sydata.video.service.IVideoHkService;
import com.sydata.video.util.HkApiInvokerUtil;
import com.sydata.video.vo.RegionEquipmentCountVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * @author lzq
 * @description 海康视频监控Service实现类
 * @date 2022/12/26 11:24
 */
@Service("videoHkService")
@CacheConfig(cacheNames = VideoHkServiceImpl.CACHE_NAME)
public class VideoHkServiceImpl implements IVideoHkService {

    final static String CACHE_NAME = "video:hk";

    @Cacheable(key = "'regionEquipmentCount='+#hkRegionId")
    @Override
    public RegionEquipmentCountVo regionEquipmentCount(String hkRegionId) {
        Integer onLineCount = HkApiInvokerUtil.equipmentCountByRegion(hkRegionId, "1");
        Integer offLineCount = HkApiInvokerUtil.equipmentCountByRegion(hkRegionId, "0");
        return new RegionEquipmentCountVo(onLineCount, offLineCount);
    }

    @Cacheable(key = "'cameraList='+#hkRegionId")
    @Override
    public JSONObject cameraList(String hkRegionId) {
        return HkApiInvokerUtil.cameraList(hkRegionId);
    }

    @Override
    public String previewUrl(String cameraIndexCode) {
        return HkApiInvokerUtil.previewUrl(cameraIndexCode);
    }

    @Override
    public String playbackUrl(PlaybackParam param) {
        return HkApiInvokerUtil.playbackUrl(param.getCameraIndexCode(), param.getBeginTime(), param.getEndTime());
    }
}
