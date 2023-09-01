package com.sydata.organize.enums;

import one.util.streamex.StreamEx;

import java.util.Map;

import static java.util.function.UnaryOperator.identity;

/**
 * @author lzq
 * @describe 组织业务类型枚举
 * @date 2022-06-30 11:15
 */
public enum OrganizeBusTypeEnum {

    /**
     * 粮食监管单位
     */
    FOOD_SUPERVISE("food_supervise", "粮食监管单位"),


    /**
     * 粮安考核单位
     */
    FOOD_ASSESS("food_assess", "粮安考核单位"),

    ;

    private String busType;
    private String msg;

    OrganizeBusTypeEnum(String busType, String msg) {
        this.busType = busType;
        this.msg = msg;
    }

    public String getBusType() {
        return busType;
    }

    public String getMsg() {
        return msg;
    }

    private static final Map<String, OrganizeBusTypeEnum> BUS_TYPE_MAP = StreamEx.of(values())
            .toMap(OrganizeBusTypeEnum::getBusType, identity());


    /**
     * 根据组织业务类型获取枚举
     *
     * @param busType 组织业务类型
     * @return 组织业务类型枚举
     */
    public static OrganizeBusTypeEnum getByBusType(String busType) {
        return BUS_TYPE_MAP.get(busType);
    }
}
