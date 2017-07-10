package com.lodz.android.agiledev.ui.mvp.base;

import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.mvp.presenter.BasePresenter;

/**
 * 测试Presenter
 * Created by zhouL on 2017/7/7.
 */

public class MvpTestBasePresenter extends BasePresenter<MvpTestBaseViewContract> {

    /** 数据来源 */
    private ApiModule mApiModule;

    public MvpTestBasePresenter() {
        this.mApiModule = new ApiModule();
    }

    public void getResult(){
        mApiModule.requestResult(new ApiModule.Listener() {
            @Override
            public void onCallback(String response) {
                getViewContract().showResult();
                getViewContract().setResult(response);
                getViewContract().showStatusCompleted();
            }
        });
    }
}
