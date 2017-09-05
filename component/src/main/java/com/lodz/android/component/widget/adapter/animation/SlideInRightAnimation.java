package com.lodz.android.component.widget.adapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * 右侧进入
 * Created by zhouL on 2017/8/23.
 */
public class SlideInRightAnimation implements BaseAnimation {

    private static final int DEFAULT_DURATION = 300;

    private final int mDuration;

    public SlideInRightAnimation() {
        this(DEFAULT_DURATION);
    }

    public SlideInRightAnimation(int duration) {
        mDuration = duration;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)};
    }

    @Override
    public int getDuration() {
        return mDuration;
    }
}
