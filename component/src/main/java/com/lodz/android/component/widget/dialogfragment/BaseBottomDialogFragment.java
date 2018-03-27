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

public abstract class BaseBottomDialogFragment extends BaseDialogFragment{

    @Override
    protected int getAnimations() {
        return R.style.animation_bottom_in_bottom_out;
    }

    @Override
    protected void setDialogWindow(Dialog dialog) {
        super.setDialogWindow(dialog);
        if (dialog == null || dialog.getWindow() == null){
            return;
        }

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        if (isMatchWidth()){
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }
    }

    /** 是否需要填满宽度 */
    protected boolean isMatchWidth(){
        return true;
    }

}
