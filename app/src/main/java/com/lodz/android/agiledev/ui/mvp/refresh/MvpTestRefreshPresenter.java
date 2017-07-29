package com.lodz.android.agiledev.ui.mvp.refresh;

import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.mvp.presenter.BaseRefreshPresenter;

/**
 * 测试Presenter
 * Created by zhouL on 2017/7/29.
 */

public class MvpTestRefreshPresenter extends BaseRefreshPresenter<MvpTestRefreshViewContract>{

    /** 数据来源 */
    private ApiModule mApiModule;

    public MvpTestRefreshPresenter() {
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

    public void getRefreshData(){
        mApiModule.requestResult(new ApiModule.Listener() {
            @Override
            public void onCallback(String response) {
                getViewContract().setSwipeRefreshFinish();
                getViewContract().refreshFail("刷新数据失败");
//                getViewContract().showResult();
//                getViewContract().setResult(response);


            }
        });
    }
}
