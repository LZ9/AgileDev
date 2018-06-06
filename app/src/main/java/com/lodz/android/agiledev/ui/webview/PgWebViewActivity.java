package com.lodz.android.agiledev.ui.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.utils.jsbridge.BridgeWebView;
import com.lodz.android.agiledev.utils.jsbridge.BridgeWebViewClient;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.log.PrintLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载进度条的WebView
 * Created by zhouL on 2018/6/6.
 */
public class PgWebViewActivity extends BaseActivity{

    private static final String TAG = "PgWebViewActivity";

    /** 百度地址 */
    private static final String BAIDU_URL = "https://www.baidu.com/";

    /** WebView */
    @BindView(R.id.webview)
    BridgeWebView mWebView;
    /** 进度条 */
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    /** 是否加载成功 */
    private boolean isLoadSuccess = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pg_web_view_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setVisibility(View.GONE);
    }

    @Override
    protected boolean onPressBack() {
        goBack();
        return true;
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        mWebView.reload();
    }

    @Override
    protected void initData() {
        super.initData();
        initWebView();
        showStatusCompleted();
    }

    /** 初始化webview */
    private void initWebView() {
        mWebView.setWebViewClient(new CustomWebViewClient(mWebView));// 设置 WebViewClient
        mWebView.setWebChromeClient(new CustomWebChromeClient());// 设置 WebChromeClient
        initWebSettings(mWebView.getSettings());
        mWebView.loadUrl(BAIDU_URL);
    }

    /** 初始化WebSettings */
    private void initWebSettings(WebSettings settings){
        // 默认文本编码，默认值 "UTF-8"
        settings.setDefaultTextEncodingName("UTF-8");
        // 是否自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 是否支持缩放
        settings.setSupportZoom(false);
        // 设置缓存模式
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private class CustomWebViewClient extends BridgeWebViewClient {

        private CustomWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
            showStatusCompleted();
            PrintLog.w(TAG, "onPageStarted");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            showStatusError();
            mProgressBar.setVisibility(View.GONE);
            isLoadSuccess = false;
            PrintLog.e(TAG, error.toString());
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            PrintLog.i(TAG, "onPageFinished");
            if (!isLoadSuccess){
                isLoadSuccess = true;
                return;
            }
            mProgressBar.setVisibility(View.GONE);
            showStatusCompleted();
        }
    }

    private class CustomWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            PrintLog.d(TAG, "进度 ： " + newProgress);
            mProgressBar.setProgress(newProgress);
        }
    }

    /** 用户点击返回按钮 */
    private void goBack(){
        if (mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.destroy();
        mWebView = null;
    }


}
