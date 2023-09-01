package com.sydata.video.util;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson2.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.sydata.video.config.HkVideoConfig;
import com.sydata.video.enums.HkApiEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Map;

import static com.hikvision.artemis.sdk.constant.ContentType.CONTENT_TYPE_JSON;
import static com.sydata.video.enums.HkApiEnum.*;

/**
 * @author lzq
 * @description 海康接口调用工具类
 * @date 2022/12/26 11:05
 */
@Component
public final class HkApiInvokerUtil {

    private static HkVideoConfig hkVideoConfig;

    public HkApiInvokerUtil(HkVideoConfig hkVideoConfig) {
        HkApiInvokerUtil.hkVideoConfig = hkVideoConfig;

        // 初始化海康配置
        ArtemisConfig.host = hkVideoConfig.getHost();
        ArtemisConfig.appKey = hkVideoConfig.getAppKey();
        ArtemisConfig.appSecret = hkVideoConfig.getAppSecret();
    }


    /**
     * 根据海康区域ID获取指定状态的设备数量
     *
     * @param hkRegionId 海康区域ID
     * @param status     设备状态 	1-在线，0-离线，-1-未检测
     * @return 设备数
     */
    public static Integer equipmentCountByRegion(String hkRegionId, String status) {
        //组装JSON
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("regionId", hkRegionId);
        jsonBody.put("includeSubNode", "1");
        jsonBody.put("status", status);
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 1);
        String body = jsonBody.toString();

        Map<String, String> path = buildApiPath(REGION_EQUIPMENT);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE_JSON);
        return resultData(result).getInteger("total");
    }

    /**
     * 查询监控点列表
     *
     * @param hkRegionId 海康区域ID
     * @return 监控点列表
     */
    public static JSONObject cameraList(String hkRegionId) {
        //组装JSON
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("regionIndexCode", hkRegionId);
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 1000);
        String body = jsonBody.toString();

        Map<String, String> path = buildApiPath(CAMERA_LIST);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE_JSON);
        return resultData(result);
    }

    /**
     * 获取监控点预览取流URL
     *
     * @param cameraIndexCode 监控点唯一标识
     * @return 预览取流URL
     */
    public static String previewUrl(String cameraIndexCode) {
        //组装JSON
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", cameraIndexCode);
        jsonBody.put("protocol","ws");
        jsonBody.put("expand","transcode=0");
        String body = jsonBody.toString();

        Map<String, String> path = buildApiPath(PREVIEW_URLS);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE_JSON);
        return resultData(result).getString("url");
    }

    /**
     * 获取监控点回放取流URL
     *
     * @param cameraIndexCode 监控点唯一标识
     * @param beginTime       开始时间
     * @param endTime         结束时间
     * @return 回放取流URL
     */
    public static String playbackUrl(String cameraIndexCode, LocalDateTime beginTime, LocalDateTime endTime) {
        //组装JSON
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", cameraIndexCode);
        jsonBody.put("beginTime", beginTime);
        jsonBody.put("endTime", endTime);
        String body = jsonBody.toString();

        Map<String, String> path = buildApiPath(PLAYBACK_URLS);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE_JSON);
        return resultData(result).getString("url");
    }


    /**
     * 构建api路径
     *
     * @param hkApiEnum 海康接口枚举
     * @return apiPath
     */
    private static Map<String, String> buildApiPath(HkApiEnum hkApiEnum) {
        return MapUtil.of(hkVideoConfig.getProtocol(), hkVideoConfig.getPath() + hkApiEnum.getApi());
    }

    /**
     * 获取结果集
     *
     * @param result 响应参数
     * @return data结果
     */
    private static JSONObject resultData(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        Assert.state(code == 0, "海康接口返回异常:" + jsonObject.get("msg"));
        return jsonObject.getJSONObject("data");
    }
}
