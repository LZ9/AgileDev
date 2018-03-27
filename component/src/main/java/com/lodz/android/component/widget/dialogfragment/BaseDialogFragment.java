package com.lodz.android.component.widget.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lodz.android.component.R;

/**
 * DialogFragment基类
 * Created by zhouL on 2018/3/26.
 */

public abstract class BaseDialogFragment extends DialogFragment{

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

    protected void startCreate() {}

    protected abstract void findViews(View view, Bundle savedInstanceState);

    protected void setListeners(View view){}

    protected void initData(View view) {}

    protected void endCreate() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null){
            return super.onCreateDialog(savedInstanceState);
        }
        return new Dialog(getContext(), R.style.BaseDialog);
    }

    private void setWindowAnimations(Dialog dialog) {
        if (dialog == null || dialog.getWindow() == null){
            return;
        }
        if (getAnimations() != -1) {
            dialog.getWindow().setWindowAnimations(getAnimations()); //设置窗口弹出动画
        }
    }

    protected void setDialogWindow(Dialog dialog){

    }

    @StyleRes
    protected int getAnimations() {
        return -1;
    }

    @Override
    public void onStart() {
        super.onStart();
        setWindowAnimations(getDialog());
        setDialogWindow(getDialog());
    }
}
