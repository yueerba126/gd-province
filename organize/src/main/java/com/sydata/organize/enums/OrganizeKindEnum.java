package com.sydata.organize.enums;

/**
 * @author lzq
 * @describe 组织类型枚举
 * @date 2022-06-30 11:15
 */
public enum OrganizeKindEnum {

    /**
    * 行政组织
    * */
    ADMIN("行政组织"),


    /**
    * 企业组织
    * */
    ENTERPRISE("企业组织"),
    ;

    private String kind;

    OrganizeKindEnum(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }
}
