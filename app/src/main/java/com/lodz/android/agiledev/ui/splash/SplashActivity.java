package com.lodz.android.agiledev.ui.splash;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lodz.android.agiledev.App;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.utils.crash.CrashHandler;
import com.lodz.android.agiledev.utils.file.FileManager;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.cache.ACacheUtils;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.AppUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.core.utils.UiHandler;

import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 启动页
 * Created by zhouL on 2017/8/30.
 */

@RuntimePermissions
public class SplashActivity extends AbsActivity{

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_splash_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        super.initData();
        UiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){// 6.0以上的手机对权限进行动态申请
                    SplashActivityPermissionsDispatcher.requestPermissionWithCheck(SplashActivity.this);//申请权限
                }else {
                    init();
                }
            }
        }, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);//将回调交给代理类处理
    }

    /** 权限申请成功 */
    @NeedsPermission({
            Manifest.permission.READ_PHONE_STATE,// 手机状态
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 存储卡读写
            Manifest.permission.RECORD_AUDIO,// 声音
            Manifest.permission.CAMERA// 照相
    })
    protected void requestPermission() {
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.READ_PHONE_STATE)){
            return;
        }
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            return;
        }
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.RECORD_AUDIO)){
            return;
        }
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.CAMERA)){
            return;
        }
        init();
    }

    /** 被拒绝 */
    @OnPermissionDenied({
            Manifest.permission.READ_PHONE_STATE,// 手机状态
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 存储卡读写
            Manifest.permission.RECORD_AUDIO,// 声音
            Manifest.permission.CAMERA// 照相
    })
    protected void onDenied() {
        SplashActivityPermissionsDispatcher.requestPermissionWithCheck(this);//申请权限
    }

    /** 用户拒绝后再次申请前告知用户为什么需要该权限 */
    @OnShowRationale({
            Manifest.permission.READ_PHONE_STATE,// 手机状态
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 存储卡读写
            Manifest.permission.RECORD_AUDIO,// 声音
            Manifest.permission.CAMERA// 照相
    })
    protected void showRationaleBeforeRequest(PermissionRequest request) {
        request.proceed();//请求权限
    }

    /** 被拒绝并且勾选了不再提醒 */
    @OnNeverAskAgain({
            Manifest.permission.READ_PHONE_STATE,// 手机状态
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 存储卡读写
            Manifest.permission.RECORD_AUDIO,// 声音
            Manifest.permission.CAMERA// 照相
    })
    protected void onNeverAskAgain() {
        ToastUtils.showShort(getContext(), R.string.splash_check_permission_tips);
        showPermissionCheckDialog();
        AppUtils.jumpAppDetailSetting(this);
    }

    /** 显示权限核对弹框 */
    private void showPermissionCheckDialog() {
        CheckDialog dialog = new CheckDialog(getContext());
        dialog.setContentMsg(R.string.splash_check_permission_title);
        dialog.setPositiveText(R.string.splash_check_permission_confirm, new CheckDialog.Listener() {
            @Override
            public void onClick(Dialog dialog) {
                SplashActivityPermissionsDispatcher.requestPermissionWithCheck(SplashActivity.this);//申请权限
                dialog.dismiss();
            }
        });
        dialog.setNegativeText(R.string.splash_check_permission_unconfirmed, new CheckDialog.Listener() {
            @Override
            public void onClick(Dialog dialog) {
                AppUtils.jumpAppDetailSetting(getContext());
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ToastUtils.showShort(getContext(), R.string.splash_check_permission_cancel);
                App.get().exit();
            }
        });
        dialog.show();
    }


    private void init() {
        PrintLog.d("testtag", "init");
        FileManager.init();// 初始化文件管理
        initCrashHandler();//初始化异常处理
        initACache();// 初始化缓存类
        jumpMainActivity();
    }

    /** 初始化异常处理 */
    private void initCrashHandler() {
        CrashHandler.get()
                .setLauncherClass(MainActivity.class)
                .setInterceptor(true)
//                .setToastTips("嗝屁啦")
                .setSaveFolderPath(FileManager.getCrashFolderPath())
//                .setLogFileName("heheda.log")
                .init();
    }

    /** 初始化缓存类 */
    private void initACache() {
        ACacheUtils.get().newBuilder()
                .setCacheDir(FileManager.getCacheFolderPath())
//                .setMaxSize(1024 * 1024 * 50)
//                .setMaxCount(Integer.MAX_VALUE)
                .build(this);
    }

    private void jumpMainActivity() {
        MainActivity.start(getContext());
        finish();
    }
}
