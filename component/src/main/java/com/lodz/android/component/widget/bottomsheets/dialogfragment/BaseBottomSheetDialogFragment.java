package com.lodz.android.component.widget.bottomsheets.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lodz.android.core.utils.ReflectUtils;

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

    protected void startCreate() {

    }

    protected abstract void findViews(View view, Bundle savedInstanceState);

    protected void setListeners(View view){}

    protected void initData(View view) {}

    protected void endCreate() {}

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetBehavior behavior = (BottomSheetBehavior) ReflectUtils.getFieldValue(BottomSheetDialog.class, getDialog(), "mBehavior");
        if (getContext() != null && behavior != null){
            onBehaviorInit(getContext(), behavior);
        }
    }

    protected abstract void onBehaviorInit(Context context, BottomSheetBehavior behavior);

    /**
     * 设置背景蒙版
     * @param value 蒙版透明度（0~1）
     */
    protected void setDim(float value){
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setDimAmount(value);
        }
    }
}
