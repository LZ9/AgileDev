package com.lodz.android.agiledev.utils.network;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 网络管理
 * Created by zhouL on 2016/11/24.
 */

public class NetworkManager {

    private static NetworkManager mInstance;

    public static NetworkManager get() {
        if (mInstance == null) {
            synchronized (NetworkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetworkManager();
                }
            }
        }
        return mInstance;
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

    /** 更新网络信息 */
    public void updateNet(ConnectivityManager manager){
        mNetInfo.type = NetInfo.NETWORK_TYPE_NONE;
        mNetInfo.standard = NetInfo.NETWORK_TYPE_NONE;
        if (manager == null){
            return;
        }

        NetworkInfo netInfo = manager.getActiveNetworkInfo();
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
            case 17:
            case 18:
                type = NetInfo.NETWORK_TYPE_3G;
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
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
            return info.getType();//这里做异常捕获防止一些ROM被修改这个类型不是int型
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
            return info.getSubtype();//这里做异常捕获防止一些ROM被修改这个类型不是int型
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
    public void notifyNetworkListeners() {
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
