package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingRylb-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingRylbEnum {

    法人("0"),
    主要联系人("1"),
    从业人员("2"),
    ;

    private String status;

    FilingRylbEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
