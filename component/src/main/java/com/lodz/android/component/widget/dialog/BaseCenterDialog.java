package com.lodz.android.component.widget.dialog;

import android.content.Context;

import com.lodz.android.component.R;


/**
 * 中间弹框基类（缩放动画）
 * Created by zhouL on 2016/12/7.
 */
public abstract class BaseCenterDialog extends BaseDialog{

    public BaseCenterDialog(Context context) {
        super(context);
    }

    public BaseCenterDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getAnimations() {
        return R.style.animation_center_in_center_out;
    }
}
