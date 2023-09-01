package com.sydata.organize.enums;

import one.util.streamex.StreamEx;

import java.util.Map;

import static java.util.function.Function.identity;

/**
 * @author lzq
 * @describe 数据视图枚举
 * @date 2022-06-30 11:15
 */
public enum DataViewEnum {

    /**
     * 粮权关系
     */
    STOCK("粮权关系"),


    /**
     * 归属关系
     */
    ASCRIPTION("归属关系"),

    ;

    private String view;

    private static final Map<String, DataViewEnum> MAP = StreamEx.of(values()).toMap(DataViewEnum::getView, identity());

    DataViewEnum(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    /**
     * 根据视图获取枚举
     *
     * @param view 视图
     * @return 视图枚举
     */
    public static DataViewEnum getByView(String view) {
        return MAP.get(view);
    }
}
