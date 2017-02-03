package com.lodz.android.agiledev;

import com.lodz.android.component.base.BaseApplication;
import com.lodz.android.core.utils.UiHandler;
import com.lodz.android.imageloader.fresco.config.ImageloaderManager;

/**
 *
 * Created by zhouL on 2017/2/3.
 */

public class AgileDevApplication extends BaseApplication{
    @Override
    protected void afterCreate() {
        initImageLoader();
    }

    /** 初始化图片加载库 */
    private void initImageLoader() {
        ImageloaderManager.get().newBuilder()
                .setPlaceholderResId(R.drawable.ic_launcher)//设置默认占位符
                .setErrorResId(R.drawable.ic_launcher)// 设置加载失败图
                .setTapToRetryEnabled(false)// 开启加载失败重试
                .setAutoPlayAnimations(true)// 开启GIF自动播放
                .build(this);
    }

    @Override
    protected void beforeExit() {
        ImageloaderManager.get().clearMemoryCachesWithGC();// 退出时清除图片缓存
        UiHandler.destroy();
    }
}
