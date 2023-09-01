package com.sydata.common.util;

import cn.hutool.core.lang.RegexPool;

/**
 * @author lzq
 * @description 正则表达式
 * @date 2023/6/25 10:32
 */
public final class RegexpUtil implements RegexPool {

    /**
     * 特殊字符校验
     * 不能存在如下字符："/"、"\"、"'"
     */
    public static final String SPECIAL_CHECK = "^[^/'\\\\]*$";
}
