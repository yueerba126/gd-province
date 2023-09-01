package com.sydata.common.api.enums;

/**
 * 启用禁用枚举
 *
 * @author sydata
 * @date 2020/10/24
 */
public enum YesNo {

    /**
     * 1是 启用 正常 在线 有效 等正面状态
     */
    YES("1", "是"),
    /**
     * 0否 禁用 异常 离线 无效 等负面状态
     */
    NO("0", "否");

    private String code;
    private String name;

    YesNo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
