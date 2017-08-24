package com.lodz.android.component.widget.adapter.animation;

import android.animation.Animator;
import android.view.View;

/**
 * 动画基类
 * Created by zhouL on 2017/8/23.
 */

public interface BaseAnimation {

    Animator[] getAnimators(View view);

    int getDuration();
}
