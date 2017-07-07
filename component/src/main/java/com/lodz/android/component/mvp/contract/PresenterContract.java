package com.lodz.android.component.mvp.contract;

/**
 * Presenter接口
 * Created by zhouL on 2017/7/7.
 */

public interface PresenterContract<VC extends ViewContract> {

    void attachView(VC view);

    void detachView();

}
