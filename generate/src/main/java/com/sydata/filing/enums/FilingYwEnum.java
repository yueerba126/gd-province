package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingYw-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingYwEnum {

    有("0"),
    无("1"),
    ;

    private String status;

    FilingYwEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
