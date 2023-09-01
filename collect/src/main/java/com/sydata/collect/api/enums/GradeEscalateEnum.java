package com.sydata.collect.api.enums;

/**
 * @author lzq
 * @description 上报标志
 * @date 2023/5/18 15:25
 */
public enum GradeEscalateEnum {

    /**
     * 保存
     */
    SAVE("1"),
    /**
     * 提交
     */
    SUMMIT("2"),
    /**
     * 可以上报
     */
    PASS("3"),
    /**
     * 上报成功
     */
    SUCCESS("4"),
    /**
     * 上报失败
     */
    FAIL("5"),
    /**
     * 被打回
     */
    REPULSE("6"),
    /**
     * 被降级
     */
    REDUCE("7"),
    /**
     * 被摘除
     */
    PICK("8"),
    /**
     * 申报成功
     */
    SB_SUCCESS("8"),
    ;

    private String state;


    GradeEscalateEnum(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }
}
