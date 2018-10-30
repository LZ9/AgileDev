package com.lodz.android.component.mvp.contract.abs;

import android.content.Context;

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import androidx.annotation.NonNull;

/**
 * Presenter接口
 * Created by zhouL on 2017/7/7.
 */

public interface PresenterContract<VC extends ViewContract> {

    /**
     * 连接
     * @param context 上下文
     * @param view View接口
     */
    void attach(Context context, VC view);

    /** 分离 */
    void detach();

    /** 是否已分离 */
    boolean isDetach();

    /** 在Activity里绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event);

    /** 在Fragment里绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event);

    /** 自动绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilDetachEvent();
}

