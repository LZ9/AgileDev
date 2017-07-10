package com.lodz.android.agiledev.ui.mvp.abs;

import android.support.v7.app.AlertDialog;

import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.mvp.presenter.AbsPresenter;
import com.lodz.android.component.utils.ProgressDialogHelper;

/**
 * 测试Presenter
 * Created by zhouL on 2017/7/7.
 */

public class MvpTestAbsPresenter extends AbsPresenter<MvpTestAbsViewContract> {

    /** 数据来源 */
    private ApiModule mApiModule;

    public MvpTestAbsPresenter() {
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
