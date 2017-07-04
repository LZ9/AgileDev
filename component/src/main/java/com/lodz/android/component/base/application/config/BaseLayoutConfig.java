package com.lodz.android.component.base.application.config;

/**
 * 基类配置
 * Created by zhouL on 2017/7/3.
 */

public class BaseLayoutConfig {

    /** 异常页面配置 */
    private ErrorLayoutConfig mErrorLayoutConfig;
    /** 加载页面配置 */
    private LoadingLayoutConfig mLoadingLayoutConfig;
    /** 无数据页面配置 */
    private NoDataLayoutConfig mNoDataLayoutConfig;
    /** 标题栏配置 */
    private TitleBarLayoutConfig mTitleBarLayoutConfig;

    public BaseLayoutConfig() {
        this.mErrorLayoutConfig = new ErrorLayoutConfig();
        this.mLoadingLayoutConfig = new LoadingLayoutConfig();
        this.mNoDataLayoutConfig = new NoDataLayoutConfig();
        this.mTitleBarLayoutConfig = new TitleBarLayoutConfig();
    }

    /** 获取异常页面配置 */
    public ErrorLayoutConfig getErrorLayoutConfig() {
        return mErrorLayoutConfig;
    }

    /** 获取加载页面配置 */
    public LoadingLayoutConfig getLoadingLayoutConfig() {
        return mLoadingLayoutConfig;
    }

    /** 获取无数据页面配置 */
    public NoDataLayoutConfig getNoDataLayoutConfig() {
        return mNoDataLayoutConfig;
    }

    /** 获取标题栏配置 */
    public TitleBarLayoutConfig getTitleBarLayoutConfig() {
        return mTitleBarLayoutConfig;
    }
}
