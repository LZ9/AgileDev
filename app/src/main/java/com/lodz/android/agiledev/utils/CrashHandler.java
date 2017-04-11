package com.lodz.android.agiledev.utils;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.lodz.android.component.base.BaseApplication;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.AppUtils;
import com.lodz.android.core.utils.DateUtils;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 崩溃处理类
 * Created by zhouL on 2017/4/11.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashTag";

    private static CrashHandler mInstance;

    public static CrashHandler get() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    private CrashHandler() {
    }

    /** 系统默认的UncaughtException处理类 */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /** 是否拦截 */
    private boolean isInterceptor = true;

    /** 初始化代码 */
     public CrashHandler init(){
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
         return this;
    }

    /**
     * 设置是否对异常进行拦截
     * @param interceptor 是否拦截
     */
    public void setInterceptor(boolean interceptor) {
        isInterceptor = interceptor;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable t) {
        if (isInterceptor){// 用户处理
            PrintLog.d(TAG, "user handle");
            handleException(t);
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PrintLog.e(TAG, "error: ", t);
            t.printStackTrace();
            exceptionExit();
            return;
        }

        if (mDefaultHandler != null){
            PrintLog.d(TAG, "system handle");
            mDefaultHandler.uncaughtException(thread, t);
            return;
        }
        PrintLog.d(TAG, "unhandle");
        exceptionExit();
    }

    /** 异常退出 */
    private void exceptionExit(){
       android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);  // 非0表示异常退出
    }

    /**
     * 处理异常
     * @param t 异常
     */
    private void handleException(Throwable t) {
        showToast();
        Map<String, String> deviceInfos = getDeviceInfo(BaseApplication.get().getApplicationContext());
        String content = getLogContent(deviceInfos, t);
        boolean isSaveSuccess = saveCrashLogInFile(FileManager.getCrashFolderPath(), content);
        PrintLog.d(TAG, "保存崩溃日志 ： " + isSaveSuccess);
    }

    /** 显示提示语 */
    private void showToast() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(BaseApplication.get().getApplicationContext(), "很抱歉，程序出现异常即将退出", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }

    /**
     * 保存崩溃信息到本地文件
     * @param folderPath 保存路径
     * @param content 保存信息
     */
    private boolean saveCrashLogInFile(String folderPath, String content) {
        try {
            long timestamp = System.currentTimeMillis();
            String time = DateUtils.getFormatString(DateUtils.FormatType.Type_7, new Date(timestamp));
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            FileOutputStream fos = new FileOutputStream(folderPath + fileName);
            fos.write(content.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取设备信息
     * @param context 上下文
     */
    private Map<String, String> getDeviceInfo(Context context) {
        Map<String, String> infos = new HashMap<>();
        try {
            infos.put("versionName", AppUtils.getAppName(context));
            infos.put("versionCode", AppUtils.getVersionCode(context) + "");
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            }
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                PrintLog.i(TAG, entry.getKey() + " ：" + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infos;
    }

    /**
     * 获取日志内容
     * @param deviceInfos 设备信息
     * @param t 异常
     */
    private String getLogContent(Map<String, String> deviceInfos, Throwable t){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : deviceInfos.entrySet()) {
            String msg = entry.getKey() + "=" + entry.getValue() + "\n";
            stringBuilder.append(msg);
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        t.printStackTrace(printWriter);
        Throwable cause = t.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        stringBuilder.append(result);
        return stringBuilder.toString();
    }
}
