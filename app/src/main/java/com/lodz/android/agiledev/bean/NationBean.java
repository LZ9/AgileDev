package com.lodz.android.agiledev.bean;

import com.lodz.android.core.array.Groupable;

/**
 * 国家数据类
 * Created by zhouL on 2018/6/1.
 */
public class NationBean implements Groupable{

    /** 国籍代码 */
    public String code = "";
    /** 名称 */
    public String name = "";
    /** 拼音首字母缩写 */
    public String pinYin = "";

    @Override
    public String getSortStr() {
        return code.toUpperCase();
    }

    /** 获取标题 */
    public String getTitle(){
        return code + "-" + name;
    }
}
