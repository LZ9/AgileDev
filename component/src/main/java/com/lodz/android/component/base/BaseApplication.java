package com.lodz.android.component.base;

import android.app.Application;

import com.lodz.android.component.event.ActivityFinishEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 基类Application
 * Created by zhouL on 2016/11/17.
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication sInstance;

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

    /** 关闭所有Activity */
    public void finishActivities() {
        // 发送关闭事件
        EventBus.getDefault().post(new ActivityFinishEvent());
    }

    /** 退出app  */
    public void exit() {
        beforeExit();
        finishActivities();
//        System.exit(0);// 退出整个应用
    }

    protected abstract void beforeExit();

}
