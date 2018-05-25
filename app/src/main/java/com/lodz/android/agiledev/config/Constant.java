package com.lodz.android.agiledev.config;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 常量
 * Created by zhouL on 2018/5/25.
 */
public class Constant {

    /** 共享元素名称 */
    @StringDef({ShareElementName.IMG, ShareElementName.TITLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShareElementName {
        /** 图片 */
        String IMG = "img";
        /** 标题 */
        String TITLE = "title";
    }
}
