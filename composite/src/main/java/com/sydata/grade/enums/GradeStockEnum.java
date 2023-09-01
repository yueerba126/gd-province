package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 粮库等级
 * @date 2023/5/18 15:25
 */
public enum GradeStockEnum {

    /**
     * A
     */
    A(1),
    /**
     * AA
     */
    AA(2),
    /**
     * AAA
     */
    AAA(3),
    /**
     * AA
     */
    AAAA(4),
    /**
     * AA
     */
    AAAAA(5),
    ;

    private Integer state;


    GradeStockEnum(Integer state) {
        this.state = state;
    }


    public Integer getState() {
        return state;
    }
}
