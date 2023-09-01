package com.sydata.dashboard.enums;

/**
 * 储备粮实物库存报表类型
 *
 * @author fuql
 * @date 2020/10/24
 */
public enum PhysicalInventoryEnums {

    /**
     * 0全部储备
     */
    ZERO("0", "全部储备"),

    /**
     * 1省储
     */
    PROVINCE("1", "省储"),
    /**
     * 2市储
     */
    CITY("2", "市储"),
    /**
     * 3区县储备
     */
    AREA("3", "区县储备"),
    /**
     * 4其他储备
     */
    FORE("4", "其他储备"),
    /**
     * 5 市储 + 区县储备
     */
    FIVE("5", "市储 + 区县储备"),
;

    private String code;
    private String name;

    PhysicalInventoryEnums(String code, String name) {
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
