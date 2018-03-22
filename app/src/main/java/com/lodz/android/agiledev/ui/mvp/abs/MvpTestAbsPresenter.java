package com.lodz.android.agiledev.ui.mvp.abs;

import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.mvp.presenter.AbsPresenter;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.component.rx.utils.RxUtils;

/**
 * 测试Presenter
 * Created by zhouL on 2017/7/7.
 */

public class MvpTestAbsPresenter extends AbsPresenter<MvpTestAbsViewContract> {


    public void getResult(){
        ApiModule.requestResult()
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilDetachEvent())
                .subscribe(new ProgressObserver<String>() {
                    @Override
                    public void onPgNext(String s) {
                        getViewContract().showResult();
                        getViewContract().setResult(s);
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {
                        getViewContract().setResult("fail");
                    }
                }.create(getContext(), "加载中...", false));
    }
}
