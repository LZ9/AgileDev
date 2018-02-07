package com.lodz.android.agiledev.bean;

import com.lodz.android.agiledev.bean.base.BaseRequestBean;

/**
 * 景点数据
 * Created by zhouL on 2018/2/6.
 */

public class SpotRequestBean extends BaseRequestBean{

    /** 编号 */
    public String id = "";
    /** 输出 */
    public String output = "";

    public SpotRequestBean(String id, String output) {
        this.id = id;
        this.output = output;
    }
}
