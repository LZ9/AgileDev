package com.lodz.android.agiledev.bean;

import com.lodz.android.core.array.Groupable;

/**
 * 主页数据
 * Created by zhouL on 2018/7/16.
 */
public class MainBean implements Groupable {

    /** 标题名称 */
    private String titleName = "";
    /** 跳转的类 */
    private Class<?> cls;
    /** 索引文字 */
    private String indexText = "#";

    public MainBean(String titleName, String indexText, Class<?> cls) {
        this.titleName = titleName;
        this.indexText = indexText;
        this.cls = cls;
    }

    public String getTitleName() {
        return titleName;
    }

    public Class<?> getCls() {
        return cls;
    }

    public String getIndexText() {
        return indexText;
    }

    @Override
    public String getSortStr() {
        return indexText;
    }
}
