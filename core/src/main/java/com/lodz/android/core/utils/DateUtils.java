package com.lodz.android.core.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.StringDef;

/**
 * 时间格式化帮助类
 * Created by zhouL on 2016/12/19.
 */
public class DateUtils {

    public static final String TYPE_1 = "HH:mm";
    public static final String TYPE_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String TYPE_3 = "yyyyMMddHHmmssSSS";
    public static final String TYPE_4 = "yyyyMMddHHmmss";
    public static final String TYPE_5 = "yyyyMMdd";
    public static final String TYPE_6 = "yyyy-MM-dd";
    public static final String TYPE_7 = "yyyy-MM-dd-HH-mm-ss";
    public static final String TYPE_8 = "HH:mm:ss";
    public static final String TYPE_9 = "yyyy-MM-dd HH-mm-ss";
    public static final String TYPE_10 = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String TYPE_11 = "yyyy-MM-dd HH:mm";


    @StringDef({TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, TYPE_7, TYPE_8, TYPE_9, TYPE_10, TYPE_11})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FormatType {}

    /**
     * 格式化当前时间
     * @param formatType 格式化类型，例如：{@link #TYPE_1}到{@link #TYPE_11}
     */
    public static String getCurrentFormatString(String formatType) {
        return getFormatString(formatType, new Date(System.currentTimeMillis()));
    }

    /**
     * 格式化当前时间
     * @param formatType 格式化类型，例如：{@link #TYPE_1}到{@link #TYPE_11}
     * @param date 日期
     */
    public static String getFormatString(String formatType, Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType, Locale.CHINA);
            return format.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将格式化后的时间转成Date
     * @param formatType source对应的格式化类型，例如：{@link #TYPE_1}到{@link #TYPE_11}
     * @param source 时间数据
     */
    public static Date parseFormatDate(String formatType, String source) {
        SimpleDateFormat format = new SimpleDateFormat(formatType, Locale.CHINA);
        try {
            return format.parse(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 改变日期格式
     * @param oldFormatType 旧格式，例如：{@link #TYPE_1}到{@link #TYPE_11}
     * @param newFormatType 新格式，例如：{@link #TYPE_1}到{@link #TYPE_11}
     * @param source 时间数据
     */
    public static String changeFormatString(String oldFormatType, String newFormatType, String source) {
        Date date = parseFormatDate(oldFormatType, source);
        if (date == null){
            return "";
        }
        return getFormatString(newFormatType, date);
    }

    /**
     * 获取之后n天的日期
     * @param formatType 原日期格式，例如：{@link #TYPE_1}到{@link #TYPE_11}
     * @param source 原日期
     * @param n 之后的天数
     */
    public static String getAfterDay(String formatType, String source, int n){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(parseFormatDate(formatType, source));
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + n);
            return parseFormatCalendar(formatType, cal);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取之前n天的日期
     * @param formatType 原日期格式，例如：{@link #TYPE_1}到{@link #TYPE_11}
     * @param source 原日期
     * @param n 之前的天数
     */
    public static String getBeforeDay(String formatType, String source, int n){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(parseFormatDate(formatType, source));
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - n);
            return parseFormatCalendar(formatType, cal);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据日历获取对应格式的时间
     * @param formatType 时间格式，例如：{@link #TYPE_1}到{@link #TYPE_11}
     * @param calendar 日历
     */
    public static String parseFormatCalendar(String formatType, Calendar calendar){
        try {
            return getFormatString(formatType, new Date(calendar.getTimeInMillis()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
