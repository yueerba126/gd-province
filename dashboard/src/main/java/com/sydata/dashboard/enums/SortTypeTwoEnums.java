package com.sydata.dashboard.enums;

/**
 * 粮食品种分类-国家平台分类一级分类
 *
 * @author fuql
 * @date 2020/10/24
 */
public enum SortTypeTwoEnums {

    /**
     * 1 原粮小麦
     */
    ONE("1", "小麦"),
    /**
     * 2 原粮稻谷
     */
    TWO("2", "稻谷"),
    /**
     * 3 原粮玉米
     */
    THREE("3", "玉米"),
    /**
     * 4 原粮大豆
     */
    FORE("4", "大豆"),
    /**
     * 5 原粮其它
     */
    FIVE("5", "其它原粮"),

    /**
     * 6 大豆原油
     */
    SIX("6", "大豆原油"),

    /**
     * 7 花生原油
     */
    SEVEN("7", "花生原油"),

    /**
     * 8 菜籽原油
     */
    EIGHT("8", "菜籽原油"),

    /**
     * 9 其他原油
     */
    NINE("9", "其他原油"),

    /**
     * 10 面粉类(成品粮)
     */
    TEN("10", "面粉类"),

    /**
     * 11 玉米面类(成品粮)
     */
    ELEVEN("11", "玉米面类"),

    /**
     * 12 大米类(成品粮)
     */
    TWELVE("12", "大米类"),

    /**
     * 13 其他(成品粮)
     */
    THIRTEEN("13", "其他(成品粮)"),

    /**
     * 14 大豆油(成品油)
     */
    FOURTEEN("14", "大豆油"),

    /**
     * 15 花生油(成品油)
     */
    FIFTEEN("15", "花生油"),

    /**
     * 16 菜籽油(成品油)
     */
    SIXTEEN("16", "菜籽油"),

    /**
     * 17 其他(成品油)
     */
    SEVENTEEN("17", "其他成品油"),

    /**
     * 18 其他类
     */
    EIGHTEEN("18", "其他类"),

;

    private String code;
    private String name;

    SortTypeTwoEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }


    public static String getLspzflName(String code){
        for (SortTypeTwoEnums ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getName();
            }
        }
        return "";
    }
}
