package com.lodz.android.agiledev.ui.rvanim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.lodz.android.component.widget.adapter.animation.BaseAnimation;

/**
 * 缩放淡入
 * Created by zhouL on 2017/9/6.
 */

public class ScaleAlphaInAnimation implements BaseAnimation {

    private static final float DEFAULT_ALPHA_FROM = 0f;
    private static final float DEFAULT_SCALE_FROM = 0.5f;
    private static final int DEFAULT_DURATION = 300;

    private final float mAlphaFrom;
    private final float mScaleFrom;
    private final int mDuration;

    public ScaleAlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM, DEFAULT_SCALE_FROM, DEFAULT_DURATION);
    }

    public ScaleAlphaInAnimation(float alphaFrom, float scaleFrom, int duration) {
        mAlphaFrom = alphaFrom;
        mScaleFrom = scaleFrom;
        mDuration = duration;
    }

    @Override
    public Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mScaleFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mScaleFrom, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", mAlphaFrom, 1f);
        return new ObjectAnimator[]{scaleX, scaleY, alpha};
    }

    @Override
    public int getDuration() {
        return mDuration;
    }

}
