package com.lodz.android.component.rx.status;

/**
 * 接口数据状态
 * Created by zhouL on 2017/2/4.
 */
public interface ResponseStatus {

    /** 数据是否返回成功 */
    boolean isSuccess();

    /** 获取提示语信息 */
    String getMsg();
}
