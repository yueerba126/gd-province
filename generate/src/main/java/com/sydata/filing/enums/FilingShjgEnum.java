package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingShjg-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingShjgEnum {

    通过("0"),
    不通过("1"),
    ;

    private String status;

    FilingShjgEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
