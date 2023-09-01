package com.sydata.framework.util;

import cn.hutool.core.text.StrFormatter;
import one.util.streamex.StreamEx;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author sydata
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 星号
     */
    private static final String START = "*";

    /**
     * 中文正则校验
     */
    private static final Pattern ZH_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 字符串逗号间隔符
     */
    public static final String STRING_DELIMITER = ",";
    /**
     * 字符串点
     */
    public static final String DOT = ".";
    /**
     * 字符串两个点
     */
    public static final String DOT_DOUBLE = "..";
    /**
     * 字符串空格间隔符
     */
    public static final String STRING_SPACE = " ";

    /**
     * 字符串斜杠间隔符
     */
    public static final String STRING_SLASH = "/";

    /**
     * 字符串左括号间隔符
     */
    public static final String STRING_LEFT_BRACKET = "(";

    /**
     * 字符串右括号间隔符
     */
    public static final String STRING_RIGHT_BRACKET = ")";

    /**
     * 字符串等于符号
     */
    public static final String STRING_EQ = "=";

    public static final String STRING_DELIMITER_UNDERLINE = "_";

    public static final String STRING_DELIMITER_VERTICAL_LINEE = "|";

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substringReturnThis(final String str, int start, int end) {
        if (str == null || end > str.length()) {
            return str;
        }
        return substring(str, start, end);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            boolean status = (i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase;
            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if (status) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains(STRING_DELIMITER_UNDERLINE)) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split(STRING_DELIMITER_UNDERLINE);
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰式转下划线命名法 例如：userName->user_name
     */
    public static String camelToUnderline(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isUpperCase(c)) {
                sb.append(SEPARATOR).append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str  指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, List<String> strs) {
        if (isEmpty(str) || isEmpty(strs)) {
            return false;
        }
        for (String testStr : strs) {
            if (matches(str, testStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找指定字符串是否匹配指定字符串数组中的任意一个字符串
     *
     * @param str  指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, String... strs) {
        if (isEmpty(str) || isEmpty(strs)) {
            return false;
        }
        for (String testStr : strs) {
            if (matches(str, testStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找指定字符串是否匹配
     *
     * @param str     指定字符串
     * @param pattern 需要检查的字符串
     * @return 是否匹配
     */
    public static boolean matches(String str, String pattern) {
        if (isEmpty(pattern) || isEmpty(str)) {
            return false;
        }

        // 替换空格
        pattern = pattern.replaceAll("\\s*", "");
        // pattern截取开始位置
        int beginOffset = 0;
        // 前星号的偏移位置
        int formerStarOffset = -1;
        // 后星号的偏移位置
        int latterStarOffset = -1;

        String remainingUri = str;
        String prefixPattern = "";
        String suffixPattern = "";

        boolean result = false;
        do {
            formerStarOffset = indexOf(pattern, START, beginOffset);
            prefixPattern = substring(pattern, beginOffset, formerStarOffset > -1 ? formerStarOffset : pattern.length());

            // 匹配前缀Pattern
            result = remainingUri.contains(prefixPattern);
            // 已经没有星号，直接返回
            if (formerStarOffset == -1) {
                return result;
            }

            // 匹配失败，直接返回
            if (!result) {
                return false;
            }

            if (!isEmpty(prefixPattern)) {
                remainingUri = substringAfter(str, prefixPattern);
            }

            // 匹配后缀Pattern
            latterStarOffset = indexOf(pattern, START, formerStarOffset + 1);
            suffixPattern = substring(pattern, formerStarOffset + 1, latterStarOffset > -1 ? latterStarOffset : pattern.length());

            result = remainingUri.contains(suffixPattern);
            // 匹配失败，直接返回
            if (!result) {
                return false;
            }

            if (!isEmpty(suffixPattern)) {
                remainingUri = substringAfter(str, suffixPattern);
            }

            // 移动指针
            beginOffset = latterStarOffset + 1;

        }
        while (!isEmpty(suffixPattern) && !isEmpty(remainingUri));

        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    public static String joinString(final String[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = newStringBuilder(noOfItems);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    private static StringBuilder newStringBuilder(final int noOfItems) {
        return new StringBuilder(noOfItems * 16);
    }

    /**
     * 统计某个字符出现次数
     *
     * @param seq       扫描字符串
     * @param searchSeq 搜索字符串
     * @return 出现次数
     */
    public static int indexOfCount(final String seq, final String searchSeq) {
        if (seq == null || searchSeq == null) {
            return 0;
        }
        int count = 0;
        int index = seq.indexOf(searchSeq);
        while (index != -1) {
            count++;
            index = seq.indexOf(searchSeq, index + searchSeq.length());
        }
        return count;
    }

    /**
     * 所有上级id类型字符串获取直属上级
     * 比如：440000,440300,440307,1,5551,1393,6145,
     * 取值后：440000,440300,440307,1,5551,1393,
     *
     * @param allParentId 所有上级id
     * @return 直属上级
     */
    public static String getAllParentIdParent(String allParentId) {
        if (isEmpty(allParentId)) {
            return null;
        }
        int index = allParentId.lastIndexOf(STRING_DELIMITER);
        if (index == INDEX_NOT_FOUND) {
            return null;
        }
        int lastIndexOf = allParentId.lastIndexOf(STRING_DELIMITER, index - STRING_DELIMITER.length());
        if (lastIndexOf == INDEX_NOT_FOUND) {
            return null;
        }
        return allParentId.substring(0, lastIndexOf + STRING_DELIMITER.length());
    }

    /**
     * 复杂类型集合转换成sql查询in
     *
     * @param list       待转换集合
     * @param classifier 自定义取值
     * @return sql查询in
     */
    public static <T, K> String listStringToSqlIn(List<T> list, Function<? super T, ? extends K> classifier) {
        return StreamEx.of(list).map(classifier).map(k -> String.format("'%s'", k)).joining(STRING_DELIMITER);
    }

    /**
     * 判断是否是数字
     *
     * @param cs 待校验字符串
     * @return 是否是数字
     */
    public static boolean isNumeric(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        char charAt;
        for (int i = 0; i < sz; i++) {
            charAt = cs.charAt(i);
            if (!Character.isDigit(charAt) && charAt != ' ' && charAt != '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        return ZH_PATTERN.matcher(str).find();
    }

    /**
     * 首字母大写
     *
     * @param name 原始字符
     * @return 首字母大写
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 移除[]
     *
     * @param str
     * @return
     */
    public static String removeBrackets(String str) {
        return str.replaceAll("[\\[\\]\\(\\)]", "");
    }

    /**
     * 计算string长度,中文占用两个长度
     *
     * @param value 值
     * @return 长度
     */
    public static int stringLength(String value) {
        int length = 0;
        if (isNotEmpty(value)) {
            for (int i = 0; i < value.length(); i++) {
                String temp = value.substring(i, i + 1);
                if (ZH_PATTERN.matcher(temp).matches()) {
                    length += 2;
                } else {
                    length += 1;
                }
            }
        }
        return length;
    }
}
