package com.lodz.android.agiledev.ui.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.ui.splash.CheckDialog;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.AppUtils;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 定位测试
 * Created by zhouL on 2017/11/29.
 */
@RuntimePermissions
public class LocationActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, LocationActivity.class);
        context.startActivity(starter);
    }

    /** 定位按钮 */
    @BindView(R.id.location_btn)
    Button mLocationBtn;

    /** 结果 */
    @BindView(R.id.result)
    TextView mResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
    }

    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocalLibView();
            }
        });
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
        LocationActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(LocationActivity.this);
    }

    @Override
    protected void initData() {
        super.initData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){// 6.0以上的手机对权限进行动态申请
            LocationActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(LocationActivity.this);
        }else {
            init();
        }
    }

    /** 初始化 */
    private void init(){
        showStatusCompleted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);//将回调交给代理类处理
    }

    /** 权限申请成功 */
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION})// 照相
    protected void requestPermission() {
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)){
            return;
        }
        init();
    }

    /** 被拒绝 */
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION})// 照相
    protected void onDenied() {
        ToastUtils.showShort(this, "你拒绝了此权限，该功能不可用");
        showStatusError();
    }

    /** 用户拒绝后再次申请前告知用户为什么需要该权限 */
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION})// 照相
    protected void showRationaleBeforeRequest(PermissionRequest request) {
        request.proceed();//请求权限
    }

    /** 被拒绝并且勾选了不再提醒 */
    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION})// 照相
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
                LocationActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(LocationActivity.this);//申请权限
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
                finish();
            }
        });
        dialog.show();
    }



    LocationManager locationManager; //系统定位


    private void initLocalLibView() {
        //获取定位服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //检查定位是否被打开
        boolean gpsIsOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        boolean networkIsOpen = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        boolean passiveIsOpen = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);


//        if (gpsIsOpen || networkIsOpen) {
//            //定位 并请求接口
//            //定位
//            startLocation();
//        } else {
//            Toast.makeText(this, "请打开地理位置，我们帮你找到最近的图书馆", Toast.LENGTH_SHORT).show();
////            T.showShort("请打开地理位置，我们帮你找到最近的图书馆");
//        }

        startLocation();
    }

    @SuppressLint("MissingPermission")
    private void startLocation() {

        // 为获取地理位置信息时设置查询条件 是按GPS定位还是network定位
        String bestProvider = getProvider();
         Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location == null){
            mResult.setText("类型：" + bestProvider + " ---> 未获取到经纬度");
            return;
        }

        mResult.setText("类型：" + bestProvider + " ---> Location" + "纬度：" + location.getLatitude() + "\n" +  "经度:  " + location.getLongitude());



//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1 * 1000, 0, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                if (location == null){
//                    mResult.setText("类型：" + location.getProvider() + " ---> 未获取到经纬度");
//                    return;
//                }
//                mResult.setText("类型：" + location.getProvider() + " ---> Location" + "纬度：" + location.getLatitude() + "\n" +  "经度:  " + location.getLongitude());
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                mResult.setText("onStatusChanged  status : " + status);
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//                mResult.setText("onProviderEnabled  provider : " + provider);
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                mResult.setText("onProviderDisabled  provider : " + provider);
//            }
//        });

    }



    /**
     * 定位查询条件
     * 返回查询条件 ，获取目前设备状态下，最适合的定位方式
     */
    private String getProvider() {
        // 构建位置查询条件
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        //Criteria.ACCURACY_FINE,当使用该值时，在建筑物当中，可能定位不了,建议在对定位要求并不是很高的时候用Criteria.ACCURACY_COARSE，避免定位失败
        // 查询精度：高
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 是否查询海拨：否
        criteria.setAltitudeRequired(false);
        // 是否查询方位角 : 否
        criteria.setBearingRequired(false);
        // 是否允许付费：是
        criteria.setCostAllowed(false);
        // 电量要求：低
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 返回最合适的符合条件的provider，第2个参数为true说明 , 如果只有一个provider是有效的,则返回当前provider
        return locationManager.getBestProvider(criteria, true);
    }




    // 转到手机设置界面，用户设置GPS
//    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//    startActivityForResult(intent, START_ACTION_LOCATION_SOURCE_SETTINGS); // 设置完成后返回到原来的界面










}
