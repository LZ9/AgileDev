package com.lodz.android.core.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormat;

/**
 * 数字格式化帮助类
 * Created by zhouL on 2017/1/23.
 */
public class NumberFormatUtils {

    /** 保留1位小数 */
    public static final String TYPE_ONE_DECIMAL = "#.0";
    /** 保留2位小数 */
    public static final String TYPE_TWO_DECIMAL = "#.00";

    @StringDef({TYPE_ONE_DECIMAL, TYPE_TWO_DECIMAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FormatType {}

    /**
     * 格式化数字
     * @param formatType 格式化类型，例如：{@link #TYPE_ONE_DECIMAL}、{@link #TYPE_TWO_DECIMAL}
     * @param data 数据
     */
    public static String format(String formatType, double data){
        try {
            DecimalFormat format = new DecimalFormat(formatType);
            return format.format(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
