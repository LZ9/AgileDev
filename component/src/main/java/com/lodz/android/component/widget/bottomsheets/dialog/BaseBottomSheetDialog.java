package com.lodz.android.component.widget.bottomsheets.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lodz.android.core.utils.ReflectUtils;
import com.lodz.android.core.utils.ScreenUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * BottomSheetDialog基类
 * Created by zhouL on 2018/4/23.
 */
public abstract class BaseBottomSheetDialog extends BottomSheetDialog{

    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public BaseBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BaseBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startCreate();
        createContentView(getLayoutId());
        findViews();
        setListeners();
        initData();
        endCreate();
        configStatusBar();
        configBehavior();
    }

    protected void startCreate() {}

    private void createContentView(@LayoutRes int layoutId) {
        View view = getLayoutInflater().inflate(layoutId, null);
        setContentView(view);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void findViews();

    protected void setListeners() {}

    protected void initData() {}

    protected void endCreate() {}

    protected abstract void onBehaviorInit(BottomSheetBehavior behavior);

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

    /** 配置状态栏 */
    private void configStatusBar() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        if (!configTransparentStatusBar()) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return;
        }
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);// 设置底部展示
        int screenHeight = ScreenUtils.getScreenHeight(getContext());
        int statusBarHeight = ScreenUtils.getStatusBarHeight(getContext());
        int navigationBarHeight = ScreenUtils.getNavigationBarHeight(getContext(), window);
        int dialogHeight = screenHeight - statusBarHeight + navigationBarHeight - configTopOffsetPx();//屏幕高度 - 状态栏高度 + 导航栏 - 偏移量高度
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }

    /** 配置BottomSheetBehavior */
    private void configBehavior() {
        Class<?> cls = ReflectUtils.getClassForName("com.google.android.material.bottomsheet.BottomSheetDialog");
        if (cls == null){
            return;
        }
        BottomSheetBehavior behavior = (BottomSheetBehavior) ReflectUtils.getFieldValue(cls, this, "behavior");
        if (behavior != null) {
            onBehaviorInit(behavior);//回调BottomSheetBehavior
        }
    }

    /** 配置是否透明状态栏（默认是） */
    protected boolean configTransparentStatusBar(){
        return true;
    }

    /** 配置布局高度偏移量（默认0） */
    protected int configTopOffsetPx(){
        return 0;
    }
}
