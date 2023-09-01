package com.sydata.generate.core.utils;

import cn.hutool.core.util.NumberUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: architecture
 * @description:
 * @author: lzq
 * @create: 2023-01-09 17:07
 */
public class CustomNumberUtil {
    public static void main(String[] args) {
        //针对数字类型做加法
        NumberUtil.add(1, 2);//3
        // 针对数字类型做减法
        NumberUtil.sub(1, 1);//0
        //针对数字类型做乘法
        NumberUtil.mul(1, 3);//3
        //针对数字类型做除法，并提供重载方法用于规定除不尽的情况下保留小数位数和舍弃方式。
        NumberUtil.div(1, 3);//0.3333333333
        double te1 = 123456.123456;
        double te2 = 123456.128456;
        NumberUtil.round(te1, 4);//结果:123456.1235
        NumberUtil.round(te2, 4);//结果:123456.1285
        NumberUtil.roundStr(te1, 4);//结果:123456.1235
        NumberUtil.roundStr(te2, 4);//结果:123456.1285
        long c = 299792458;//光速
        String format = NumberUtil.decimalFormat(",###", c);//299,792,458
        // 是否为数字
        NumberUtil.isNumber("1");//true
        //是否为整数
        NumberUtil.isInteger("1.0");//false
        //是否为浮点数
        NumberUtil.isDouble("1.0");//true
        //是否为质数
        NumberUtil.isPrimes(3);//true;
        //生成不重复随机数 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组，返回int[]
        NumberUtil.generateRandomNumber(0, 100, 10);
        //生成不重复随机数 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组，返回Integer[]
        NumberUtil.generateBySet(0, 100, 10);
        //方法根据范围和步进，生成一个有序整数列表
        int[] rangeList = NumberUtil.range(1, 100, 2);//1,3,5,...99
        //将给定范围内的整数添加到已有集合中
        List<Integer> integerList = NumberUtil.appendRange(100, 200, 2
                , new ArrayList<Integer>(Arrays.asList(new Integer[]{1})))
                .stream().collect(Collectors.toList());
        //阶乘
        NumberUtil.factorial(3, 1);//6
        //平方根
        NumberUtil.sqrt(16);//4
        //最大公约数
        NumberUtil.divisor(15, 21);//3
        //最小公倍数
        NumberUtil.multiple(15, 21);//105
        //获得数字对应的二进制字符串
        NumberUtil.getBinaryStr(8);//1000
        //二进制转int
        NumberUtil.binaryToInt("111");//7
        //二进制转long
        NumberUtil.binaryToLong("111");//7
        //比较两个值的大小
        NumberUtil.compare(1, 3);//-1
        //数字转字符串，自动并去除尾小数点儿后多余的0
        NumberUtil.toStr(1.00000);//1
    }
}
