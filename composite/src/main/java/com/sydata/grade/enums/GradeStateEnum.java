package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 模板状态
 * @date 2023/5/18 15:25
 */
public enum GradeStateEnum {

    /**
     * 拟稿
     */
    SAVE("1"),
    /**
     * 已发布
     */
    PUSH("2"),
    /**
     * 已完成
     */
    COMPLETE("3"),
    ;

    private String state;


    GradeStateEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
