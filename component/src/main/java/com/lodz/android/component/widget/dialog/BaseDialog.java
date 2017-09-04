package com.lodz.android.component.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.lodz.android.component.R;


/**
 * 弹框基类
 * Created by zhouL on 2016/12/7.
 */
public abstract class BaseDialog extends Dialog{

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.BaseDialog);
        initDialog(context);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initDialog(context);
    }

    private void initDialog(Context context) {
        onStartInit(context);
        setContentView(getLayoutId());
        findViews();
        setListeners();
        initData();
        setWindowAnimations();
    }

    protected void onStartInit(Context context) {}

    protected abstract int getLayoutId();

    protected abstract void findViews();

    protected void setListeners() {}

    protected void initData() {}

    @StyleRes
    protected int getAnimations() {
        return -1;
    }

    private void setWindowAnimations() {
        if (getWindow() != null && getAnimations() != -1){
            getWindow().setWindowAnimations(getAnimations()); //设置窗口弹出动画
        }
    }

}
