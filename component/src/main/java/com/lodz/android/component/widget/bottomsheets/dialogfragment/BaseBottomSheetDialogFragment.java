package com.lodz.android.component.widget.bottomsheets.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lodz.android.component.R;
import com.lodz.android.core.utils.ReflectUtils;
import com.lodz.android.core.utils.ScreenUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * BottomSheetDialogFragment基类
 * Created by zhouL on 2018/4/23.
 */
public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startCreate();
        findViews(view, savedInstanceState);
        setListeners(view);
        initData(view);
        endCreate();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Class<?> cls = ReflectUtils.getClassForName("com.google.android.material.bottomsheet.BottomSheetDialog");
        if (cls == null){
            return;
        }
        BottomSheetBehavior behavior = (BottomSheetBehavior) ReflectUtils.getFieldValue(cls, getDialog(), "behavior");
        if (behavior != null){
            onBehaviorInit(behavior);//回调BottomSheetBehavior
        }
    }

    protected void startCreate() {}

    protected abstract void findViews(View view, Bundle savedInstanceState);

    protected void setListeners(View view){}

    protected void initData(View view) {}

    protected void endCreate() {}

    protected abstract void onBehaviorInit(BottomSheetBehavior behavior);

    /**
     * 设置背景蒙版
     * @param value 蒙版透明度（0~1）
     */
    protected void setDim(float value){
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setDimAmount(value);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null){
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext(), R.style.TransparentBottomSheetStyle);//使用自定义style创建Dialog
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBar();
    }

    /** 配置状态栏 */
    private void setStatusBar() {
        Window window = getDialog().getWindow();
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
        int navigationBarHeight = ScreenUtils.getNavigationBarHeight(requireActivity());
        int dialogHeight = screenHeight - statusBarHeight + navigationBarHeight - configTopOffsetPx();//屏幕高度 - 状态栏高度 + 导航栏 - 偏移量高度
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
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
