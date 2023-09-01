package com.sydata.dashboard.enums;

/**
 * 广东省市
 *
 * @author fuql
 * @date 2020/10/24
 */
public enum InventoryMonitoringEnums {

    /**
     * 广州市
     */
    GZS("440100", "广州市"),
    /**
     * 韶关市
     */
    SGS("440200", "韶关市"),
    /**
     * 深圳市
     */
    SZS("440300", "深圳市"),
    /**
     * 珠海市
     */
    ZHS("440400", "珠海市"),
    /**
     * 汕头市
     */
    STS("440500", "汕头市"),
    /**
     * 佛山市
     */
    FSS("440600", "佛山市"),
    /**
     * 江门市
     */
    JMS("440700", "江门市"),
    /**
     * 湛江市
     */
    ZJS("440800", "湛江市"),
    /**
     * 茂名市
     */
    MMS("440900", "茂名市"),
    /**
     * 肇庆市
     */
    ZQS("441200", "肇庆市"),
    /**
     * 惠州市
     */
    HZS("441300", "惠州市"),
    /**
     * 梅州市
     */
    MZS("441400", "梅州市"),
    /**
     * 汕尾市
     */
    SWS("441500", "汕尾市"),
    /**
     * 河源市
     */
    HYS("441600", "河源市"),
    /**
     * 阳江市
     */
    YJS("441700", "阳江市"),
    /**
     * 清远市
     */
    QYS("441800", "清远市"),
    /**
     * 东莞市
     */
    DGS("441900", "东莞市"),
    /**
     * 中山市
     */
    ZSS("442000", "中山市"),
    /**
     * 潮州市
     */
    CSS("445100", "潮州市"),
    /**
     * 揭阳市
     */
    JYS("445200", "揭阳市"),
    /**
     * 云浮市
     */
    YFS("445300", "云浮市"),
;

    private String code;
    private String name;

    InventoryMonitoringEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
