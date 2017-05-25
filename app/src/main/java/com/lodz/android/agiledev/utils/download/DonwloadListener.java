package com.lodz.android.agiledev.utils.download;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 下载状态回调
 * Created by zhouL on 2017/3/28.
 */
public interface DonwloadListener {

    @IntDef({ErrorType.URL_EMPTY, ErrorType.NETWORK_ERROR, ErrorType.DOWNLOADING_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorType {
        int URL_EMPTY = 0;// 下载地址为空
        int NETWORK_ERROR = 1;// 网络错误
        int DOWNLOADING_ERROR = 2;// 下在过程错误
    }

    /** 开始下载 */
    void onStart();

    /**
     * 进度回调
     * @param totalSize 总大小
     * @param progress 已下载大小
     */
    void onProgress(long totalSize, long progress);

    /**
     * 异常回调
     * @param errorType 异常类型
     * @param t 错误信息
     */
    void onError(@ErrorType int errorType, Throwable t);
    /** 下载完成 */
    void onComplete();
    /** 下载暂停 */
    void onPause();
}
