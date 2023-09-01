package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingBalx-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingBalxEnum {

    初始备案("0"),
    变更备案("1"),
    注销备案("2"),
    ;

    private String status;

    FilingBalxEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
