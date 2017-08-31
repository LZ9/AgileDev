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
public abstract class BaseLeftDialog extends BaseDialog {

    public BaseLeftDialog(Context context) {
        super(context);
        setWindowAnimations();
    }

    public BaseLeftDialog(Context context, int themeResId) {
        super(context, themeResId);
        setWindowAnimations();
    }

    private void setWindowAnimations() {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.animation_left_in_left_out); //设置窗口弹出动画
        }
    }

    @Override
    public void show() {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            if (isMatchHeight()) {
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
