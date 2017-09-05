package com.lodz.android.component.widget.adapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * 底部进入
 * Created by zhouL on 2017/8/23.
 */
public class SlideInBottomAnimation implements BaseAnimation {

    private static final int DEFAULT_DURATION = 300;

    private final int mDuration;

    public SlideInBottomAnimation() {
        this(DEFAULT_DURATION);
    }

    public SlideInBottomAnimation(int duration) {
        mDuration = duration;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)};
    }

    @Override
    public int getDuration() {
        return mDuration;
    }
}
