package com.lodz.android.component.mvp.base.fragment;

import com.lodz.android.component.base.fragment.LazyFragment;
import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.abs.ViewContract;
import com.lodz.android.core.utils.ToastUtils;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;

import androidx.annotation.NonNull;

/**
 * MVP懒加载的fragment
 * Created by zhouL on 2017/7/29.
 */

public abstract class MvpLazyFragment<PC extends PresenterContract<VC>, VC extends ViewContract> extends LazyFragment implements ViewContract {

    /** Presenter接口 */
    private PC mPresenterContract;

    @Override
    @SuppressWarnings("unchecked")
    protected void startCreate() {
        super.startCreate();
        mPresenterContract = createMainPresenter();
        if (mPresenterContract != null){
            mPresenterContract.attach(getContext(), (VC) this);
        }
    }

    protected abstract PC createMainPresenter();

    @NonNull
    protected PC getPresenterContract(){
        return mPresenterContract;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenterContract != null){
            mPresenterContract.detach();
        }
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event) {
        throw new IllegalArgumentException("you bind fragment but call activity event");
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event) {
        return bindUntilEvent(event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilDetachEvent() {
        return bindUntilEvent(FragmentEvent.DESTROY_VIEW);
    }

    @Override
    public final void finish() {
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
