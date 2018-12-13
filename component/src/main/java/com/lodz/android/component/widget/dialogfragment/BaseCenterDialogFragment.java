package com.lodz.android.component.widget.dialogfragment;

import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;

import com.lodz.android.component.R;

/**
 * 中间DialogFragment基类（缩放动画）
 * Created by zhouL on 2018/12/13.
 */
public abstract class BaseCenterDialogFragment extends BaseDialogFragment{

    @Override
    protected int getAnimations() {
        return hasAnimations() ? R.style.animation_center_in_center_out : super.getAnimations();
    }

    /** 是否有动画 */
    protected boolean hasAnimations() {
        return true;
    }

    @Override
    protected void setDialogWindow(Dialog dialog) {
        super.setDialogWindow(dialog);
        if (dialog == null || dialog.getWindow() == null){
            return;
        }

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
    }
}
