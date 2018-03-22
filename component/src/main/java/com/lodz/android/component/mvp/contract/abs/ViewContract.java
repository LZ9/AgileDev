package com.lodz.android.component.mvp.contract.abs;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * View接口
 * Created by zhouL on 2017/7/7.
 */

public interface ViewContract {

    /** 在Activity里绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event);

    /** 在Fragment里绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event);

    /** 自动绑定Rx生命周期 */
    <T> LifecycleTransformer<T> bindUntilDetachEvent();
}
