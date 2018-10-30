package com.lodz.android.agiledev.ui.premissions;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.utils.AppUtils;

import androidx.annotation.NonNull;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 权限申请测试类
 * Created by zhouL on 2017/2/7.
 */
@RuntimePermissions
public class PremissionsActivity extends BaseActivity {
    private static final int CALL_CAMERA_REQUEST_CODE = 1;

    private Button mCallButton;

    private Button mPhotoButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_premissions_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mCallButton = findViewById(R.id.btn_call);
        mPhotoButton = findViewById(R.id.btn_photo);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PremissionsActivityPermissionsDispatcher.callPhoneWithCheck(PremissionsActivity.this);//发起权限申请
                PremissionsActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(PremissionsActivity.this);
            }
        });
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PremissionsActivityPermissionsDispatcher.callCameraWithCheck(PremissionsActivity.this);//发起权限申请
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PremissionsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /** 权限申请成功 */
    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestPermission() {
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.READ_PHONE_STATE)){
            return;
        }
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            return;
        }
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            return;
        }
        // do something
    }

    /** 用户拒绝后再次申请前告知用户为什么需要该权限 */
    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleBeforeRequest(final PermissionRequest request) {
        request.proceed();//请求权限
    }

    /** 被拒绝 */
    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onDenied() {
        PremissionsActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(this);
    }

    /** 被拒绝并且勾选了不再提醒 */
    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onNeverAskAgain() {
        AppUtils.jumpAppDetailSetting(this);
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PremissionsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);//将回调交给代理类处理
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case CALL_CAMERA_REQUEST_CODE:
//                if (resultCode == Activity.RESULT_OK) {
//                    Bitmap bmap = data.getParcelableExtra("data");
//                    ToastUtils.showShort(this, "头像保存成功");
//                }
//
//                break;
//        }
//    }
//
//    @NeedsPermission(Manifest.permission.CALL_PHONE)//权限申请成功
//    protected void callPhone() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Intent intent = new Intent(Intent.ACTION_CALL);
//        Uri data = Uri.parse("tel:1234567890");
//        intent.setData(data);
//        startActivity(intent);
//    }
//
//    @OnShowRationale(Manifest.permission.CALL_PHONE)//申请前告知用户为什么需要该权限
//    protected void showRationaleForCallPhoto(PermissionRequest request) {
//        showRationaleDialog("使用此功能需要打开拨号的权限", request);
//    }
//
//    @OnPermissionDenied(Manifest.permission.CALL_PHONE)//被拒绝
//    protected void onCallPhoneDenied() {
//        ToastUtils.showShort(this, "你拒绝了权限，该功能不可用");
//    }
//
//    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)//被拒绝并且勾选了不再提醒
//    protected void onCallPhoneNeverAskAgain() {
//        AskForPermission();
//    }
//
//    @NeedsPermission(Manifest.permission.CAMERA)//权限申请成功
//    protected void callCamera() {
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(openCameraIntent, CALL_CAMERA_REQUEST_CODE);
//    }
//
//    @OnShowRationale(Manifest.permission.CAMERA)//申请前告知用户为什么需要该权限
//    protected void showRationaleForCamera(PermissionRequest request) {
//        showRationaleDialog("使用此功能需要打开照相机的权限", request);
//    }
//
//    @OnPermissionDenied(Manifest.permission.CAMERA)//被拒绝
//    protected void onCameraDenied() {
//        ToastUtils.showShort(this, "你拒绝了权限，该功能不可用");
//    }
//
//    @OnNeverAskAgain(Manifest.permission.CAMERA)//被拒绝并且勾选了不再提醒
//    protected void onCameraNeverAskAgain() {
//        AskForPermission();
//    }
//
//    /**
//     * 告知用户具体需要权限的原因
//     * @param messageResId
//     * @param request
//     */
//    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
//        new AlertDialog.Builder(this)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(@NonNull DialogInterface dialog, int which) {
//                        request.proceed();//请求权限
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(@NonNull DialogInterface dialog, int which) {
//                        request.cancel();
//                    }
//                })
//                .setCancelable(false)
//                .setMessage(messageResId)
//                .show();
//    }
//
//    /**
//     * 被拒绝并且不再提醒,提示用户去设置界面重新打开权限
//     */
//    private void AskForPermission() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("当前应用缺少拍照权限,请去设置界面打开\n打开之后按两次返回键可回到该应用哦");
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
//                startActivity(intent);
//            }
//        });
//        builder.create().show();
//    }


}
