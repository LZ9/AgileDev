package com.lodz.android.component.widget.bottomsheets.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * BottomSheetDialog基类
 * Created by zhouL on 2018/4/23.
 */
public abstract class BaseBottomSheetDialog extends BottomSheetDialog{

    private BottomSheetBehavior mBottomSheetBehavior;

    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
        initDialog(context);
    }

    public BaseBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
        initDialog(context);
    }

    public BaseBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog(context);
    }

    private void initDialog(Context context) {
        onStartInit(context);
        createContentView(getLayoutId());
        findViews();
        setListeners();
        initData();
    }

    protected void onStartInit(Context context) {}

    private void createContentView(@LayoutRes int layoutId) {
        View view = getLayoutInflater().inflate(layoutId, null);
        setContentView(view);
        mBottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void findViews();

    protected void setListeners() {}

    protected void initData() {}

    protected BottomSheetBehavior getBehavior(){
        return mBottomSheetBehavior;
    }

    protected DialogInterface getDialogInterface(){
        return this;
    }

    /**
     * 设置背景蒙版
     * @param value 蒙版透明度（0~1）
     */
    protected void setDim(float value){
        if (getWindow() != null){
            getWindow().setDimAmount(value);
        }
    }
}
