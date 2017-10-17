package com.lodz.android.core.utils;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

/**
 * 动画帮助类
 * Created by zhouL on 2016/12/7.
 */
public class AnimUtils {

    /**
     * 开始动画
     * @param context 上下文
     * @param view 控件
     * @param animResId 动画资源id
     * @param visibility 动画结束后的显隐状态
     */
    public static void startAnim(Context context, View view, @AnimRes int animResId, int visibility){
        view.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(context, animResId);
        view.startAnimation(animation);
        view.setVisibility(visibility);
    }

    public static void startRotateSelf(View view, float fromDegrees, float toDegrees, long duration, boolean fillAfter){
        view.clearAnimation();
        Animation animation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animation.setFillAfter(fillAfter);//设置为true，动画转化结束后被应用
        view.startAnimation(animation);//开始动画
    }

}
