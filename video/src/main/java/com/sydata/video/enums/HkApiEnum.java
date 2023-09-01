package com.sydata.video.enums;

/**
 * @author lzq
 * @description 海康接口枚举
 * @date 2022/12/26 11:11
 */
public enum HkApiEnum {

    /**
     * 根据区域ID获取监控点在线状态
     */
    REGION_EQUIPMENT("/api/nms/v1/online/camera/get", "根据海康区域ID获取监控点在线状态"),


    /**
     * 查询监控点列表
     */
    CAMERA_LIST("/api/resource/v1/camera/advance/cameraList", "查询监控点列表"),

    /**
     * 获取监控点预览取流URL
     */
    PREVIEW_URLS("/api/video/v2/cameras/previewURLs", "获取监控点预览取流URL"),

    /**
     * 获取监控点回放取流URL
     */
    PLAYBACK_URLS("/api/video/v1/cameras/playbackURLs", "获取监控点回放取流URL"),

    ;
    private String api;
    private String msg;


    HkApiEnum(String api, String msg) {
        this.api = api;
        this.msg = msg;
    }

    public String getApi() {
        return api;
    }

    public String getMsg() {
        return msg;
    }
}
