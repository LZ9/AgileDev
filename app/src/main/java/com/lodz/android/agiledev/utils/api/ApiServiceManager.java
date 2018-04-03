package com.lodz.android.agiledev.utils.api;


import android.text.TextUtils;

import com.lodz.android.agiledev.config.UrlConfig;
import com.lodz.android.component.rx.converter.FastJsonConverterFactory;
import com.lodz.android.core.log.PrintLog;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 接口管理器
 * Created by zhouL on 2016/10/31.
 */
public class ApiServiceManager {

    /** 接口日志标签 */
    private static final String TAG = "resultValue";

    private static ApiServiceManager mInstance;

    public static ApiServiceManager get() {
        if (mInstance == null) {
            synchronized (ApiServiceManager.class) {
                if (mInstance == null) {
                    mInstance = new ApiServiceManager();
                }
            }
        }
        return mInstance;
    }

    private Retrofit mRetrofit;

    private ApiServiceManager() {
        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .baseUrl(UrlConfig.BASE_URL)
                .client(getOkHttpClient())
                .build();
    }

    /** 获取一个OkHttpClient */
    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor())
                .build();
    }

    /** 请求拦截器 */
    private class RequestInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request oldRequest = chain.request();
            Request request = oldRequest.newBuilder()
//                    .headers(Headers.of(getHeaders()))//注入头信息
//                    .url(getCommonUrl(oldRequest))// 注入通用入参
                    .build();
            logRequest(request);
            return logResponse(chain.proceed(request));
        }
    }

//    /**
//     * 在URL地址中放入通用参数
//     * @param request 请求
//     */
//    private HttpUrl getCommonUrl(Request request) {
//        HttpUrl.Builder commonUrl = request.url()
//                .newBuilder()
//                .scheme(request.url().scheme())
//                .host(request.url().host())
//                .addQueryParameter(UrlConfig.COMMON_APP_KEY, UrlConfig.COMMON_APP_KEY_VALUE)
//                .addQueryParameter(UrlConfig.COMMON_FORMAT, UrlConfig.COMMON_FORMAT_VALUE)
//                .addQueryParameter(UrlConfig.COMMON_LOCALE, UrlConfig.COMMON_LOCALE_VALUE);
//        return commonUrl.build();
//    }

//    /** 获取头信息 */
//    private Map<String, String> getHeaders() {
//        HashMap<String, String> headersMap = new HashMap<>();
//        headersMap.put("appKey", "00001");
//        headersMap.put("format", "json");
//        headersMap.put("locale", "zh_CN");
//        return headersMap;
//    }

    /**
     * 打印请求信息日志
     * @param request 请求
     */
    private void logRequest(Request request) {
        List<String> list = request.url().pathSegments();
        PrintLog.i(TAG, "[" + list.get(list.size() - 1) + "] ---> " + request.url().toString());
        logSegmentedLog(false, list.get(list.size() - 1), getRequestString(request));
    }

    /**
     * 获取请求字符串
     * @param request 请求
     */
    private String getRequestString(Request request){
        try {
            final Request copy = request.newBuilder().build();
            if (copy.body() == null){
                return "";
            }
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 打印返回数据日志
     * @param response 返回数据
     */
    private okhttp3.Response logResponse(okhttp3.Response response) {
        String log = "";
        try {
            log = response.body().string();
            List<String> list = response.request().url().pathSegments();
            logSegmentedLog(true, list.get(list.size() - 1), log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 打印完需要将数据重新写入，因为response.body().string()执行一次以后会将数据清空
        response = response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), log))
                .build();
        return response;
    }

    /**
     * 打印分段日志
     * @param isResponse 是否打印响应数据
     * @param tag 标签
     * @param log 原始日志
     */
    private void logSegmentedLog(boolean isResponse, String tag, String log) {
        synchronized (ApiServiceManager.class){
            if (TextUtils.isEmpty(log) || log.length() < 3000){
                if (isResponse){
                    PrintLog.d(TAG, "[" + tag + "] <--- " + log);
                }else {
                    PrintLog.i(TAG, "[" + tag + "] ---> " + log);
                }
                return;
            }
            int index = (int) Math.ceil(log.length() / 3000.0);
            for (int i = 0; i < index; i++){
                int start = i * 3000;
                int end = 3000 + i * 3000;
                if (end >= log.length()){
                    end = log.length();
                }
                if (isResponse){
                    PrintLog.d(TAG, i == 0 ? "[" + tag + "] <--- " + log.substring(start, end) : log.substring(start, end));
                }else {
                    PrintLog.i(TAG, i == 0 ? "[" + tag + "] ---> " + log.substring(start, end) : log.substring(start, end));
                }
                if (end == log.length()){
                    return;
                }
            }
        }
    }

    /**
     * 创建对应的ApiService
     * @param service 接口类
     */
    public <T> T create(final Class<T> service){
        return mRetrofit.create(service);
    }
}
