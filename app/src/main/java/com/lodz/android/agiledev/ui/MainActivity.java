package com.lodz.android.agiledev.ui;

import android.os.Bundle;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lodz.android.agiledev.AgileDevApplication;
import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.UiHandler;
import com.lodz.android.imageloader.ImageLoader;
import com.lodz.android.imageloader.utils.UriUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        final SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.drawee_view);
        final SimpleDraweeView draweeView2 = (SimpleDraweeView) findViewById(R.id.drawee_view_2);
//        String url = "http://ww1.sinaimg.cn/large/610dc034jw1f8kmud15q1j20u011hdjg.jpg";// 纵向长方形
//        String url = "http://ww2.sinaimg.cn/large/610dc034jw1f978bh1cerj20u00u0767.jpg";// 矩形
        String url = "http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg";// 横向长方形
        ImageLoader.create()
                .load(UriUtils.parseUrl(url))
                .setImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .wrapImageWidth(DensityUtils.dp2px(getContext(), 200))
//                .wrapImage()
                .into(draweeView);

        ImageLoader.create()
                .load(UriUtils.parseUrl(url))
                .setImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .wrapImageHeight(DensityUtils.dp2px(getContext(), 200))
                .into(draweeView2);


        ProgressObserver<Integer> observer = new ProgressObserver<Integer>() {
            @Override
            public void onPgSubscribe(Disposable d) {

            }

            @Override
            public void onPgNext(Integer integer) {
                PrintLog.d("testtag", integer + "");
            }

            @Override
            public void onPgError(Throwable e, boolean isNetwork) {

            }

            @Override
            public void onPgComplete() {
                PrintLog.i("testtag", "onPgComplete");
            }
        }.create(this);


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 3; i++) {
                    final int finalI = i;
                    UiHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            emitter.onNext(finalI);
//                            if (finalI == 2){
//                                emitter.onError(new NullPointerException("test"));
//                            }
                        }
                    }, 2000);

                }
            }
        }).subscribe(observer);
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }

    @Override
    protected boolean onPressBack() {
        AgileDevApplication.get().exit();
        return true;
    }
}
