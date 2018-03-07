package com.lodz.android.agiledev.ui.rxjava.operate;

import com.lodz.android.agiledev.ui.rxjava.RxContract;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.core.log.PrintLog;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 从磁盘、内存缓存中 获取缓存数据
 * Created by zhouL on 2018/3/7.
 */

public class RxGetCache implements RxContract{

    /** 有内存缓存 */
    private boolean hasMemoryCache = false;
    /** 有磁盘缓存 */
    private boolean hasDiskCache = true;

    @Override
    public void doCase() {
        Observable.concat(memory(), disk(), Observable.just("网络请求数据"))
                .compose(RxUtils.<String>ioToMainObservable())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onBaseNext(String s) {
                        PrintLog.d("testtag", "数据来源 ： " + s);
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }
                });
    }

    private Observable<String> memory(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (hasMemoryCache){
                    emitter.onNext("有内存缓存");
                }else {
                    emitter.onComplete();
                }
            }
        });
    }

    private Observable<String> disk(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (hasDiskCache){
                    emitter.onNext("有磁盘缓存");
                }else {
                    emitter.onComplete();
                }
            }
        });
    }
}
