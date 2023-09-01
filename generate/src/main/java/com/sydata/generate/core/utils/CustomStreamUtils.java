package com.sydata.generate.core.utils;

import com.google.common.collect.Lists;
import com.sydata.generate.core.utils.bean.Person;
import com.sydata.generate.core.utils.bean.Student;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * --@Description:stream工具类
 * 	1 Stream概述
 * 	2 Stream的创建
 * 	3 Stream的使用
 * 	案例使用的员工类
     * 	3.1 遍历/匹配（foreach/find/match）
     * 	3.2 筛选（filter）
     * 	3.3 聚合（max/min/count)
     * 	3.4 映射(map/flatMap)
     * 	3.5 归约(reduce)
     * 	3.6 收集(collect)
     * 	3.6.1 归集(toList/toSet/toMap)
     * 	3.6.2 统计(count/averaging)
     * 	3.6.3 分组(partitioningBy/groupingBy)
     * 	3.6.4 接合(joining)
     * 	3.6.5 归约(reducing)
     * 	3.7 排序(sorted)
     * 	3.8 提取/组合
 * --类名称：   DateUtil
 * --创建时间： 2019年12月24日 下午3:03:28
 * @author lzq
 */
public class CustomStreamUtils {
    //============================================================顺序流和并行流==============================================================================

    public void getStream() {

        List<String> list = Arrays.asList("a", "b", "c");


        // 创建一个顺序流
        Stream<String> stream = list.stream();
        // 创建一个并行流 用于多线程
        Stream<String> parallelStream = list.parallelStream();
        Optional<String> findFirst = list.stream().parallel().filter(x->x.length()>6).findFirst();

        // 使用java.util.Arrays.stream(T[] array)方法用数组创建流
        int[] array={1,3,5,6,8};
        IntStream intStream = Arrays.stream(array);


        // 使用Stream的静态方法：of()、iterate()、generate()
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5, 6);
        // 生成n个数字流，初始值是0，之后的数字每次加3，获取4个数字流后截止
        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(4);
        stream2.forEach(System.out::println);
        // 生成n个随机数字流，获取3个数字流后截止
        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
        stream3.forEach(System.out::println);
    }

//     0 3 6 9
//     0.6796156909271994
//     0.1914314208854283
//     0.8116932592396652


    //============================================================流也可以进行合并、去重、限制、跳过等操作。==============================================================================

    public void customStream() {
        String[] arr1 = { "a", "b", "c", "d" };
        String[] arr2 = { "d", "e", "f", "g" };

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        // concat:合并两个流 distinct：去重
        List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
        // limit：限制从流中获得前n个数据
        List<Integer> collect = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
        // skip：跳过前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());

        System.out.println("流合并：" + newList);
        System.out.println("limit：" + collect);
        System.out.println("skip：" + collect2);
    }

//    流合并：[a, b, c, d, e, f, g]
//    limit：[1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
//    skip：[3, 5, 7, 9, 11]

    //============================================================Comparator==============================================================================

    public void comparator() {

        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());


        List<Integer> list2 = Arrays.asList(7, 6, 9, 4, 11, 6);
        // 自然排序
        Optional<Integer> max2 = list2.stream().max(Integer::compareTo);
        // 自定义排序
        Optional<Integer> max3 = list2.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序的最大值：" + max2.get());
        System.out.println("自定义排序的最大值：" + max3.get());
    }

//    自然排序的最大值：11
//    自定义排序的最大值：11


    //============================================================（遍历/匹配（foreach/find/match）==============================================================

    public void traversalAndMatch() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
        // 为了判断查询的类对象是否存在，采用此方法
        if (findFirst.isPresent()) {
            System.out.println("匹配第一个值：" + findFirst.get());
        }
        // 匹配任意（适用于并行流）
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x > 6);
        System.out.println("匹配第一个值：" + findFirst.get());
        System.out.println("匹配任意一个值：" + findAny.get());
        System.out.println("是否存在大于6的值：" + anyMatch);
    }

    //============================================================筛选==============================================================================

    // x->x>6
    // x->{ return Object}
    /**
     * List<Integer>筛选
     *
     * @return
     */
    public void listFilter() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
        // 匹配任意（适用于并行流）
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x > 6);
        System.out.println("匹配第一个值：" + findFirst.get());
        System.out.println("匹配任意一个值：" + findAny.get());
        System.out.println("是否存在大于6的值：" + anyMatch);
        // stream
        Stream<Integer> stream = list.stream();
        stream.filter(x -> x > 7).forEach(System.out::println);
        // list中大于6的元素个数
        long count = list.stream().filter(x -> x > 6).count();
        System.out.println("list中大于6的元素个数：" + count);

        // 每个元素大写
        String[] strArr = { "abcd", "bcdd", "defde", "fTr" };
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());
        // 每个元素+3
        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());
        System.out.println("每个元素大写：" + strList);
        System.out.println("每个元素+3：" + intListNew);
    }


    /**
     * List<Object>筛选
     *
     * @return
     */
    public void listObjectFilter() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        List<String> fiterList = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName)
                .collect(Collectors.toList());
        System.out.print("高于8000的员工姓名：" + fiterList);

        Optional<Person> max = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("员工工资最大值：" + max.get().getSalary());

        // 不改变原来员工集合的方式
        List<Person> personListNew = personList.stream().map(person -> {
            Person personNew = new Person(person.getName(), 0, 0, null, null);
            personNew.setSalary(person.getSalary() + 10000);
            return personNew;
        }).collect(Collectors.toList());
        System.out.println("一次改动前：" + personList.get(0).getName() + "-->" + personList.get(0).getSalary());
        System.out.println("一次改动后：" + personListNew.get(0).getName() + "-->" + personListNew.get(0).getSalary());

        // 改变原来员工集合的方式
        List<Person> personListNew2 = personList.stream().map(person -> {
            person.setSalary(person.getSalary() + 10000);
            return person;
        }).collect(Collectors.toList());
        System.out.println("二次改动前：" + personList.get(0).getName() + "-->" + personListNew.get(0).getSalary());
        System.out.println("二次改动后：" + personListNew2.get(0).getName() + "-->" + personListNew.get(0).getSalary());
    }

//    一次改动前：Tom–>8900
//    一次改动后：Tom–>18900
//    二次改动前：Tom–>18900
//    二次改动后：Tom–>18900


    //============================================================max/min/count==============================================================================
    /**
     * 统计list集合数值类型的最大
     * 统计list集合数值类型的最小
     * 统计list集合数值类型的平均
     * count
     * @return
     */
    public void getMaxListObj() {
        ArrayList<Double> maxList = Lists.newArrayList();
        double dSum = maxList.stream().mapToDouble(Double::doubleValue).sum();
        double dMax = maxList.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
        double dMin = maxList.stream().mapToDouble(Double::doubleValue).min().getAsDouble();

        ArrayList<BigDecimal> bdMaxList = Lists.newArrayList();
        double dBSum = bdMaxList.stream().mapToDouble(BigDecimal::doubleValue).sum();
        double dBAvg = bdMaxList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
        double dBMax = bdMaxList.stream().mapToDouble(BigDecimal::doubleValue).max().getAsDouble();
        double lBSum = bdMaxList.stream().mapToDouble(BigDecimal::longValue).sum();
        double lBMin = bdMaxList.stream().mapToDouble(BigDecimal::longValue).min().getAsDouble();
        double lBAvg = bdMaxList.stream().mapToDouble(BigDecimal::longValue).average().getAsDouble();

        ArrayList<Person> oMaxList = Lists.newArrayList();
        double oMax = oMaxList.stream().mapToDouble(Person::getAge).max().getAsDouble();
        double oMin = oMaxList.stream().mapToDouble(Person::getAge).min().getAsDouble();
        double oAvg = oMaxList.stream().mapToDouble(Person::getAge).average().getAsDouble();

        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        long num = list.stream().filter(x -> x > 6).count();
        System.out.println("list中大于6的元素个数：" + num);
    }


    /**
     * 最长的字符串
     *
     * @return
     */
    public void getMaxLength() {
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");

        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());
    }

    //============================================================映射(map/flatMap)==============================================================================

    // map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
    // flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。

    public void map1() {
        List<String> stringList = Arrays.asList("hello", "world");
        List<String[]> collect = stringList.stream()
                .map(str -> str.split(""))
                .distinct().collect(Collectors.toList());
        collect.forEach(col-> System.out.println(Arrays.toString(col)));
    }

    public void map2() {
        String[] strArr = { "abcd", "bcdd", "defde", "fTr" };
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());

        System.out.println("每个元素大写：" + strList);
        System.out.println("每个元素+3：" + intListNew);
    }

//    每个元素大写：[ABCD, BCDD, DEFDE, FTR]
//    每个元素+3：[4, 6, 8, 10, 12, 14]

    public void map3() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        // 不改变原来员工集合的方式
        List<Person> personListNew = personList.stream().map(person -> {
            Person personNew = new Person(person.getName(), 0, 0, null, null);
            personNew.setSalary(person.getSalary() + 10000);
            return personNew;
        }).collect(Collectors.toList());
        System.out.println("一次改动前：" + personList.get(0).getName() + "-->" + personList.get(0).getSalary());
        System.out.println("一次改动后：" + personListNew.get(0).getName() + "-->" + personListNew.get(0).getSalary());

        // 改变原来员工集合的方式
        List<Person> personListNew2 = personList.stream().map(person -> {
            person.setSalary(person.getSalary() + 10000);
            return person;
        }).collect(Collectors.toList());
        System.out.println("二次改动前：" + personList.get(0).getName() + "-->" + personListNew.get(0).getSalary());
        System.out.println("二次改动后：" + personListNew2.get(0).getName() + "-->" + personListNew.get(0).getSalary());
    }

//    一次改动前：Tom–>8900
//    一次改动后：Tom–>18900
//    二次改动前：Tom–>18900
//    二次改动后：Tom–>18900

    public void flatMap1() {
        List<String> stringList = Arrays.asList("hello", "world");
        List<String> collect = stringList.stream()
                .map(str -> str.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    public void flatMap2() {
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
    }

//    处理前的集合：[m-k-l-a, 1-3-5]
//    处理后的集合：[m, k, l, a, 1, 3, 5]

    //============================================================归约(reduce)==============================================================================
    // reduce 方法的第一个参数 0 是 identity ，此参数用来保存归并参数的初始值，当Stream 为空时也是默认的返回值。

    public void reduce1() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        // 求和方式1
        Optional<Integer> sum = list.stream().reduce((x, y) -> x + y);
        // 求和方式2
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        // 求和方式3
        Integer sum3 = list.stream().reduce(0, Integer::sum);

        // 求乘积
        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);

        // 求最大值方式1
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);
        // 求最大值写法2
        Integer max2 = list.stream().reduce(1, Integer::max);

        System.out.println("list求和：" + sum.get() + "," + sum2.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + max.get() + "," + max2);
    }
    // list求和：29,29,29
    // list求积：2112
    // list求和：11,11


    public void reduce2() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        // 求工资之和方式1：
        Optional<Integer> sumSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        // 求工资之和方式2：
        Integer sumSalary2 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(),
                (sum1, sum2) -> sum1 + sum2);
        // 求工资之和方式3：
        Integer sumSalary3 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);

        // 求最高工资方式1：
        Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                Integer::max);
        // 求最高工资方式2：
        Integer maxSalary2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                (max1, max2) -> max1 > max2 ? max1 : max2);

//        BigDecimal agreConAmt = personList.stream()
//                .map(Person::getSalary)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("工资之和：" + sumSalary.get() + "," + sumSalary2 + "," + sumSalary3);
        System.out.println("最高工资：" + maxSalary + "," + maxSalary2);
    }
    // 工资之和：49300,49300,49300
    // 最高工资：9500,9500

    //============================================================收集(collect)归集(toList/toSet/toMap)======================================================

    public void collect(String[] args) {
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        // Set存取无序，元素唯一
        Set<Integer> set = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));

        Map<?, Person> map = personList.stream().filter(p -> p.getSalary() > 8000)
                .collect(Collectors.toMap(Person::getName, p -> p));
        System.out.println("toList:" + listNew);
        System.out.println("toSet:" + set);
        System.out.println("toMap:" + map);
    }

//    toList：[6, 4, 6, 6, 20]
//    toSet：[4, 20, 6]
//    toMap：{Tom=mutest.Person@5fd0d5ae, Anni=mutest.Person@2d98a335}

    //============================================================统计(count/averaging)==============================================================================

//    计数：count
//    平均值：averagingInt、averagingLong、averagingDouble
//    最值：maxBy、minBy
//    求和：summingInt、summingLong、summingDouble
//    统计以上所有：summarizingInt、summarizingLong、summarizingDouble
//    案例：统计员工人数、平均工资、工资总额、最高工资。

    public void count() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        // 求总数
        Long count = personList.stream().collect(Collectors.counting());
        // 求平均工资
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        // 求最高工资
        Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        // 求工资之和
        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));
        // 一次性统计所有信息
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

//        BigDecimal projectScoreSum = zbFilters.stream().map(e->e.getProjectScore()).reduce(BigDecimal.ZERO, BigDecimal::add);
//        Assert.state(projectScoreSum.compareTo(new BigDecimal(100))>=0, "模版指标分值小于100，不能发布！");

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("员工工资总和：" + sum);
        System.out.println("员工工资所有统计：" + collect);
    }

//    员工总数：3
//    员工平均工资：7900.0
//    员工工资总和：23700
//    员工工资所有统计：DoubleSummaryStatistics{count=3, sum=23700.000000,min=7000.000000, average=7900.000000, max=8900.000000}

    //============================================================分组(partitioningBy/groupingBy)==============================================================================

//    分区：将stream按条件分为两个Map，比如员工按薪资是否高于8000分为两部分。
//    分组：将集合分为多个Map，比如员工按性别分组。有单级分组和多级分组。

    public void groupingBy() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 18,"male", "New York"));
        personList.add(new Person("Jack", 7000, 18,"male", "Washington"));
        personList.add(new Person("Lily", 7800, 18,"female", "Washington"));
        personList.add(new Person("Anni", 8200, 18,"female", "New York"));
        personList.add(new Person("Owen", 9500, 18,"male", "New York"));
        personList.add(new Person("Alisa", 7900, 18,"female", "New York"));

        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> part = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
        // 将员工按性别分组
        Map<String, List<Person>> group = personList.stream().collect(Collectors.groupingBy(Person::getSex));
        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> group2 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);
    }

//    员工按薪资是否大于8000分组情况：{false=[mutest.Person@2d98a335, mutest.Person@16b98e56, mutest.Person@7ef20235], true=[mutest.Person@27d6c5e0, mutest.Person@4f3f5b24, mutest.Person@15aeb7ab]}
//    员工按性别分组情况：{female=[mutest.Person@16b98e56, mutest.Person@4f3f5b24, mutest.Person@7ef20235], male=[mutest.Person@27d6c5e0, mutest.Person@2d98a335, mutest.Person@15aeb7ab]}
//    员工按性别、地区：{female={New York=[mutest.Person@4f3f5b24, mutest.Person@7ef20235], Washington=[mutest.Person@16b98e56]}, male={New York=[mutest.Person@27d6c5e0, mutest.Person@15aeb7ab], Washington=[mutest.Person@2d98a335]}}

    //============================================================接合(joining)==============================================================================

//    joining可以将stream中的元素用特定的连接符（没有的话，则直接连接）连接成一个字符串。

    public void joining() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        String names = personList.stream().map(p -> p.getName()).collect(Collectors.joining(","));
        System.out.println("所有员工的姓名：" + names);
        List<String> list = Arrays.asList("A", "B", "C");
        String string = list.stream().collect(Collectors.joining("-"));
        System.out.println("拼接后的字符串：" + string);
    }

//    所有员工的姓名：Tom,Jack,Lily
//    拼接后的字符串：A-B-C


    //============================================================归约(reducing)==============================================================================

//    Collectors类提供的reducing方法，相比于stream本身的reduce方法，增加了对自定义归约的支持

    public void reducing() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        // 每个员工减去起征点后的薪资之和（这个例子并不严谨，但一时没想到好的例子）
        Integer sum = personList.stream().collect(Collectors.reducing(0, Person::getSalary, (i, j) -> (i + j - 5000)));
        System.out.println("员工扣税薪资总和：" + sum);

        // stream的reduce
        Optional<Integer> sum2 = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        System.out.println("员工薪资总和：" + sum2.get());
    }

//    员工扣税薪资总和：8700
//    员工薪资总和：23700

    //============================================================排序(sorted)==============================================================================

//    sorted()：自然排序，流中元素需实现Comparable接口
//    sorted(Comparator com)：Comparator排序器自定义排序

    /**
     * List<Student></>排序
     *
     * @return
     */
    public static void listObjectOrder() {
        // list.stream().sorted()
        // list.stream().sorted(Comparator.reverseOrder())
        // list.stream().sorted(Comparator.comparing(Student::getAge))
        // list.stream().sorted(Comparator.comparing(Student::getAge).reversed())
        List<Student> list = new ArrayList<Student>();
        list.add(new Student(1, "Mahesh", 12));
        list.add(new Student(2, "Suresh", 15));
        list.add(new Student(3, "Nilesh", 10));

        System.out.println("---Natural Sorting by Name---");
        List<Student> slist = list.stream().sorted().collect(Collectors.toList());
        slist.forEach(e -> System.out.println("Id:"+ e.getId()+", Name: "+e.getName()+", Age:"+e.getAge()));

        System.out.println("---Natural Sorting by Name in reverse order---");
        slist = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        slist.forEach(e -> System.out.println("Id:"+ e.getId()+", Name: "+e.getName()+", Age:"+e.getAge()));

        System.out.println("---Sorting using Comparator by Age---");
        slist = list.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        slist.forEach(e -> System.out.println("Id:"+ e.getId()+", Name: "+e.getName()+", Age:"+e.getAge()));

        System.out.println("---Sorting using Comparator by Age with reverse order---");
        slist = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        slist.forEach(e -> System.out.println("Id:"+ e.getId()+", Name: "+e.getName()+", Age:"+e.getAge()));
    }

    /**
     * Map排序
     *
     * @return
     */
    public void mapOrder() {
        Map<Integer, String> map = new HashMap<>();
        map.put(15, "Mahesh");
        map.put(10, "Suresh");
        map.put(30, "Nilesh");

        System.out.println("---Sort by Map Value---");
        map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(e -> System.out.println("Key: "+ e.getKey() +", Value: "+ e.getValue()));

        System.out.println("---Sort by Map Key---");
        map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> System.out.println("Key: "+ e.getKey() +", Value: "+ e.getValue()));
    }

    /**
     * Map<Object></>排序
     *
     * @return
     */
    public void mapObjectOrder() {
        Map<Integer, Student> map = new HashMap<>();
        map.put(1, new Student(1, "Mahesh", 12));
        map.put(2, new Student(2, "Suresh", 15));
        map.put(3, new Student(3, "Nilesh", 10));
        //Map Sorting by Value i.e student's natural ordering i.e by name
        map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue))
        .forEach(e -> {
            Integer key = (Integer)e.getKey();
            Student std = (Student)e.getValue();
            System.out.println("Key: " + key +", value: ("+ std.getId() +", "+ std.getName()+", "+ std.getAge()+")");
        });
    }


    /**
     * 自定义排序
     *
     * @return
     */
    public void customOrder(){
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 自然排序
        Optional<Integer> max = list.stream().max(Integer::compareTo);
        // 自定义排序
        Optional<Integer> max2 = list.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序的最大值：" + max.get());
        System.out.println("自定义排序的最大值：" + max2.get());
    }
}
