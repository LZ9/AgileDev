package com.lodz.android.agiledev.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * WebView测试类
 * Created by zhouL on 2018/4/11.
 */
public class WebViewTestActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, WebViewTestActivity.class);
        context.startActivity(starter);
    }

    /** 测试信息网页 */
    private static final String TEST_INFO_URL = "http://222.76.243.213:7102/jyxlpt/file/recv/html/4150888675E64BFF9556CA38E0754DA5_app.html";
    /** 测试本地信息（不带头信息） */
    private static final String TEST_LOCAL_TEST = "file:///android_asset/test.html";
    /** 测试本地信息 */
    private static final String TEST_LOCAL_TEST_APP = "file:///android_asset/test_app.html";
    /** JS交互测试页 */
    private static final String TEST_JS_BRIDGE = "file:///android_asset/JsBridgeDemo.html";

    /** 文件请求码 */
    private final static int REQUEST_CODE_FILE_CHOOSER = 10000;

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


    }

    private class CustomWebChromeClient extends WebChromeClient {

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
    public void finish() {
        super.finish();
        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.destroy();
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

}
