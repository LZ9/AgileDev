package com.lodz.android.component.base;

import android.app.Activity;
import android.app.Application;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 基类Application
 * Created by zhouL on 2016/11/17.
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication sInstance;

    private Map<String, WeakReference<Activity>> mActivityMap = new HashMap<>();

    public static BaseApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        afterCreate();
    }

    protected abstract void afterCreate();

    /** 注册Activity */
    public void registerActivity(Activity activity) {
        mActivityMap.put(activity.getClass().toString(), new WeakReference<>(activity));
    }

    /** 移除Activity */
    public void removeActivity(Activity activity) {
        if (mActivityMap.get(activity.getClass().toString()) != null){
            mActivityMap.get(activity.getClass().toString()).clear();
            mActivityMap.remove(activity.getClass().toString());
        }
    }

    /** 关闭所有Activity */
    public void finishActivities() {
        for (Map.Entry<String, WeakReference<Activity>> entry : mActivityMap.entrySet()) {
            WeakReference<Activity> aRef = entry.getValue();
            Activity activity = aRef.get();
            if (null != activity && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /** 退出app  */
    public void exit() {
        beforeExit();
        finishActivities();
//        System.exit(0);// 退出整个应用
    }

    protected abstract void beforeExit();

}
