package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingLsptzc-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingLsptzcEnum {

    未注册("0"),
    已注册("1"),
    ;

    private String status;

    FilingLsptzcEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
