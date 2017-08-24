package com.lodz.android.component.widget.adapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * 淡入效果
 * Created by zhouL on 2017/8/23.
 */
public class AlphaInAnimation implements BaseAnimation{

    private static final float DEFAULT_ALPHA_FROM = 0f;
    private static final int DEFAULT_DURATION = 300;

    private final float mFrom;
    private final int mDuration;

    public AlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM, DEFAULT_DURATION);
    }

    public AlphaInAnimation(float from, int duration) {
        mFrom = from;
        mDuration = duration;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }

    @Override
    public int getDuration() {
        return mDuration;
    }
}
