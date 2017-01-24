package com.snxun.core.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志打印
 * Created by zhouL on 2016/11/17.
 */
public class PrintLog {

    /** 是否打印日志 */
    private static boolean sIsPrint = true;

    public static void setPrint(boolean isPrint) {
        sIsPrint = isPrint;
    }

    public static void i(String tag, String msg) {
        if (sIsPrint) {
            if (TextUtils.isEmpty(msg)) {
                Log.i(tag, "NULL");
            } else {
                Log.i(tag, msg);
            }
        }
    }


    public static void v(String tag, String msg) {
        if (sIsPrint) {
            if (TextUtils.isEmpty(msg)) {
                Log.v(tag, "NULL");
            } else {
                Log.v(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (sIsPrint) {
            if (TextUtils.isEmpty(msg)) {
                Log.d(tag, "NULL");
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (sIsPrint) {
            if (TextUtils.isEmpty(msg)) {
                Log.w(tag, "NULL");
            } else {
                Log.w(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (sIsPrint) {
            if (TextUtils.isEmpty(msg)) {
                Log.e(tag, "NULL");
            } else {
                Log.e(tag, msg);
            }
        }
    }


}
