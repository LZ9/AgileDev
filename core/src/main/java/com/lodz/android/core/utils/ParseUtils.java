package com.lodz.android.core.utils;

/**
 * 解析数字帮助类
 * Created by zhouL on 2018/5/2.
 */
public class ParseUtils {

    /**
     * 把String解析为Integer，解析失败时返回0
     * @param num 数字
     */
    public static int parseInt(String num){
        try {
            return Integer.parseInt(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 把String解析为Long，解析失败时返回0
     * @param num 数字
     */
    public static Long parseLong(String num){
        try {
            return Long.parseLong(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 把String解析为Float，解析失败时返回0
     * @param num 数字
     */
    public static float parseFloat(String num){
        try {
            return Float.parseFloat(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0.0f;
    }

    /**
     * 把String解析为Double，解析失败时返回0
     * @param num 数字
     */
    public static double parseDouble(String num){
        try {
            return Double.parseDouble(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0.0d;
    }

}
