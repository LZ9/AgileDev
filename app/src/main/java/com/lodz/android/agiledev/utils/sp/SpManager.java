package com.lodz.android.agiledev.utils.sp;

/**
 * SharedPreferences管理类
 * Created by zhouL on 2016/12/26.
 */
public class SpManager {

    private static SpManager mInstance;

    public static SpManager get() {
        if (mInstance == null) {
            synchronized (SpManager.class) {
                if (mInstance == null) {
                    mInstance = new SpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置用户账号
     * @param account 账号
     */
    public void setUserAccount(String account){
        SharedPreferencesUtils.putString(SpConfig.USER_ACCOUNT, account);
    }

    /** 获取用户账号 */
    public String getUserAccount(){
        return SharedPreferencesUtils.getString(SpConfig.USER_ACCOUNT, "");
    }


}
