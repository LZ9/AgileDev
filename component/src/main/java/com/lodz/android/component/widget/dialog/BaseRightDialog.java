package com.lodz.android.component.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lodz.android.component.R;

/**
 * 右侧弹框基类
 * Created by zhouL on 2016/12/22.
 */
public abstract class BaseRightDialog extends BaseDialog {

    public BaseRightDialog(Context context) {
        super(context);
    }

    public BaseRightDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getAnimations() {
        return R.style.animation_right_in_right_out;
    }

    @Override
    public void show() {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            if (isMatchHeight()){
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(layoutParams);
            }
        }
        super.show();
    }

    /** 是否需要填满高度 */
    protected boolean isMatchHeight(){
        return true;
    }

}
