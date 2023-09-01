package com.sydata.dostrict.personnel.enums;

/**
 * 人才培养状态
 *
 * @author fuql
 * @date 2020/10/24
 */
public enum CultureBillStatusEnums {

    /**
     * 拟稿
     */
    SAVE("1", "拟稿"),

    /**
     * 删除
     */
    ABOLISH("9", "删除"),

    /**
     * 审核
     */
    AUDIT("3", "审核"),
    /**
     * 待审核
     */
    RELEASE("2", "待审核");

    private String code;
    private String name;

    CultureBillStatusEnums(String code, String name) {
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
