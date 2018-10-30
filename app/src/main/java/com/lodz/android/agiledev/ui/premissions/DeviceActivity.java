package com.lodz.android.agiledev.ui.premissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.DeviceUtils;
import com.lodz.android.core.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 获取设备信息测试类
 * Created by zhouL on 2017/3/22.
 */
@RuntimePermissions
public class DeviceActivity extends AbsActivity{

    Button mImeiBtn;

    Button mImsiBtn;

    Button mUaBtn;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_device_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mImeiBtn = findViewById(R.id.imei_btn);
        mImsiBtn = findViewById(R.id.imsi_btn);
        mUaBtn = findViewById(R.id.ua_btn);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mImeiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceActivityPermissionsDispatcher.getIMEIWithPermissionCheck(DeviceActivity.this);
            }
        });

        mImsiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceActivityPermissionsDispatcher.getIMSIWithPermissionCheck(DeviceActivity.this);
            }
        });

        mUaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintLog.e("testtag", DeviceUtils.getUserAgent());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DeviceActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);//将回调交给代理类处理
    }

    //权限申请成功
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    protected void getIMEI() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        PrintLog.d("testtag", DeviceUtils.getIMEI(getContext()));
    }

    //权限申请成功
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    protected void getIMSI() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        PrintLog.i("testtag", DeviceUtils.getIMSI(getContext()));
    }

    //被拒绝
    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    protected void onDenied() {
        ToastUtils.showShort(this, "你拒绝了此权限，该功能不可用");
    }

    //用户拒绝后再次申请前告知用户为什么需要该权限
    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    protected void showRationaleBeforeRequest(PermissionRequest request) {
        showRequestRationaleDialog("使用此功能需要读取手机状态权限", request);
    }

    //被拒绝并且勾选了不再提醒
    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    protected void onNeverAskAgain() {
        AskForPermission("该操作需要读取您的手机状态，请前往设置页面打开此权限");
    }

    /**
     * 显示申请权限理由弹框
     * @param str 申请理由
     * @param request 请求对象
     */
    private void showRequestRationaleDialog(String str, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();//请求权限
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(str)
                .show();
    }

    /**
     * 被拒绝并且不再提醒,提示用户去设置界面重新打开权限
     * @param askMsg 询问信息
     */
    private void AskForPermission(String askMsg) {
        new AlertDialog.Builder(this)
                .setTitle(askMsg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                        startActivity(intent);
                    }
                })
                .setCancelable(false)
                .show();
    }

}
