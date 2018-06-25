package com.lodz.android.component.base.application;

import android.app.Application;
import android.os.Bundle;

import com.lodz.android.component.base.application.config.BaseLayoutConfig;
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

    /** 基础控件配置 */
    private BaseLayoutConfig mBaseLayoutConfig;
    /** 保存回收前数据的Bundle */
    private Bundle mRestoreBundle;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mBaseLayoutConfig = new BaseLayoutConfig();
        afterCreate();
    }
    /** 获取基础控件配置 */
    public BaseLayoutConfig getBaseLayoutConfig() {
        return mBaseLayoutConfig;
    }

    protected abstract void afterCreate();

    /** 关闭所有Activity */
    public void finishActivities() {

        // 发送关闭事件
        EventBus.getDefault().post(new ActivityFinishEvent());
    }

    /** 退出app  */
    public void exit() {
        finishActivities();
        beforeExit();
//        System.exit(0);// 退出整个应用
    }

    protected abstract void beforeExit();

    /** 当APP在后台被回收时可以调用该方法保存关键数据 */
    public void putSaveInstanceState(Bundle bundle){
        mRestoreBundle = bundle;
    }

    /** 当APP被回收后从后台回到前台时调用该方法获取保存的关键数据 */
    public Bundle getSaveInstanceState(){
        return mRestoreBundle;
    }
}
