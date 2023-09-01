package com.sydata.warn.enums;

/**
 * @author fuql
 * @description 预警类型及内容
 * @date 2022/10/21 10:56
 */
public enum WarnTypeContentEnum {

    /**
     * 库存超限告警
     */
    INVENTORY_WARN("1", "%s实际库存量低于最低计划库存量%s万吨，现可用库存：%s万吨（实际库存：%s万吨）"),
    /**
     * 库存数量异常波动告警
     * （粮食品种）库存数量小于" + (plan_qty ) + "吨
     */
    QTY_WARN("2", "%s库存数量小于%s吨"),
    /**
     * 储备年限告警
     * (公司)（库点）（仓房）:（物料品种）储存超过（储备年限）年
     */
    YEAR_WARN("3", "%s%s%s:%s储存超过%s年"),


    ;

    private final String key;
    private final String msg;

    WarnTypeContentEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }
}
