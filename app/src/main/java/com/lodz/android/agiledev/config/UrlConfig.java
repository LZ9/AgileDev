package com.lodz.android.agiledev.config;

import androidx.annotation.Keep;

/**
 * 地址配置
 * Created by zhouL on 2016/11/22.
 */
public class UrlConfig {

    private UrlConfig() {}

    @Keep
    private static class BaseUrls{
        @Keep
        private static final String Release = "http://www.baidu.com/"; // 正式地址
        @Keep
        private static final String Test = "http://www.baidu.com/"; // 测试地址
    }

    /** 正式环境 BASE_URL设置为静态可修改*/
    public static final String BASE_URL = BaseUrls.Release;

}
