package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingBazt-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingBaztEnum {

    保存("0"),
    待备案("1"),
    已备案("2"),
    审核不通过("3"),
    已注销("4"),
    ;

    private String status;

    FilingBaztEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
