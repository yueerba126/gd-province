package com.sydata.organize.enums;

import one.util.streamex.StreamEx;

import java.util.Map;

import static java.util.function.UnaryOperator.identity;

/**
 * @author lzq
 * @describe 登录设备枚举
 * @date 2022-06-29 09:15
 */
public enum LoginDeviceEnum {

    /**
     * PC
     */
    PC("pc"),

    /**
     * APP
     */
    APP("app"),

    /**
     * api
     */
    API("api"),

    ;

    private static final Map<String, LoginDeviceEnum> DEVICE_MAP = StreamEx.of(values())
            .toMap(LoginDeviceEnum::getDevice, identity());


    private String device;


    LoginDeviceEnum(String device) {
        this.device = device;
    }

    public String getDevice() {
        return device;
    }


    /**
     * 获取登录设备枚举
     *
     * @param device 登录设备
     * @return 登录设备枚举
     */
    public static LoginDeviceEnum getByDevice(String device) {
        return DEVICE_MAP.get(device);
    }
}
