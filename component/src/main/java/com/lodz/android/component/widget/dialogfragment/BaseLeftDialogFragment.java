package com.lodz.android.component.widget.dialogfragment;

import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lodz.android.component.R;

/**
 * 底部DialogFragment基类
 * Created by zhouL on 2018/3/26.
 */

public abstract class BaseLeftDialogFragment extends BaseDialogFragment{

    @Override
    protected int getAnimations() {
        return R.style.animation_left_in_left_out;
    }

    @Override
    protected void setDialogWindow(Dialog dialog) {
        super.setDialogWindow(dialog);
        if (dialog == null || dialog.getWindow() == null){
            return;
        }

        Window window = dialog.getWindow();
        window.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        if (isMatchHeight()){
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }
    }

    /** 是否需要填满高度 */
    protected boolean isMatchHeight(){
        return true;
    }

}
