package com.lodz.android.component.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.lodz.android.component.R;


/**
 * 弹框基类
 * Created by zhouL on 2016/12/7.
 */
public abstract class BaseDialog extends Dialog{

    public BaseDialog(Context context) {
        super(context, R.style.BaseDialog);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initWindowParam(getWindow());
    }

    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
        initWindowParam(getWindow());
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initWindowParam(getWindow());
    }

    /** 初始化弹框布局 */
    protected void initWindowParam(Window window){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        findViews();
        setListeners();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void findViews();

    protected void setListeners() {}

    protected void initData() {}


}
