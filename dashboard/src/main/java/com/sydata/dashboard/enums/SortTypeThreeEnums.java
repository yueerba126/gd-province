package com.sydata.dashboard.enums;

/**
 * 粮食品种分类-国家平台分类三级分类
 *
 * @author fuql
 * @date 2020/10/24
 */
public enum SortTypeThreeEnums {

    /**
     * 1 粮食
     */
    ONE("1", "粮食"),

    /**
     * 2 油
     */
    TWO("2", "油"),
;

    private String code;
    private String name;

    SortTypeThreeEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static String getTypeThree(String code){
        for (SortTypeThreeEnums ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getName();
            }
        }
        return "";
    }
}
