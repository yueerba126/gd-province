package com.sydata.grade.enums;

/**
 * @program: gd-saas-erp
 * @description:
 * @author: lzq
 * @create: 2023-05-30 16:17
 */
public enum HttpEnum {

    /**
     * 200
     */
    OK(200),
    /**
     * 500
     */
    NO(500),
    ;

    private int state;


    HttpEnum(int state) {
        this.state = state;
    }


    public int getState() {
        return state;
    }
}
