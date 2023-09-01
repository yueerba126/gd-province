package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingCyzw-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingCyzwEnum {

    粮油保管员("0"),
    粮油质检员("1"),
    ;

    private String status;

    FilingCyzwEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
