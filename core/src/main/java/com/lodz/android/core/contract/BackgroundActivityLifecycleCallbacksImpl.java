package com.lodz.android.core.contract;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 后台activity数量统计
 * Created by zhouL on 2018/6/26.
 */
public class BackgroundActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    /** 显示的activity数量 */
    private int mCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        mCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mCount--;
        if (mCount < 0){
            mCount = 0;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public boolean isBackground(){
        return mCount == 0;
    }
}
