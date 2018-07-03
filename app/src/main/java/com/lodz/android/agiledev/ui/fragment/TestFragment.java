package com.lodz.android.agiledev.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.fragment.BaseRefreshFragment;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxObservableOnSubscribe;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.UiHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhouL on 2017/2/22.
 */

public class TestFragment extends BaseRefreshFragment{

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    private TextView mTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_layout;
    }



    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        mTextView = view.findViewById(R.id.first_text);
    }

    @Override
    protected void onDataRefresh() {
        UiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(System.currentTimeMillis()+"");
                setSwipeRefreshFinish();
                showStatusCompleted();
            }
        }, 2000);
    }

    @Override
    protected void initData(View view) {
        super.initData(view);
        showStatusLoading();
        BaseObserver<Integer> observer = new BaseObserver<Integer>() {
            @Override
            public void onBaseSubscribe(Disposable d) {

            }

            @Override
            public void onBaseNext(Integer integer) {
                PrintLog.d("testtag", integer + "");
                showStatusNoData();
            }

            @Override
            public void onBaseError(Throwable e) {

            }

            @Override
            public void onBaseComplete() {
                PrintLog.i("testtag", "onPgComplete");
            }
        };

        Observable.create(new RxObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                UiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        emitter.onNext(1);
                        emitter.onComplete();
                    }
                }, 3000);
            }
        }).subscribe(observer);
    }
}
