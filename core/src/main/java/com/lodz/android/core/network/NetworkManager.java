package com.lodz.android.core.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 网络管理
 * Created by zhouL on 2016/11/24.
 */

public class NetworkManager {

    private static NetworkManager mInstance = new NetworkManager();

    public static NetworkManager get() {
        return mInstance;
    }

    private NetworkManager() {
    }

    /** 网络广播 */
    private ConnectBroadcastReceiver mReceiver;
    /** 网络信息 */
    private NetInfo mNetInfo = new NetInfo();
    /** 网络监听器 */
    private List<NetworkListener> mNetworkListeners = new ArrayList<>();

    /**
     * 注册网络监听广播
     * @param context 上下文
     */
    public void init(Context context){
        try {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            mReceiver = new ConnectBroadcastReceiver();
            context.registerReceiver(mReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解除网络监听广播
     * @param context 上下文
     */
    public void release(Context context) {
        try {
            if (mReceiver != null) {
                context.unregisterReceiver(mReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 网络是否可用 */
    public boolean isNetworkAvailable() {
        return mNetInfo.type != NetInfo.NETWORK_TYPE_NONE;
    }

    /** 是否在wifi下 */
    public boolean isWifi() {
        return mNetInfo.type == NetInfo.NETWORK_TYPE_WIFI;
    }

    /** 获取当前网络类型（未连接/WIFI/4G/3G/2G） */
    @NetInfo.NetType
    public int getNetType(){
        return mNetInfo.type;
    }

    /**
     * 获取运营商代号（0：未知 1：移动 2：联通 3：电信）
     * @param context 上下文
     */
    @OperatorInfo.OperatorType
    public int getSimOperator(Context context) {
        if (context == null){
            return OperatorInfo.OPERATOR_UNKNOWN;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null){
            return OperatorInfo.OPERATOR_UNKNOWN;
        }
        String opeator = telephonyManager.getSimOperator();
        if (TextUtils.isEmpty(opeator)) {
            return OperatorInfo.OPERATOR_UNKNOWN;
        }

        if (opeator.equals("46000") || opeator.equals("46002") || opeator.equals("46007")) {// 中国移动
            return OperatorInfo.OPERATOR_CMCC;
        } else if (opeator.equals("46001")) {// 中国联通
            return OperatorInfo.OPERATOR_CUCC;
        } else if (opeator.equals("46003")) {// 中国电信
            return OperatorInfo.OPERATOR_CTCC;
        }
        // 未知
        return OperatorInfo.OPERATOR_UNKNOWN;
    }

    /**
     * 获取运营商（基站）信息
     * @param context 上下文
     */
    @SuppressLint("MissingPermission")
    public OperatorInfo getOperatorInfo(Context context){
        if (context == null){
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null){
            return null;
        }
        int simOperator = getSimOperator(context);
        OperatorInfo info = new OperatorInfo();
        info.type = simOperator;
        if (!info.isSuccess()){
            return null;
        }

        try {
            // 移动或联通
            if (simOperator == OperatorInfo.OPERATOR_CMCC || simOperator == OperatorInfo.OPERATOR_CUCC) {
                GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();
                if (location == null){
                    return null;
                }
                info.cid = String.valueOf(location.getCid());
                info.lac = String.valueOf(location.getLac());
                info.mcc = telephonyManager.getNetworkOperator().substring(0, 3);
                info.mnc = telephonyManager.getNetworkOperator().substring(3);
                return info;
            }

            // 电信
            if (simOperator == OperatorInfo.OPERATOR_CTCC) {
                CellLocation cellLocation = telephonyManager.getCellLocation();
                if (cellLocation == null){
                    return null;
                }
                if (cellLocation instanceof CdmaCellLocation) {
                    CdmaCellLocation location = (CdmaCellLocation) cellLocation;
                    info.cid = String.valueOf(location.getBaseStationId());
                    info.lac = String.valueOf(location.getNetworkId());
                    info.mcc = telephonyManager.getNetworkOperator().substring(0, 3);
                    info.mnc = telephonyManager.getNetworkOperator();
                    return info;
                }else if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation location = (GsmCellLocation) cellLocation;
                    info.cid = String.valueOf(location.getCid());
                    info.lac = String.valueOf(location.getLac());
                    info.mcc = telephonyManager.getNetworkOperator().substring(0, 3);
                    info.mnc = telephonyManager.getNetworkOperator().substring(3);
                    return info;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /** 更新网络信息 */
    @SuppressLint("MissingPermission")
    void updateNet(ConnectivityManager manager){
        mNetInfo.type = NetInfo.NETWORK_TYPE_NONE;
        mNetInfo.standard = NetInfo.NETWORK_TYPE_NONE;
        if (manager == null){
            return;
        }
        NetworkInfo netInfo = null;
        try {
            netInfo = manager.getActiveNetworkInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (netInfo == null || !netInfo.isAvailable()){//网络未连接
            return;
        }

        int type = getType(netInfo);
        if (type == ConnectivityManager.TYPE_WIFI){// wifi下
            mNetInfo.type = NetInfo.NETWORK_TYPE_WIFI;
            mNetInfo.standard = NetInfo.NETWORK_TYPE_WIFI;
            return;
        }

        int subType = getSubType(netInfo);
        switch (subType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_IDEN:
            case TelephonyManager.NETWORK_TYPE_GSM:
                type = NetInfo.NETWORK_TYPE_2G;
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
            case TelephonyManager.NETWORK_TYPE_IWLAN:
                type = NetInfo.NETWORK_TYPE_3G;
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
            case 19:// TelephonyManager.NETWORK_TYPE_LTE_CA
                type = NetInfo.NETWORK_TYPE_4G;
                break;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            default:
                type = NetInfo.NETWORK_TYPE_UNKNOWN;
                break;
        }
        mNetInfo.type = type;
        mNetInfo.standard = subType;
        mNetInfo.extraInfo = netInfo.getExtraInfo();
    }

    /**
     * 获取网络类型
     * @param info 网络信息类
     */
    private int getType(NetworkInfo info){
        try {
            //这里做类型判断防止一些ROM被修改这个类型不是int型
            Object type = info.getType();
            if (type instanceof Integer){
                return (int) type;
            }
            if (type instanceof String){
                String typeStr = (String) type;
                if (!TextUtils.isEmpty(typeStr)) {
                    return Integer.parseInt(typeStr);
                }
            }
            if (type instanceof Double || type instanceof Float){
                return (int) type;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return NetInfo.NETWORK_TYPE_UNKNOWN;
    }

    /**
     * 获取网络制式
     * @param info 网络信息类
     */
    private int getSubType(NetworkInfo info){
        try {
            //这里做类型判断防止一些ROM被修改这个类型不是int型
            Object type = info.getSubtype();
            if (type instanceof Integer){
                return (int) type;
            }
            if (type instanceof String){
                String typeStr = (String) type;
                if (!TextUtils.isEmpty(typeStr)) {
                    return Integer.parseInt(typeStr);
                }
            }
            if (type instanceof Double || type instanceof Float){
                return (int) type;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return TelephonyManager.NETWORK_TYPE_UNKNOWN;
    }

    /**
     * 添加网络监听器
     * @param listener 监听器
     */
    public void addNetworkListener(NetworkListener listener) {
        mNetworkListeners.add(listener);
    }

    /**
     * 删除网络监听器
     * @param listener 监听器
     */
    public void removeNetworkListener(NetworkListener listener) {
        mNetworkListeners.remove(listener);
    }

    /** 清除所有监听器 */
    public void clearNetworkListener(){
        mNetworkListeners.clear();
    }

    /** 通知监听器回调 */
    void notifyNetworkListeners() {
        Iterator<NetworkListener> iterator = mNetworkListeners.iterator();
        while (iterator.hasNext()){
            NetworkListener listener = iterator.next();
            if (listener != null){
                listener.onNetworkStatusChanged(isNetworkAvailable(), mNetInfo);
            }else{
                iterator.remove();
            }
        }
    }

    /** 网络监听器 */
    public interface NetworkListener {
        /**
         * 网络状态变化
         * @param isNetworkAvailable 网络是否可用
         * @param netInfo 网络信息
         */
        void onNetworkStatusChanged(boolean isNetworkAvailable, NetInfo netInfo);
    }
}
