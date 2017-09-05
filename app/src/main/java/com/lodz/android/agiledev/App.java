package com.lodz.android.agiledev;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.network.NetworkManager;
import com.lodz.android.core.utils.UiHandler;
import com.lodz.android.imageloader.ImageloaderManager;

/**
 * application
 * Created by zhouL on 2017/2/3.
 */

public class App extends BaseApplication{

    public static App getInstance() {
        return (App) get();
    }

    @Override
    protected void afterCreate() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

        PrintLog.setPrint(BuildConfig.LOG_DEBUG);// 配置日志开关
        NetworkManager.get().init(this);// 初始化网络管理
        initImageLoader();

        configBaseLayout();
    }

    /** 配置基类 */
    private void configBaseLayout() {
        configTitleBarLayout();
//        configErrorLayout();
//        configLoadingLayout();
//        configNoDataLayout();
    }

    /** 配置无数据 */
    private void configNoDataLayout() {
        getBaseLayoutConfig().getNoDataLayoutConfig().setOrientation(LinearLayout.HORIZONTAL);
        getBaseLayoutConfig().getNoDataLayoutConfig().setNeedImg(true);
        getBaseLayoutConfig().getNoDataLayoutConfig().setNeedTips(true);
        getBaseLayoutConfig().getNoDataLayoutConfig().setImg(R.drawable.ic_launcher);
        getBaseLayoutConfig().getNoDataLayoutConfig().setTips("没有数据啊啊啊啊啊");
        getBaseLayoutConfig().getNoDataLayoutConfig().setTipsTextColor(R.color.color_ffa630);
        getBaseLayoutConfig().getNoDataLayoutConfig().setTipsTextSize(22);
        getBaseLayoutConfig().getNoDataLayoutConfig().setBackgroundColor(R.color.color_ea8380);
    }

    /** 配置加载页 */
    private void configLoadingLayout() {
        getBaseLayoutConfig().getLoadingLayoutConfig().setOrientation(LinearLayout.HORIZONTAL);
        getBaseLayoutConfig().getLoadingLayoutConfig().setNeedTips(true);
        getBaseLayoutConfig().getLoadingLayoutConfig().setTips("测试啊啊啊啊啊");
        getBaseLayoutConfig().getLoadingLayoutConfig().setTipsTextColor(R.color.color_ff4081);
        getBaseLayoutConfig().getLoadingLayoutConfig().setTipsTextSize(15);
        getBaseLayoutConfig().getLoadingLayoutConfig().setBackgroundColor(R.color.color_3981ef);
        getBaseLayoutConfig().getLoadingLayoutConfig().setIsIndeterminate(true);
        getBaseLayoutConfig().getLoadingLayoutConfig().setIndeterminateDrawable(R.drawable.anims_custom_progress);
    }

    /** 配置标题栏 */
    private void configTitleBarLayout() {
        getBaseLayoutConfig().getTitleBarLayoutConfig().setNeedBackButton(true);
//        getBaseLayoutConfig().getTitleBarLayoutConfig().setReplaceBackBtnResId(R.drawable.ic_launcher);
        getBaseLayoutConfig().getTitleBarLayoutConfig().setBackgroundColor(R.color.color_00a0e9);
//        getBaseLayoutConfig().getTitleBarLayoutConfig().setBackBtnName("返返");
//        getBaseLayoutConfig().getTitleBarLayoutConfig().setBackBtnTextColor(R.color.color_d9d9d9);
//        getBaseLayoutConfig().getTitleBarLayoutConfig().setBackBtnTextSize(14);
        getBaseLayoutConfig().getTitleBarLayoutConfig().setTitleTextColor(R.color.white);
//        getBaseLayoutConfig().getTitleBarLayoutConfig().setTitleTextSize(18);
        getBaseLayoutConfig().getTitleBarLayoutConfig().setIsShowDivideLine(false);
//        getBaseLayoutConfig().getTitleBarLayoutConfig().setDivideLineHeight(10);
//        getBaseLayoutConfig().getTitleBarLayoutConfig().setDivideLineColor(R.color.color_2f6dc9);
        getBaseLayoutConfig().getTitleBarLayoutConfig().setIsNeedElevation(true);
        getBaseLayoutConfig().getTitleBarLayoutConfig().setElevationVale(13f);
    }

    /** 配置错误页 */
    private void configErrorLayout() {
        getBaseLayoutConfig().getErrorLayoutConfig().setOrientation(LinearLayout.HORIZONTAL);
        getBaseLayoutConfig().getErrorLayoutConfig().setImg(R.drawable.ic_launcher);
        getBaseLayoutConfig().getErrorLayoutConfig().setBackgroundColor(R.color.color_ffa630);
        getBaseLayoutConfig().getErrorLayoutConfig().setNeedTips(true);
        getBaseLayoutConfig().getErrorLayoutConfig().setTips("测试啦");
        getBaseLayoutConfig().getErrorLayoutConfig().setTipsTextColor(R.color.color_ea413c);
        getBaseLayoutConfig().getErrorLayoutConfig().setTipsTextSize(22);
    }

    /** 初始化图片加载库 */
    private void initImageLoader() {
        ImageloaderManager.get().newBuilder()
                .setPlaceholderResId(R.drawable.ic_launcher)//设置默认占位符
                .setErrorResId(R.drawable.ic_launcher)// 设置加载失败图
//                .setFrescoRetryResId(R.drawable.ic_launcher)// 设置默认重载图片
//                .setFrescoTapToRetryEnabled(false)// 开启加载失败重试
//                .setFrescoAutoPlayAnimations(true)// 开启GIF自动播放
                .setDirectoryFile(this.getApplicationContext().getCacheDir())
                .setDirectoryName("image_cache")
                .build(this);
    }

    @Override
    protected void beforeExit() {
        ImageloaderManager.get().clearMemoryCachesWithGC(this);// 退出时清除图片缓存
        UiHandler.destroy();
        NetworkManager.get().release(this);// 释放网络管理资源
        NetworkManager.get().clearNetworkListener();// 清除所有网络监听器
    }

    private int sessionId = 123213234;

    @Override
    public Bundle getSaveInstanceState() {
        PrintLog.e("testtag", "getSaveInstanceState : " + sessionId);
        Bundle bundle = new Bundle();
        bundle.putInt("sessionid", sessionId);
        return bundle;
    }

    @Override
    public void getRestoreInstanceState(Bundle bundle) {
        super.getRestoreInstanceState(bundle);
        if (bundle != null){
            sessionId = bundle.getInt("sessionid", 0);
        }
        PrintLog.e("testtag", "getRestoreInstanceState : " + sessionId);
    }
}
