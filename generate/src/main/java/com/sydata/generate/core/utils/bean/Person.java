package com.sydata.generate.core.utils.bean;

import lombok.Data;

@Data
public class Person {
    // 姓名

    private String name;

    // 薪资

    private int salary;

    // 年龄

    private int age;

    //性别

    private String sex;

    // 地区

    private String area;

    public Person() {
    }

    // 构造方法

    public Person(String name, int salary, int age,String sex,String area) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.area = area;
    }
}

