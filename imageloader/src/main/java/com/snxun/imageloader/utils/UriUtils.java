package com.snxun.imageloader.utils;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import com.facebook.common.util.UriUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Uri帮助类
 * Created by zhouL on 2016/11/21.
 */
public class UriUtils {

    /**
     * 解析Url地址
     * @param url 网络地址
     */
    public static Uri parseUrl(String url){
        if (TextUtils.isEmpty(url)){
            return null;
        }
        return Uri.parse(url);
    }

    /**
     * 解析本地文件路径
     * @param filePath 本地文件路径
     */
    public static Uri parseFilePath(String filePath){
        if (TextUtils.isEmpty(filePath)){
            return null;
        }
        return new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(filePath)
                .build();
    }

    /**
     * 解析资源文件id
     * @param resId 资源文件id
     */
    public static Uri parseResId(@DrawableRes int resId){
        return new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
    }

    /**
     * 将文件地址列表转为Uri数组
     * @param list 文件地址列表
     */
    public static ArrayList<Uri> getFilePathUris(List<String> list){
        ArrayList<Uri> uris = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            uris.add(parseFilePath(list.get(i)));
        }
        return uris;
    }

    /**
     * 将网络地址列表转为Uri数组
     * @param list 网络地址列表
     */
    public static ArrayList<Uri> getUrlUris(List<String> list){
        ArrayList<Uri> uris = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            uris.add(parseUrl(list.get(i)));
        }
        return uris;
    }

    /**
     * 将资源文件列表转为Uri数组
     * @param list 资源文件列表
     */
    public static ArrayList<Uri> getResIdUris(List<Integer> list){
        ArrayList<Uri> uris = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            uris.add(parseResId(list.get(i)));
        }
        return uris;
    }
}
