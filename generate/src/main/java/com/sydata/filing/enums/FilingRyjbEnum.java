package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingRyjb-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingRyjbEnum {

    初级工("0"),
    中级工("1"),
    高级工("2"),
    ;

    private String status;

    FilingRyjbEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
