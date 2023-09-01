package com.sydata.framework.cache.listener;

import one.util.streamex.StreamEx;

/**
 * @author lzq
 * @describe redis key 事件枚举
 * @date 2022-03-02 01:48
 */
public enum RedisKeyEventEnum {
    /**
     * 设置
     */
    SET("set"),

    /**
     * 过期
     */
    EXPIRED("expired"),

    /**
     * 设置过期时间
     */
    EXPIRE("expire"),

    /**
     * 删除
     */
    DEL("del"),

    /**
     * 修改名后
     */
    RENAME_TO("rename_to"),

    /**
     * 修改名前
     */
    RENAME_FROM("rename_from"),

    ;


    private String event;


    RedisKeyEventEnum(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    /**
     * 获取redis key事件枚举
     *
     * @param event 事件
     * @return redis key事件枚举
     */
    public static RedisKeyEventEnum getByEvent(String event) {
        return StreamEx.of(values())
                .toMap(RedisKeyEventEnum::getEvent, t -> t)
                .get(event);
    }
}
