package com.sydata.safe.asess.enums;

/**
 * @author czx
 * @description 抽查计划状态枚举
 * @date 2023/2/13 15:25
 */
public enum CheckPlanStateEnum {


    /**
     * 保存
     */
    SAVE("保存"),

    /**
     * 抽查中
     */
    CHECKING("抽查中"),

    /**
     * 已抽查
     */
    COMPLETE("已抽查"),

    ;

    private String state;


    CheckPlanStateEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
