package com.lodz.android.component.mvp.base;

import android.content.Context;

import com.lodz.android.component.mvp.contract.PresenterContract;
import com.lodz.android.component.mvp.contract.ViewContract;

/**
 * 基类Presenter
 * Created by zhouL on 2017/7/7.
 */

public class BasePresenter<VC extends ViewContract> implements PresenterContract<VC>{

    /** View接口 */
    private VC mViewContract;
    /** 上下文 */
    private Context mContext;

    public Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate(Context context, VC view) {
        if (mViewContract != null){
            mViewContract = null;
        }
        mContext = context;
        mViewContract = view;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    public VC getViewContract(){
        return mViewContract;
    }


    @Override
    public void onDestroy() {
        mContext = null;
        mViewContract = null;
    }
}
