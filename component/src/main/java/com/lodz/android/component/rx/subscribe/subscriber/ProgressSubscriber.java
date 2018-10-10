package com.lodz.android.component.rx.subscribe.subscriber;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.core.utils.UiHandler;

import org.reactivestreams.Subscription;

/**
 * 展示加载框的订阅者（带背压）
 * Created by zhouL on 2017/3/1.
 */
public abstract class ProgressSubscriber<T> extends RxSubscriber<T>{

    @Override
    public final void onRxSubscribe(Subscription s) {
        showProgress();
        onPgSubscribe(s);
    }

    @Override
    public final void onRxNext(T t) {
        onPgNext(t);
    }

    @Override
    public final void onRxError(Throwable e, boolean isNetwork) {
        onPgError(e, isNetwork);
    }

    @Override
    public final void onRxComplete() {
        dismissProgress();
        onPgComplete();
    }

    /** 加载框 */
    private AlertDialog mProgressDialog;

    /**
     * 创建加载框
     * @param context 上下文
     */
    public ProgressSubscriber<T> create(@NonNull Context context) {
        return create(context, "", true);
    }

    /**
     * 创建加载框
     * @param context 上下文
     * @param cancelable 是否可取消
     */
    public ProgressSubscriber<T> create(@NonNull Context context, boolean cancelable) {
        return create(context, "", cancelable);
    }

    /**
     * 创建加载框
     * @param context 上下文
     * @param strResId 提示信息资源id
     * @param cancelable 是否可取消
     */
    public ProgressSubscriber<T> create(@NonNull Context context, @StringRes int strResId, boolean cancelable) {
        return create(context, context.getString(strResId), cancelable);
    }

    /**
     * 创建加载框
     * @param dialog 自定义加载框
     */
    public ProgressSubscriber<T> create(@NonNull AlertDialog dialog){
        try {
            mProgressDialog = dialog;
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancelDialog();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 创建加载框
     * @param context 上下文
     * @param msg 提示信息
     * @param cancelable 是否可取消
     */
    public ProgressSubscriber<T> create(@NonNull Context context, String msg, boolean cancelable) {
        try {
            mProgressDialog = getProgressDialog(context, msg, cancelable);
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    /** 获取一个加载框 */
    private AlertDialog getProgressDialog(@NonNull Context context, String msg, boolean cancelable) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_view_progress_layout, null);
        AlertDialog progressDialog = new AlertDialog.Builder(context, R.style.ProgressStyle)
                .setView(view)
                .create();
        if (!TextUtils.isEmpty(msg)) {
            TextView msgTextView = view.findViewById(R.id.msg);
            msgTextView.setVisibility(View.VISIBLE);
            msgTextView.setText(msg);
        }
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(cancelable);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelDialog();
            }
        });
        Window window = progressDialog.getWindow();
        if (window != null){
            window.setGravity(Gravity.CENTER);
        }
        return progressDialog;
    }

    /** 取消加载框 */
    private void cancelDialog() {// 用户关闭
        cancel();
        onPgCancel();
        dismissProgress();
    }

    /** 显示加载框 */
    private void showProgress(){
        UiHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mProgressDialog != null){
                        mProgressDialog.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /** 关闭加载框 */
    private void dismissProgress(){
        UiHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mProgressDialog != null){
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onErrorEnd() {// 抛异常关闭
        dismissProgress();
    }

    @Override
    public void onCancel() {// 开发者关闭
        dismissProgress();
    }

    public void onPgSubscribe(Subscription s){}

    public abstract void onPgNext(T t);

    public abstract void onPgError(Throwable e, boolean isNetwork);

    public void onPgComplete(){}

    /** 用户取消回调 */
    public void onPgCancel() {}
}
