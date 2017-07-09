package com.lodz.android.agiledev.ui.mvp;

import android.support.v7.app.AlertDialog;

import com.lodz.android.component.mvp.base.BasePresenter;
import com.lodz.android.component.utils.ProgressDialogHelper;

/**
 * 测试Presenter
 * Created by zhouL on 2017/7/7.
 */

public class MvpTestPresenter extends BasePresenter<MvpTestViewContract>{

    /** 数据来源 */
    private ApiModule mApiModule;

    public MvpTestPresenter() {
        this.mApiModule = new ApiModule();
    }

    public void getResult(){
        final AlertDialog dialog = ProgressDialogHelper.getProgressDialog(getContext(), "loading", true, true);
        dialog.show();
        mApiModule.requestResult(new ApiModule.Listener() {
            @Override
            public void onCallback(String response) {
                getViewContract().showResult();
                getViewContract().setResult(response);
                dialog.dismiss();
            }
        });
    }
}
