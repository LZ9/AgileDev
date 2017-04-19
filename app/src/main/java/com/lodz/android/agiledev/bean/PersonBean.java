package com.lodz.android.agiledev.bean;

import java.io.Serializable;

/**
 * Created by zhouL on 2017/4/19.
 */

public class PersonBean implements Serializable{

    public String name = "";

    public int age = 0;

    public boolean isMan = false;

    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isMan=" + isMan +
                '}';
    }
}
