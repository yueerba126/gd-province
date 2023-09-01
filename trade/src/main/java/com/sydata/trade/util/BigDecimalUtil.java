package com.sydata.trade.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author lzq
 * @description 数额精度计算工具类
 * @date 2023/4/26 15:54
 */
public final class BigDecimalUtil {

    /**
     * 单价校验
     *
     * @param totalPrice      总价
     * @param count           数量
     * @param price           单价
     * @param priceDifference 允许差额
     * @return 是否在允许差额范围之内
     */
    public static boolean priceCheck(BigDecimal totalPrice, BigDecimal count, BigDecimal price,
                                     BigDecimal priceDifference) {
        BigDecimal difference = totalPrice.divide(count, 3, RoundingMode.FLOOR).subtract(price).abs();
        return priceDifference.compareTo(difference) >= 0;
    }
}
