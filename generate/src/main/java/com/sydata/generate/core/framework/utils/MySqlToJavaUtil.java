package com.sydata.generate.core.framework.utils;

import com.sydata.generate.core.framework.convert.MySqlTypeConvert;
import com.sydata.generate.core.framework.convert.DateType;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-17 22:58
 */
public class MySqlToJavaUtil {

    public MySqlToJavaUtil() {
    }

    public static String getClassName(String table) {
        table = changeToJavaFiled(table);
        StringBuilder sbuilder = new StringBuilder();
        char[] cs = table.toCharArray();
        cs[0] = (char)(cs[0] - 32);
        sbuilder.append(String.valueOf(cs));
        return sbuilder.toString();
    }

    public static String changeToJavaFiled(String field) {
        String[] fields = field.split("_");
        StringBuilder sbuilder = new StringBuilder(fields[0]);

        for(int i = 1; i < fields.length; ++i) {
            char[] cs = fields[i].toCharArray();
            cs[0] = (char)(cs[0] - 32);
            sbuilder.append(String.valueOf(cs));
        }

        return sbuilder.toString();
    }

    public static String jdbcTypeToJavaType(String sqlType) {
        MySqlTypeConvert typeConvert = new MySqlTypeConvert();
        return typeConvert.processTypeConvert(DateType.TIME_PACK, sqlType).getType();
    }

    public static String jdbcTypeToJavaTypePck(String sqlType) {
        MySqlTypeConvert typeConvert = new MySqlTypeConvert();
        return typeConvert.processTypeConvert(DateType.TIME_PACK, sqlType).getPkg();
    }
}
