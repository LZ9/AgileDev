package com.lodz.android.agiledev.ui.rxjava.operate;

import android.text.TextUtils;

import com.lodz.android.agiledev.ui.rxjava.RxContract;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.core.log.PrintLog;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * 从磁盘、内存缓存中 获取缓存数据
 * Created by zhouL on 2018/3/7.
 */

public class RxGetCache implements RxContract{

    /** 有内存缓存 */
    private boolean hasMemoryCache = true;
    /** 有磁盘缓存 */
    private boolean hasDiskCache = true;

    @Override
    public void doCase() {
        // 内存/磁盘和网络只会取其中一个
//        memory().compose(RxUtils.<String>ioToMainObservable())
//                .flatMap(new Function<String, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(String s) throws Exception {
//                        return !TextUtils.isEmpty(s) ? Observable.just(s) : disk().compose(RxUtils.<String>ioToMainObservable());
//                    }
//                })
//                .flatMap(new Function<String, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(String s) throws Exception {
//                        return !TextUtils.isEmpty(s) ? Observable.just(s) : network().compose(RxUtils.<String>ioToMainObservable());
//                    }
//                })
//                .subscribe(new BaseObserver<String>() {
//                    @Override
//                    public void onBaseNext(String s) {
//                        PrintLog.d("testtag", "数据来源 ： " + s + " --- " + Thread.currentThread().getName());
//                    }
//
//                    @Override
//                    public void onBaseError(Throwable e) {
//
//                    }
//                });

        //先取内存/磁盘，然后跑一次网络
        Observable.zip(memory(), disk(), new BiFunction<String, String, Observable<String>>() {
            @Override
            public Observable<String> apply(String memory, String disk) throws Exception {
                String cache = "";
                if (!TextUtils.isEmpty(memory)) {
                    cache = memory;
                }else if (!TextUtils.isEmpty(disk)) {
                    cache = disk;
                }
                return Observable.concat(Observable.just(cache), network()).compose(RxUtils.<String>ioToMainObservable());
            }
        }).compose(RxUtils.<Observable<String>>ioToMainObservable())
                .flatMap(new Function<Observable<String>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Observable<String> observable) throws Exception {
                        return observable;
                    }
                })
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onBaseNext(String s) {
                        if (TextUtils.isEmpty(s)) {
                            return;
                        }
                        PrintLog.d("testtag", "数据来源 ： " + s + " --- " + Thread.currentThread().getName());
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
                if (emitter.isDisposed()){
                    return;
                }
                try {
                    Thread.sleep(100);
                    emitter.onNext(hasMemoryCache ? "有内存缓存 " + Thread.currentThread().getName() : "");
                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }

            }
        });
    }

    private Observable<String> disk(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }

                try {
                    Thread.sleep(200);
                    emitter.onNext(hasDiskCache ? "有磁盘缓存 " + Thread.currentThread().getName() : "");
                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    private Observable<String> network(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }
                try {
                    Thread.sleep(2000);
                    emitter.onNext("网络请求 " + Thread.currentThread().getName());
                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }
}
