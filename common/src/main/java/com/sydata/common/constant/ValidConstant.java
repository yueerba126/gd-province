package com.sydata.common.constant;

import org.apache.commons.collections4.SetUtils;

import java.math.BigDecimal;
import java.util.Set;

/**
 * 数据校验常量
 *
 * @author czx
 */
public class ValidConstant {

    /**
     * 入库业务标识
     */
    public static final String RK = "14";

    /**
     * 出库业务标识
     */
    public static final String CK = "15";

    /**
     * 货位状态字典值-空仓
     */
    public static final String KC = "1";

    /**
     * 货位状态字典值-入库中
     */
    public static final String RKZ = "2";

    /**
     * 货位状态字典值-封仓
     */
    public static final String FC = "3";

    /**
     * 时间转字符串格式yyyyMMdd，如20221031
     */
    public static final String YYYY_MM_DD = "yyyyMMdd";

    /**
     * 时间转字符串格式yyyyMMdd，如221031
     */
    public static final String YY_MM_DD = "yyMMdd";

    /**
     * 仓房代码-平房仓顶级key
     */
    public static final String PFC = "10101";

    /**
     * 仓房代码-筒仓顶级key
     */
    public static final String TC = "10102";

    /**
     * 仓房代码-浅圆仓顶级key
     */
    public static final String QYC = "10103";

    /**
     * 人员姓名校验规则.默认|分割
     */
    public static final String CHINA = "^[\\|\\u4e00-\\u9fa5]+$";

    /**
     * 国家粮食平台，人员姓名排除字段
     */
    public static final Set<String> ILLEGAL_NAME = SetUtils.hashSet("暂无", "无", "空", "测试");


    /**
     * 交通工具汽车运输字典
     */
    public static final String QC = "1";

    /**
     * 采购合同业务类型字典值
     */
    public static final String CG_HT = "2";

    /**
     * 销售合同业务类型字典值
     */
    public static final String XS_HT = "1";


    /**
     * 包存粮方式字典值
     */
    public static final String BCL = "2";

    /**
     * 单价误差允许范围值
     */
    public static final BigDecimal PRICE_DIFFERENCE = BigDecimal.valueOf(0.01);
}
