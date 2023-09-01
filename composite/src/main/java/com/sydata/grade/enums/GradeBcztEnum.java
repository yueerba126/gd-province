package com.sydata.grade.enums;

/**
 * @author lzq
 * @description 保存状态
 * @date 2023/5/18 15:25
 */
public enum GradeBcztEnum {

    /**
     * 暂存
     */
    ZC("1"),
    /**
     * 提交
     */
    TJ("2"),
    ;

    private String state;


    GradeBcztEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
