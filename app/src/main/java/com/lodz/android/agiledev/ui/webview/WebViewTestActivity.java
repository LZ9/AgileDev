package com.lodz.android.agiledev.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.UserBean;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.utils.jsbridge.BridgeHandler;
import com.lodz.android.agiledev.utils.jsbridge.BridgeWebView;
import com.lodz.android.agiledev.utils.jsbridge.BridgeWebViewClient;
import com.lodz.android.agiledev.utils.jsbridge.CallBackFunction;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.ErrorLayout;
import com.lodz.android.component.widget.base.LoadingLayout;
import com.lodz.android.core.log.PrintLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * WebView和JS交互测试类
 * Created by zhouL on 2018/4/11.
 */
public class WebViewTestActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, WebViewTestActivity.class);
        context.startActivity(starter);
    }

    private static final String TAG = "webview_test";

    /** 测试信息网页 */
    private static final String TEST_INFO_URL = "http://222.76.243.213:7102/jyxlpt/file/recv/html/4150888675E64BFF9556CA38E0754DA5_app.html";
    /** 测试本地信息（不带头信息） */
    private static final String TEST_LOCAL_TEST = "file:///android_asset/h5_test";
    /** 测试本地信息 */
    private static final String TEST_LOCAL_TEST_APP = "file:///android_asset/h5_test_app";
    /** JS交互测试页 */
    private static final String TEST_JS_BRIDGE = "file:///android_asset/JsBridgeDemo";
    /** 错误地址 */
    private static final String TEST_ERROR_URL = "https://www.baiduasdwqewq.com/";


    /** 文件请求码 */
    private final static int REQUEST_CODE_FILE_CHOOSER = 10000;


    /** 加载控件 */
    @BindView(R.id.web_loading_layout)
    LoadingLayout mWebLoadingLayout;
    /** 加载失败控件 */
    @BindView(R.id.web_error_layout)
    ErrorLayout mWebErrorLayout;
    /** 浏览器 */
    @BindView(R.id.webview)
    BridgeWebView mWebView;

    /** ScrollView */
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    /** 信息 */
    @BindView(R.id.result)
    TextView mResultTv;
    /** JAVA调用WEB（有回调） */
    @BindView(R.id.call_web_response)
    Button mCallWebResponseBtn;
    /** JAVA调用WEB（无回调） */
    @BindView(R.id.call_web_unresponse)
    Button mCallWebUnresponseBtn;

    /** 上传文件信息 */
    private ValueCallback<Uri[]> mUploadMessageAboveL;

    /** 是否加载成功 */
    private boolean isLoadSuccess = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        // JAVA调用WEB（有回调）
        mCallWebResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = JSON.toJSONString(new UserBean("1238784791", "qwesdw"));
                appendLog("java 发给web ：" + data);
                mWebView.callHandler("functionInJs", data, new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        appendLog("web 发送过来的数据：" + data + "\n");
                    }
                });
            }
        });

        // JAVA调用WEB（无回调）
        mCallWebUnresponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "hello";
                appendLog("java 发给web ：" + msg + "\n");
                mWebView.send(msg);
            }
        });

        // 加载失败控件
        mWebErrorLayout.setReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
        initWebView();
    }

    private void initWebView() {

        mWebView.setWebViewClient(new CustomWebViewClient(mWebView));// 设置 WebViewClient
        mWebView.setWebChromeClient(new CustomWebChromeClient());// 设置 WebChromeClient
        initWebSettings(mWebView.getSettings());

        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                appendLog("web 发送过来的数据：" + data + "\n");
                function.onCallBack("java get param");
            }
        });

        mWebView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                appendLog("web 发送过来的数据：" + data + "\n");
                function.onCallBack("java get user info");
            }
        });

        mWebView.loadUrl(TEST_JS_BRIDGE);
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
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private class CustomWebViewClient extends BridgeWebViewClient{

        private CustomWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showWebLoading();
            PrintLog.w(TAG, "onPageStarted");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            showWebError();
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
            showWebCompleted();
        }
    }

    private class CustomWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            PrintLog.d(TAG, "进度 ： " + newProgress);
        }

        /** H5调用文件选择 */
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadMessageAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }
    }

    /** 打开图片选择页 */
    private void openImageChooserActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Image Chooser"), REQUEST_CODE_FILE_CHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FILE_CHOOSER){// 文件选择回调
            if (mUploadMessageAboveL == null){
                return;
            }
            Uri[] uris = null;
            if (data != null && data.getData() != null){
                uris = new Uri[]{data.getData()};
            }
            mUploadMessageAboveL.onReceiveValue(uris);
            mUploadMessageAboveL = null;
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


    private void appendLog(String strLog) {
        mResultTv.append(strLog + "\n");
        // 自动滚屏
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    /** 显示web加载页 */
    private void showWebLoading(){
        mWebLoadingLayout.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.GONE);
        mWebErrorLayout.setVisibility(View.GONE);
    }

    /** 显示web加载失败页 */
    private void showWebError(){
        mWebLoadingLayout.setVisibility(View.GONE);
        mWebView.setVisibility(View.GONE);
        mWebErrorLayout.setVisibility(View.VISIBLE);
    }

    /** 显示web内容页 */
    private void showWebCompleted(){
        mWebLoadingLayout.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
        mWebErrorLayout.setVisibility(View.GONE);
    }
}
