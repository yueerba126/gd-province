package com.sydata.dostrict.personnel.enums;

/**
 * 人员制度管理状态
 *
 * @author fuql
 * @date 2020/10/24
 */
public enum SystemBillStatusEnums {

    /**
     * 拟稿
     */
    SAVE("1", "拟稿"),

    /**
     * 删除
     */
    ABOLISH("9", "删除"),
    /**
     * 发布
     */
    RELEASE("2", "发布");

    private String code;
    private String name;

    SystemBillStatusEnums(String code, String name) {
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
