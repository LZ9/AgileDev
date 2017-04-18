package com.lodz.android.core.utils;

import android.text.TextUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 根据分隔符将字符串转为列表
     * @param source 字符串
     * @param Separator 分隔符
     */
    public static List<String> getListBySeparator(String source, String Separator) {
        List<String> list = new ArrayList<>();
        while (source.contains(Separator)){
            String value = source.substring(0, source.indexOf(Separator));
            if (!TextUtils.isEmpty(value)){
                list.add(value);
            }
            source = source.substring(source.indexOf(Separator)+1, source.length());
        }
        if (!TextUtils.isEmpty(source)){
            list.add(source);
        }
        return list;
    }
}
