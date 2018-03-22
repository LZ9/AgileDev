package com.lodz.android.component.mvp.base.activity;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.abs.ViewContract;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * MVP基础Activity
 * Created by zhouL on 2017/7/7.
 */

public abstract class MvpAbsActivity<PC extends PresenterContract<VC>, VC extends ViewContract> extends AbsActivity implements ViewContract {

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
    public final <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event) {
        return bindUntilEvent(event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event) {
        throw new IllegalArgumentException("you bind activity but call fragment event");
    }
}
