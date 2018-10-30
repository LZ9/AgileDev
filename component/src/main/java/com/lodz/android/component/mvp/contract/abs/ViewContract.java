package com.lodz.android.component.mvp.contract.abs;

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;


/**
 * View接口
 * Created by zhouL on 2017/7/7.
 */

public interface ViewContract {

    /** 显示短Toast */
    void showShortToast(String tips);

    /** 显示短Toast */
    void showShortToast(@StringRes int resId);

    /** 显示长Toast */
    void showLongToast(String tips);

    /** 显示长Toast */
    void showLongToast(@StringRes int resId);

    /** 关闭页面 */
    void finish();

    /** 在Activity里绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event);

    /** 在Fragment里绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event);

    /** 自动绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilDetachEvent();
}
