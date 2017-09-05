package com.lodz.android.component.widget.adapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * 左侧进入
 * Created by zhouL on 2017/8/23.
 */
public class SlideInLeftAnimation implements BaseAnimation {

    private static final int DEFAULT_DURATION = 300;

    private final int mDuration;

    public SlideInLeftAnimation() {
        this(DEFAULT_DURATION);
    }

    public SlideInLeftAnimation(int duration) {
        mDuration = duration;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)};
    }

    @Override
    public int getDuration() {
        return mDuration;
    }
}
