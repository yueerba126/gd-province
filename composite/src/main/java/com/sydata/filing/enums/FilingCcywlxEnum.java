package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingCcywlx-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingCcywlxEnum {

    储备("0"),
    收购("1"),
    加工("2"),
    销售("3"),
    运输("4"),
    中转("5"),
    进出口("6"),
    其他("7"),
    ;

    private String status;

    FilingCcywlxEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
