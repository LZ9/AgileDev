package com.lodz.android.component.widget.adapter.decoration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * RV基础装饰器
 * Created by zhouL on 2018/2/7.
 */

public class BaseItemDecoration extends RecyclerView.ItemDecoration{

    private Context mContext;

    public BaseItemDecoration(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 校验数值
     * @param value 数值
     */
    protected int checkValue(int value){
        return value <= 0 ? 0 : value;
    }
}
