package com.sydata.generate.core;

import com.google.common.collect.Lists;
import com.sydata.generate.core.framework.domain.MethodInfo;
import com.sydata.generate.core.framework.generator.BusGenerator;

import java.util.List;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-18 00:31
 */
public class BusRun {
    // 基础信息：项目名、作者、版本、是否开启参数初始化
    public static final String AUTHOR = "lzq";
    public static final String VERSION = "V1.0";
    public static final String AGILE = System.currentTimeMillis() + "";
    // 数据库连接信息：连接URL、用户名、秘密、数据库名
    public static final String DB_URL = "jdbc:mysql://192.168.0.36:3306/gd-province-platform?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    public static final String DB_NAME = "root";
    public static final String DB_PASSWORD = "Password-888";
    public static final String DB_DATABASE = "gd-province-platform";
    // 类信息：类名、对象名（一般是【类名】的首字母小些）、类说明、时间
    public static final String PROJECT = "multiple";
    public static final String MODULE = "generate";
    public static final String BASE_URL = "com.sydata.business.";
    public static final String TABLE = "warehousing_filing_process";
    public static final String CLASS_COMMENT = "仓储备案-仓储审核详情";
    public static final String DATE = "2023年06月16日";

    // 在此处添加，自定义的方法的内容，只有此处需要自己写，上面的只要是能用的表即可
    public static final String REQUEST_MAPPING_PREFIX = "filing";
    public static final String ENTITY_NAME = "BusinessMan";
    public static final String ENTITY_COMMENT = "业务人";
    public static final List<MethodInfo> METHOD_INFO_LIST = Lists.newArrayList();
    static {
        METHOD_INFO_LIST.add(new MethodInfo("list", "List<" + ENTITY_NAME + "Vo>", "查询列表", "@Override"));
        METHOD_INFO_LIST.add(new MethodInfo("detail", ENTITY_NAME + "Vo", "查询详情", "@Override"));
    }
    public static void main(String[] args) {
        BusGenerator.main(args);
    }
}
