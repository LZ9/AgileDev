package com.lodz.android.agiledev.ui.mvp.sandwich;

import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.mvp.presenter.BasePresenter;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;

/**
 * 测试Presenter
 * Created by zhouL on 2018/4/17.
 */
public class MvpTestSandwichPresenter extends BasePresenter<MvpTestSandwichViewContract> {

    public void getResult(boolean isSuccess){
        ApiModule.requestResult(isSuccess)
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilDetachEvent())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onBaseNext(String s) {
                        if (isDetach()){
                            return;
                        }
                        getViewContract().showResult();
                        getViewContract().setResult(s);
                        getViewContract().showStatusCompleted();
                    }

                    @Override
                    public void onBaseError(Throwable e) {
                        if (isDetach()){
                            return;
                        }
                        getViewContract().showStatusError();
                    }
                });
    }

    public void getRefreshData(boolean isSuccess){
        ApiModule.requestResult(isSuccess)
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilDetachEvent())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onBaseNext(String s) {
                        if (isDetach()){
                            return;
                        }
                        getViewContract().setSwipeRefreshFinish();
                        getViewContract().showResult();
                        getViewContract().setResult(s);
                        getViewContract().showStatusCompleted();
                    }

                    @Override
                    public void onBaseError(Throwable e) {
                        if (isDetach()){
                            return;
                        }
                        getViewContract().setSwipeRefreshFinish();
                        getViewContract().refreshFail("刷新数据失败");
                    }
                });
    }

}
