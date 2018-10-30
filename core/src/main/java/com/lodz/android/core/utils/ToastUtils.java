package com.lodz.android.core.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * Toast帮助类
 * Created by zhouL on 2016/11/17.
 */

public class ToastUtils {

    /** 上下文 */
    private Context mContext;
    /** 提示语 */
    private CharSequence mText;
    /** 时长 */
    private int mDuration = Toast.LENGTH_SHORT;
    /** 位置 */
    private int mGravity = -1;
    /** 位置的X轴偏移量 */
    private int mGravityOffsetX = 0;
    /** 位置的Y轴偏移量 */
    private int mGravityOffsetY = 0;
    /** 图片资源id */
    @DrawableRes
    private int mImgResId = 0;
    /** 自定义界面 */
    private View mCustomView;

    private ToastUtils(Context context) {
        mContext = context;
    }

    /**
     * 显示短时间的Toast
     * @param context 上下文
     * @param text 文字
     */
    public static void showShort(Context context, CharSequence text) {
        create(context).setText(text).setShort().show();
    }

    /**
     * 显示短时间的Toast
     * @param context 上下文
     * @param strResId 文字资源id
     */
    public static void showShort(Context context, @StringRes int strResId) {
        if (context == null){
            return;
        }
        create(context).setText(context.getString(strResId)).setShort().show();
    }

    /**
     * 显示长时间的Toast
     * @param context 上下文
     * @param text 文字
     */
    public static void showLong(Context context, CharSequence text) {
        create(context).setText(text).setLong().show();
    }

    /**
     * 显示长时间的Toast
     * @param context 上下文
     * @param strResId 文字资源id
     */
    public static void showLong(final Context context, @StringRes final int strResId) {
        if (context == null){
            return;
        }
        create(context).setText(context.getString(strResId)).setLong().show();
    }

    /**
     * 创建
     * @param context 上下文
     */
    public static ToastUtils create(Context context){
        return new ToastUtils(context);
    }

    /**
     * 设置提示文字
     * @param text 文字
     */
    public ToastUtils setText(CharSequence text){
        mText = text;
        return this;
    }

    /**
     * 设置提示文字
     * @param textResId 文字资源id
     */
    public ToastUtils setText(@StringRes int textResId){
        mText = mContext.getString(textResId);
        return this;
    }

    /** 设置短时间 */
    public ToastUtils setShort(){
        mDuration = Toast.LENGTH_SHORT;
        return this;
    }

    /** 设置长时间 */
    public ToastUtils setLong(){
        mDuration = Toast.LENGTH_LONG;
        return this;
    }

    /**
     * 设置显示位置
     * @param dravity 位置
     */
    public ToastUtils setGravity(int dravity){
        mGravity = dravity;
        return this;
    }

    /**
     * 设置显示位置
     * @param dravity 位置
     * @param xOffset X轴偏移量
     * @param yOffset Y轴偏移量
     */
    public ToastUtils setGravity(int dravity, int xOffset, int yOffset){
        mGravity = dravity;
        mGravityOffsetX = xOffset;
        mGravityOffsetY = yOffset;
        return this;
    }

    /**
     * 设置toast顶部图片
     * @param imgResId 图片资源id
     */
    public ToastUtils setTopImg(@DrawableRes int imgResId){
        mImgResId = imgResId;
        return this;
    }

    /**
     * 设置自定义界面
     * @param view 界面
     */
    public ToastUtils setView(View view){
        mCustomView = view;
        return this;
    }

    /** 显示Toast */
    public void show() {
        if (mContext == null){
            return;
        }

        Toast toast = null;
        if (!TextUtils.isEmpty(mText)) {
            toast = getDefaultToast();
        } else if (mCustomView != null){
            toast = getCustomViewToast();
        }

        if (toast == null){
            return;
        }

        if (mGravity != -1){
            toast.setGravity(mGravity, mGravityOffsetX, mGravityOffsetY);
        }

        if (mImgResId != 0 && mCustomView == null){//没有自定义view的情况下才设置
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageCodeProject = new ImageView(mContext);
            imageCodeProject.setImageResource(mImgResId);
            toastView.addView(imageCodeProject, 0);
        }

        if (AppUtils.isMainThread()){//主线程直接显示
            toast.show();
            return;
        }

        showPost(toast);//非主线程post到主线程显示
    }

    /** 获取默认的toast */
    private Toast getDefaultToast() {
        return Toast.makeText(mContext, mText, mDuration);
    }

    /** 获取自定义view的toast */
    private Toast getCustomViewToast() {
        Toast toast = new Toast(mContext);
        toast.setView(mCustomView);
        return toast;
    }

    /** 将toast转到UI线程显示 */
    private void showPost(final Toast toast) {
        UiHandler.post(new Runnable() {
            @Override
            public void run() {
                toast.show();
            }
        });
    }
}
