package com.snxun.component.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.snxun.component.R;
import com.snxun.core.utils.ScreenUtils;


/**
 * 底部弹框基类
 * Created by zhouL on 2016/12/7.
 */
public abstract class BaseBottomDialog extends BaseDialog{

    public BaseBottomDialog(Context context) {
        super(context);
        setWindowAnimations();
    }

    public BaseBottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        setWindowAnimations();
    }

    private void setWindowAnimations(){
        if (getWindow() != null){
            getWindow().setWindowAnimations(R.style.animation_bottom_in_bottom_out); //设置窗口弹出动画
        }
    }


    @Override
    protected void initWindowParam(Window window) {
        if (window != null){
            window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = ScreenUtils.getScreenWidth(getContext());
            window.setAttributes(layoutParams);
        }
    }

}
