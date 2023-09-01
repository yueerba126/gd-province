package com.sydata.basis.enums;

/**
 * @author lzq
 * @describe 字典类型枚举
 * @date 2022-07-26 14:35
 */
public enum DictTypeEnum {

    /**
     * 粮食品种
     */
    FOOD_VARIETY("food_variety"),

    /**
     * 粮食等级
     */
    FOOD_GRADE("food_grade"),

    /**
     * 粮食性质
     */
    FOOD_NATURE("food_nature"),

    /**
     * 质检项
     */
    INSPECT_ITEM("inspect_item"),

    /**
     * 害虫种类
     */
    PEST_TYPE("pest_type"),

    /**
     * 虫粮判定等级
     */
    PEST_GRADE("pest_grade"),

    ;


    private final String dictType;

    DictTypeEnum(String dictType) {
        this.dictType = dictType;
    }

    public String getDictType() {
        return dictType;
    }
}
