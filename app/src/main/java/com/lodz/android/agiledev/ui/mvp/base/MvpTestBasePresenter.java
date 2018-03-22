package com.lodz.android.agiledev.ui.mvp.base;

import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.mvp.presenter.BasePresenter;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;

/**
 * 测试Presenter
 * Created by zhouL on 2017/7/7.
 */

public class MvpTestBasePresenter extends BasePresenter<MvpTestBaseViewContract> {

    public void getResult(){
        ApiModule.requestResult()
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilDetachEvent())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onBaseNext(String s) {
                        getViewContract().showResult();
                        getViewContract().setResult(s);
                        getViewContract().showStatusCompleted();
                    }

                    @Override
                    public void onBaseError(Throwable e) {
                        getViewContract().showStatusError();
                    }
                });
    }
}
