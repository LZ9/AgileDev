package com.lodz.android.agiledev.utils.jsbridge;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.utils.api.ApiServiceManager;
import com.lodz.android.agiledev.utils.file.FileManager;
import com.lodz.android.core.log.PrintLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 带进度条WebView
 * @author zhouL
 * @date 2019/4/18
 */
public class PgWebView extends FrameLayout {

    private static final String TAG = "PgWebViewTag";

    /** WebView */
    private BridgeWebView mWebView;
    /** 进度条 */
    private ProgressBar mProgressBar;

    /** 监听器 */
    private Listener mListener;
    /** 是否加载成功 */
    private boolean isLoadSuccess = true;

    public PgWebView(@NonNull Context context) {
        super(context);
        init();
    }

    public PgWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PgWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PgWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_pb_webview, this);
        mWebView = findViewById(R.id.bridge_web_view);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    /** 初始化WebView */
    public void initWebView() {
        mWebView.setWebViewClient(new CustomWebViewClient(mWebView));// 设置 WebViewClient
        mWebView.setWebChromeClient(new CustomWebChromeClient());// 设置 WebChromeClient
        initWebSettings(mWebView.getSettings());
    }

    /** 初始化WebSettings */
    private void initWebSettings(WebSettings settings) {
        // 默认文本编码，默认值 "UTF-8"
        settings.setDefaultTextEncodingName("UTF-8");
        // 是否自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 是否支持缩放
        settings.setSupportZoom(false);
        // 设置缓存模式
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 开启localstorage
        settings.setDomStorageEnabled(true); // 开启 DOM storage 功能
        settings.setAppCachePath(FileManager.getCacheFolderPath());
        settings.setAllowFileAccess(true); // 可以读取文件缓存
        settings.setAppCacheEnabled(true); //开启H5(APPCache)缓存功能
    }

    /** WebView客户端 */
    private class CustomWebViewClient extends BridgeWebViewClient {

        private CustomWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
            PrintLog.ws(TAG, "onPageStarted");
            if (mListener != null){
                mListener.onPageStarted(view, url, favicon);
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mProgressBar.setVisibility(View.GONE);
            isLoadSuccess = false;
            PrintLog.es(TAG, error.toString());
            if (mListener != null){
                mListener.onReceivedError(view, request, error);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            PrintLog.is(TAG, "onPageFinished");
            if (!isLoadSuccess) {
                isLoadSuccess = true;
                return;
            }
            mProgressBar.setVisibility(View.GONE);
            if (mListener != null){
                mListener.onPageFinished(view, url);
            }
        }
    }

    /** WebChromeClient客户端 */
    private class CustomWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            PrintLog.ds(TAG, "进度 ： " + newProgress);
            mProgressBar.setProgress(newProgress);
            if (mListener != null){
                mListener.onProgressChanged(view, newProgress);
            }
        }
    }

    /**
     * 加载地址
     * @param url 地址
     */
    public void loadUrl(String url) {
        if (mWebView != null) {
            mWebView.loadUrl(url);
        }
    }

    /** 是否可以回退 */
    public boolean isCanGoBack() {
        return mWebView != null && mWebView.canGoBack();
    }

    /** 回退 */
    public void goBack() {
        if (mWebView != null){
            mWebView.goBack();
        }
    }

    /** 重载 */
    public void reload() {
        if (mWebView != null){
            mWebView.reload();
        }
    }

    /** 释放资源 */
    public void release() {
        if (mWebView == null){
            return;
        }
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.destroy();
        mWebView = null;
    }

    /**
     * 订阅H5数据
     * @param handler 回调
     */
    public boolean registerData(BridgeLogHandler handler) {
        if (mWebView == null || handler == null) {
            return false;
        }
        try {
            mWebView.registerHandler(handler.getApiName(), handler);
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 向H5发送数据（必须要在主线程里运行）
     * @param apiName 接口名
     * @param data 数据
     * @param callBack 回调
     */
    public boolean sendData(String apiName, String data, CallBackFunction callBack) {
        if (mWebView == null) {
            return false;
        }
        try {
            PrintLog.es(ApiServiceManager.TAG, "android send js [" + apiName + "]  --->  " + (TextUtils.isEmpty(data) ? "null" : data));
            mWebView.callHandler(apiName, data, callBack);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置监听器
     * @param listener 监听器
     */
    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{

        /**
         * 开始加载页面
         * @param webView 控件
         * @param url 地址
         */
        void onPageStarted(WebView webView, String url, Bitmap favicon);

        /**
         * 加载进度回调
         * @param webView 控件
         * @param progress 进度
         */
        void onProgressChanged(WebView webView, int progress);

        /**
         * 加载失败
         * @param webView 控件
         * @param request 请求
         * @param error 失败结果
         */
        void onReceivedError(WebView webView, WebResourceRequest request, WebResourceError error);

        /**
         * 加载完成
         * @param webView 控件
         * @param url 地址
         */
        void onPageFinished(WebView webView, String url);
    }


}
