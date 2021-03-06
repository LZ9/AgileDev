package com.lodz.android.component.mvp.base.activity;

import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.base.BaseViewContract;
import com.lodz.android.core.utils.ToastUtils;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;

import androidx.annotation.NonNull;

/**
 * MVP基类Activity（带基础状态控件）
 * Created by zhouL on 2017/7/10.
 */

public abstract class MvpBaseActivity<PC extends PresenterContract<VC>, VC extends BaseViewContract> extends BaseActivity implements BaseViewContract{

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
    public void goneTitleBar() {
        super.goneTitleBar();
    }

    @Override
    public void showTitleBar() {
        super.showTitleBar();
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

    @Override
    public final void showShortToast(int resId) {
        showShortToast(getString(resId));
    }

    @Override
    public final void showShortToast(String tips) {
        ToastUtils.showShort(getContext(), tips);
    }

    @Override
    public final void showLongToast(int resId) {
        showLongToast(getString(resId));
    }

    @Override
    public final void showLongToast(String tips) {
        ToastUtils.showLong(getContext(), tips);
    }
}
