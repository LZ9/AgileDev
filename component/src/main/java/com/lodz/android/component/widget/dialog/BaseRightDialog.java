package com.lodz.android.component.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;

import com.lodz.android.component.R;

/**
 * 右侧弹框基类
 * Created by zhouL on 2016/12/22.
 */
public abstract class BaseRightDialog extends BaseDialog {

    public BaseRightDialog(Context context) {
        super(context);
        setWindowAnimations();
    }

    public BaseRightDialog(Context context, int themeResId) {
        super(context, themeResId);
        setWindowAnimations();
    }

    private void setWindowAnimations() {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.animation_right_in_right_out); //设置窗口弹出动画
        }
    }

    @Override
    public void show() {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }
        super.show();
    }

}
