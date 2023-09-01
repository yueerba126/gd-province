package com.sydata.organize.enums;

import one.util.streamex.StreamEx;

import java.util.Map;

import static java.util.function.UnaryOperator.identity;

/**
 * @author lzq
 * @description 用户消息目标类型枚举
 * @date 2023/3/2 15:31
 */
public enum UserMessageTargetTypeEnum {

    /**
     * 部门
     */
    DEPT(1, "部门"),

    /**
     * 角色
     */
    ROLE(2, "角色"),

    /**
     * 用户
     */
    USER(3, "用户"),

    /**
     * 组织
     */
    ORGANIZE(4, "组织"),


    ;

    private Integer targetType;
    private String msg;

    private static final Map<Integer, UserMessageTargetTypeEnum> TARGET_TYPE_MAP = StreamEx.of(values())
            .toMap(UserMessageTargetTypeEnum::getTargetType, identity());

    UserMessageTargetTypeEnum(Integer targetType, String msg) {
        this.targetType = targetType;
        this.msg = msg;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 获取用户消息目标类型枚举
     *
     * @param targetType 目标类型
     * @return 用户消息目标类型枚举
     */
    public static UserMessageTargetTypeEnum getByTargetType(Integer targetType) {
        return TARGET_TYPE_MAP.get(targetType);
    }
}
