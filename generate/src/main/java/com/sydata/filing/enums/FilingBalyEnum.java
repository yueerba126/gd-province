package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingBaly-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingBalyEnum {

    库软件("0"),
    粤商局("1"),
    ;

    private String status;

    FilingBalyEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
