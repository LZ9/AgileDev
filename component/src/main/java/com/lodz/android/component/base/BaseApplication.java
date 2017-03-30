package com.lodz.android.component.base;

import android.app.Application;
import android.os.Bundle;

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

    /** 当APP在后台被回收时可以调用该方法保存关键数据 */
    public Bundle getSaveInstanceState(){
        return null;
    }

    /** 当APP被回收后从后台回到前台时调用该方法获取保存的关键数据 */
    public void getRestoreInstanceState(Bundle bundle){}
}
