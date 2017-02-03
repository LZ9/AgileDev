package com.lodz.android.core.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
    public static void startAnim(Context context, View view, int animResId, int visibility){
        view.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(context, animResId);
        view.startAnimation(animation);
        view.setVisibility(visibility);
    }
}
