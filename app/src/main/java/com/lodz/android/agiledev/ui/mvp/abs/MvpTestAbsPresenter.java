package com.lodz.android.agiledev.ui.mvp.abs;

import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.mvp.presenter.BasePresenter;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.component.rx.utils.RxUtils;

/**
 * 测试Presenter
 * Created by zhouL on 2017/7/7.
 */

public class MvpTestAbsPresenter extends BasePresenter<MvpTestAbsViewContract> {


    public void getResult(boolean isSuccess){
        ApiModule.requestResult(isSuccess)
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilDetachEvent())
                .subscribe(new ProgressObserver<String>() {
                    @Override
                    public void onPgNext(String s) {
                        if (isDetach()){
                            return;
                        }
                        getViewContract().showResult();
                        getViewContract().setResult(s);
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {
                        if (isDetach()){
                            return;
                        }
                        getViewContract().setResult("fail");
                    }
                }.create(getContext(), "加载中...", true));
    }
}
