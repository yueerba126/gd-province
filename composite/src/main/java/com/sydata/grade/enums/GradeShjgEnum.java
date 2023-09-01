package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 授牌状态
 * @date 2023/5/18 15:25
 */
public enum GradeShjgEnum {

    /**
     * 通过
     */
    YES("1"),
    /**
     * 不通过
     */
    NO("2"),
    /**
     * 降级
     */
    REDUCE("1"),
    /**
     * 摘牌
     */
    PICK("2"),
    ;

    private String state;


    GradeShjgEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
