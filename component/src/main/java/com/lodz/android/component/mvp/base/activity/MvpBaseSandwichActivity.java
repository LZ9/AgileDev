package com.lodz.android.component.mvp.base.activity;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.activity.BaseSandwichActivity;
import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.sandwich.BaseSandwichViewContract;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * MVP基类Activity（带基础状态控件、中部刷新控件和顶部/底部扩展）
 * Created by zhouL on 2018/4/17.
 */
public abstract class MvpBaseSandwichActivity<PC extends PresenterContract<VC>, VC extends BaseSandwichViewContract> extends BaseSandwichActivity implements BaseSandwichViewContract{

    /** Presenter接口 */
    private PC mPresenterContract;

    @Override
    @SuppressWarnings("unchecked")
    protected void startCreate() {
        super.startCreate();
        mPresenterContract = createMainPresenter();
        if (mPresenterContract != null){
            mPresenterContract.attach(this, (VC) this);
        }
    }

    protected abstract PC createMainPresenter();

    @NonNull
    protected final PC getPresenterContract(){
        return mPresenterContract;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenterContract != null){
            mPresenterContract.detach();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (mPresenterContract != null){
            mPresenterContract.detach();
        }
    }

    @Override
    public void showStatusNoData() {
        super.showStatusNoData();
    }

    @Override
    public void showStatusError() {
        super.showStatusError();
    }

    @Override
    public void showStatusLoading() {
        super.showStatusLoading();
    }

    @Override
    public void showStatusCompleted() {
        super.showStatusCompleted();
    }

    @Override
    public void setSwipeRefreshFinish() {
        super.setSwipeRefreshFinish();
    }

    @Override
    public void setSwipeRefreshEnabled(boolean enabled) {
        super.setSwipeRefreshEnabled(enabled);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event) {
        return bindUntilEvent(event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event) {
        throw new IllegalArgumentException("you bind activity but call fragment event");
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilDetachEvent() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
}
