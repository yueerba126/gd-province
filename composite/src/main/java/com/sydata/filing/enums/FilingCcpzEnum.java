package com.sydata.filing.enums;

/**   
 * @Description:TODO(FilingCcpz-枚举)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public enum FilingCcpzEnum {

    小麦("0"),
    玉米("1"),
    稻谷("2"),
    大豆("3"),
    成品粮("4"),
    食用植物油("5"),
    其他("6"),
    ;

    private String status;

    FilingCcpzEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
