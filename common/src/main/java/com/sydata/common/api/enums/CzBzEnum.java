package com.sydata.common.api.enums;

import one.util.streamex.StreamEx;

import java.util.Map;

import static java.util.function.Function.identity;

/**
 * @author lzq
 * @describe 操作标志枚举
 * @date 2022-06-29 19:44
 */
public enum CzBzEnum {

    /**
     * 新增数据
     */
    I("i", "新增数据"),

    /**
     * 修改数据
     */
    U("u", "修改数据"),

    /**
     * 删除数据
     */
    D("d", "删除数据"),

    ;

    private String czBz;

    private String msg;

    private static Map<String, CzBzEnum> CZBZ_MAP = StreamEx.of(values()).toMap(CzBzEnum::getCzBz, identity());

    CzBzEnum(String czBz, String msg) {
        this.czBz = czBz;
        this.msg = msg;
    }

    public String getCzBz() {
        return czBz;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 根据操作标志获取枚举
     *
     * @param czBz 操作标志
     * @return 操作标志枚举
     */
    public static CzBzEnum getBy(String czBz) {
        return CZBZ_MAP.get(czBz);
    }
}
