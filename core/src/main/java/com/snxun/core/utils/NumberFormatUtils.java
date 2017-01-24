package com.snxun.core.utils;

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
    public static final String Type_One_Decimal = "#.0";
    /** 保留2位小数 */
    public static final String Type_Two_Decimal = "#.00";

    @StringDef({Type_One_Decimal, Type_Two_Decimal})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FormatType {}

    /**
     * 格式化数字
     * @param formatType 格式化类型
     * @param data 数据
     */
    public static String format(@FormatType String formatType, double data){
        DecimalFormat format = new DecimalFormat(formatType);
        return format.format(data);
    }

}