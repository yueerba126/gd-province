package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingDwxz-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingDwxzEnum {

    国有("0"),
    外资("1"),
    民营("2"),
    其它("3"),
    ;

    private String status;

    FilingDwxzEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
