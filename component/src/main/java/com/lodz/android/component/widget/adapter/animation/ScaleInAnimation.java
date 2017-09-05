package com.lodz.android.component.widget.adapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * 缩放效果
 * Created by zhouL on 2017/8/23.
 */
public class ScaleInAnimation implements BaseAnimation {

    private static final float DEFAULT_SCALE_FROM = 0.5f;
    private static final int DEFAULT_DURATION = 300;

    private final float mFrom;
    private final int mDuration;

    public ScaleInAnimation() {
        this(DEFAULT_SCALE_FROM, DEFAULT_DURATION);
    }

    public ScaleInAnimation(float from, int duration) {
        mFrom = from;
        mDuration = duration;
    }

    @Override
    public Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
        return new ObjectAnimator[]{scaleX, scaleY};
    }

    @Override
    public int getDuration() {
        return mDuration;
    }
}
