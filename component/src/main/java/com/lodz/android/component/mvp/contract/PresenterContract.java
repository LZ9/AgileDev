package com.lodz.android.component.mvp.contract;

import android.content.Context;

/**
 * Presenter接口
 * Created by zhouL on 2017/7/7.
 */

public interface PresenterContract<VC extends ViewContract> {

    /**
     * onCreate
     * @param context 上下文
     * @param view View接口
     */
    void onCreate(Context context, VC view);

    /** onDestroy */
    void onDestroy();

    /** onPause */
    void onPause();

    /** onResume */
    void onResume();

}
