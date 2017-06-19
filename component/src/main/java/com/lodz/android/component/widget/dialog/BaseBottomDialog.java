package com.lodz.android.component.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lodz.android.component.R;


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
    public void show() {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            if (isMatchWidth()){
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(layoutParams);
            }
        }
        super.show();
    }

    /** 是否需要填满宽度 */
    protected boolean isMatchWidth(){
        return true;
    }

}
