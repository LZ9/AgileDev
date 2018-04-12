package com.lodz.android.agiledev.bean;

/**
 * 用户信息
 * Created by zhouL on 2018/4/11.
 */
public class UserBean {

    /** 账号 */
    public String account;
    /** 密码 */
    public String pswd;

    public UserBean(String account, String pswd) {
        this.account = account;
        this.pswd = pswd;
    }
}
