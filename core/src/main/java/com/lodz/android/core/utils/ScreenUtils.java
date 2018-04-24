package com.lodz.android.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 屏幕信息帮助类
 * Created by zhouL on 2016/11/17.
 */
public class ScreenUtils {

    /**
     * 获得屏幕宽度
     * @param context 上下文
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null){
            return 0;
        }
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     * @param context 上下文
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null){
            return 0;
        }
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 判断是否存在NavigationBar
     * @param activity Activity
     */
    public static boolean hasNavigationBar(Activity activity) {
        int decorViewHeight = activity.getWindow().getDecorView().getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int useableScreenHeight = dm.heightPixels;
        return decorViewHeight != useableScreenHeight;
    }

    /**
     * 获取虚拟按键高度
     * @param activity Activity
     */
    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int id = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavigationBar(activity)) {
            return resources.getDimensionPixelSize(id);
        }
        return 0;
    }

    /**
     * 获取状态栏高度
     * @param context 上下文
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
