package com.lodz.android.agiledev.ui.mvp;

import com.lodz.android.core.utils.UiHandler;

/**
 * 数据
 * Created by zhouL on 2017/7/9.
 */

public class ApiModule {

    public void requestResult(final Listener listener){
        UiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listener != null){
                    listener.onCallback("result callback" + System.currentTimeMillis());
                }
            }
        }, 1000);
    }

    public interface Listener{
        void onCallback(String response);
    }
}
