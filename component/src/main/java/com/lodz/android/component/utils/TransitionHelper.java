package com.lodz.android.component.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.lodz.android.core.utils.ArrayUtils;

import java.util.List;

/**
 * 动画过度帮助类
 * Created by zhouL on 2018/5/24.
 */
public class TransitionHelper {

    /**
     * 使用过度动画跳转
     * @param list 共享元素列表
     */
    @SuppressWarnings("unchecked")
    public static void jumpTransition(@NonNull Activity activity, Intent intent, List<Pair<View, String>> list){
        if (ArrayUtils.isEmpty(list) || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            activity.startActivity(intent);
        }else {
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, list.toArray(new Pair[list.size()])).toBundle());
        }
    }

    /**
     * 使用过度动画跳转
     * @param sharedElement 共享元素对象
     * @param sharedElementName 共享元素名称
     */
    public static void jumpTransition(@NonNull Activity activity, Intent intent, @NonNull View sharedElement, @NonNull String sharedElementName){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            activity.startActivity(intent);
        }else {
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName).toBundle());
        }
    }

    /** 关闭页面 */
    public static void finish(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.finishAfterTransition();
        }else {
            activity.finish();
        }
    }

    /**
     * 设置共享元素
     * @param view 控件
     * @param shareElementName 共享元素名称
     */
    public static void setTransition(View view, String shareElementName){
        ViewCompat.setTransitionName(view, shareElementName);
    }

    /**
     * 设置进入的过度时间
     * @param duration 时间长度（毫秒）
     */
    public static void setEnterTransitionDuration(Activity activity, long duration){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getEnterTransition().setDuration(duration);
        }
    }

    /**
     * 设置退出的过度时间
     * @param duration 时间长度（毫秒）
     */
    public static void setReturnTransitionDuration(Activity activity, long duration){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getReturnTransition().setDuration(duration);
        }
    }
}
