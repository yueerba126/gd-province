package com.sydata.generate.core.utils;

import org.apache.commons.collections4.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: architecture
 * @description:
 * @author: lzq
 * @create: 2023-01-09 16:57
 */
public class CustomCollectionUtils {
    public static void main(String[] args) {
        String[] arrA = new String[]{"1", "2", "3"};
        String[] arrB = new String[]{"1", "a", "b"};
        List<String> listA = Arrays.asList(arrA);
        List<String> listB = Arrays.asList(arrB);

        // 判断集合是否为 空
        System.out.println(CollectionUtils.isEmpty(listA));// false
        System.out.println(CollectionUtils.isEmpty(listB));// false

        // 判断集合是否为 不为空
        System.out.println(CollectionUtils.isNotEmpty(listA));// true
        System.out.println(CollectionUtils.isNotEmpty(listB));// true

        // 两个集合的比较
        System.out.println(CollectionUtils.isEqualCollection(listA, listB));// false

        // 集合的操作
        // 取并集
        System.out.println(CollectionUtils.union(listA, listB));// [1, a, 2, b, 3]
        // 取交集
        System.out.println(CollectionUtils.intersection(listA, listB));// [1]
        // 取交集的补集
        System.out.println(CollectionUtils.disjunction(listA, listB));// [a, 2, b, 3]
        // 取集合相减
        System.out.println(CollectionUtils.subtract(listA, listB));// [2, 3]
        System.out.println(CollectionUtils.subtract(listB, listA));// [a, b]

        List<String> listC = new ArrayList<>();
        listC.add("1");
        listC.add("2");
        listC.add("3");
        String[] arrC = {"4", "5", "6"};
        // 向集合中添加值
        CollectionUtils.addAll(listC, arrC);
        System.out.println(listC);// [1, 2, 3, 4, 5, 6]

    }
}
