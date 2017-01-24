package com.snxun.component.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.snxun.component.R;
import com.snxun.core.utils.ScreenUtils;

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
        if (getWindow() != null) {
            getWindow().setWindowAnimations(R.style.animation_right_in_right_out); //设置窗口弹出动画
        }
    }


    @Override
    protected void initWindowParam(Window window) {
        if (window != null) {
            window.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.height = ScreenUtils.getScreenHeight(getContext());
            window.setAttributes(layoutParams);
        }
    }
}
