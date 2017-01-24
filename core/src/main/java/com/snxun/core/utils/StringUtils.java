package com.snxun.core.utils;

import android.text.TextUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 字符串帮助类
 * Created by zhouL on 2017/1/22.
 */
public class StringUtils {

    /**
     * 用UTF-8解码
     * @param str 文字
     */
    public static String decodeUtf8(String str){
        try {
            if (!TextUtils.isEmpty(str)){
                return URLDecoder.decode(str, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 用UTF-8编码
     * @param str 文字
     */
    public static String encodeUtf8(String str){
        try {
            if (!TextUtils.isEmpty(str)){
                return URLEncoder.encode(str, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
