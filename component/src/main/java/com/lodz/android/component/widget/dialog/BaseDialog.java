package com.lodz.android.component.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;

import com.lodz.android.component.R;


/**
 * 弹框基类
 * Created by zhouL on 2016/12/7.
 */
public abstract class BaseDialog extends AppCompatDialog {

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

    /**
     * 设置阴影
     * @param elevation 阴影值
     * @param background 背景（需要设置背景才能设置阴影）
     */
    protected void setElevation(float elevation, Drawable background){
        if (background == null){
            return;
        }
        if (getWindow() == null){
            return;
        }
        if (getWindow().getDecorView() == null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setElevation(elevation);
            getWindow().getDecorView().setBackground(background);
        }
    }

    protected DialogInterface getDialogInterface(){
        return this;
    }
}
