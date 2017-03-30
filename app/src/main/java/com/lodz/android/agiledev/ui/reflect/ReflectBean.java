package com.lodz.android.agiledev.ui.reflect;

/**
 * 反射测试数据
 * Created by zhouL on 2017/3/30.
 */

public class ReflectBean {

    public String name = "Jack";

    protected int age = 30;

    private String nationality = "China";

    public ReflectBean() {
    }

    private ReflectBean(String name) {
        this.name = name;
    }

    protected ReflectBean(String name, int age, String nationality) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    protected int getAge() {
        return age;
    }

    private String getNationality() {
        return nationality;
    }

    private String test(int num) {
        return String.valueOf(num + 1);
    }
}
