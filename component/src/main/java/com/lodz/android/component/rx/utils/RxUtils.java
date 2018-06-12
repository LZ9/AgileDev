package com.lodz.android.component.rx.utils;

import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.component.rx.exception.DataException;
import com.lodz.android.component.rx.exception.RxException;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.BitmapUtils;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx帮助类
 * Created by zhouL on 2017/3/1.
 */
public class RxUtils {

    /** 在异步线程发起，在主线程订阅 */
    public static <T> ObservableTransformer<T, T> ioToMainObservable() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /** 在异步线程发起，在主线程订阅 */
    public static <T> FlowableTransformer<T, T> ioToMainFlowable() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /** 在异步线程发起，在主线程订阅 */
    public static <T> MaybeTransformer<T, T> ioToMainMaybe() {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /** 在异步线程发起，在主线程订阅 */
    public static <T> SingleTransformer<T, T> ioToMainSingle() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 获取异常的提示语（配合订阅者使用）
     * @param e 异常
     * @param isNetwork 是否网络异常
     * @param defaultTips 默认提示语
     */
    public static String getExceptionTips(Throwable e, boolean isNetwork, String defaultTips){
        if (e == null){
            return defaultTips;
        }
        if (isNetwork && e instanceof RxException) {
            RxException exception = (RxException) e;
            return exception.getErrorMsg();
        }
        if (e instanceof DataException){
            DataException dataException = (DataException) e;
            if (dataException.getData() != null && !TextUtils.isEmpty(dataException.getData().getMsg())){
                return dataException.getData().getMsg();
            }
        }
        return defaultTips;
    }

    /**
     * 获取网络异常的提示语（配合订阅者使用）
     * @param e 异常
     * @param isNetwork 是否网络异常
     * @param defaultTips 默认提示语
     */
    public static String getNetworkExceptionTips(Throwable e, boolean isNetwork, String defaultTips){
        if (e == null){
            return defaultTips;
        }
        if (isNetwork && e instanceof RxException) {
            RxException exception = (RxException) e;
            return exception.getErrorMsg();
        }
        return defaultTips;
    }

    /**
     * 把图片路径转为base64
     * @param path 图片路径
     * @param widthPx 宽度（像素）
     * @param heightPx 高度（像素）
     */
    public static Observable<String> decodePathToBase64(final String path, final int widthPx, final int heightPx){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }
                if (TextUtils.isEmpty(path)) {
                    emitter.onNext("");
                    emitter.onComplete();
                    return;
                }
                try {
                    Bitmap bitmap = BitmapUtils.decodeBitmap(path, widthPx, heightPx);
                    if (emitter.isDisposed()){
                        return;
                    }
                    if (bitmap == null){
                        emitter.onNext("");
                        emitter.onComplete();
                        return;
                    }
                    String base64 = BitmapUtils.bitmapToBase64Default(bitmap);
                    if (emitter.isDisposed()){
                        return;
                    }
                    emitter.onNext(TextUtils.isEmpty(base64) ? "" : base64);
                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * 把图片路径转为base64
     * @param paths 图片路径
     * @param widthPx 宽度（像素）
     * @param heightPx 高度（像素）
     */
    public static Observable<List<String>> decodePathToBase64(final List<String> paths, final int widthPx, final int heightPx){
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }
                if (ArrayUtils.isEmpty(paths)){
                    emitter.onNext(new ArrayList<String>());
                    emitter.onComplete();
                    return;
                }

                try {
                    List<String> base64s = new ArrayList<>();
                    for (String path : paths) {
                        if (emitter.isDisposed()){
                            return;
                        }
                        Bitmap bitmap = BitmapUtils.decodeBitmap(path, widthPx, heightPx);
                        if (bitmap == null){
                            continue;
                        }
                        String base64 = BitmapUtils.bitmapToBase64Default(bitmap);
                        if (TextUtils.isEmpty(base64)) {
                            continue;
                        }
                        base64s.add(base64);
                    }
                    if (emitter.isDisposed()){
                        return;
                    }
                    emitter.onNext(base64s);
                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * 防抖点击（默认1秒内）
     * @param view 控件
     */
    public static Observable<View> viewClick(final View view){
        return viewClick(view, 1, TimeUnit.SECONDS);
    }

    /**
     * 防抖点击
     * @param view 控件
     * @param windowDuration 某段时间内
     * @param unit 时间单位
     */
    public static Observable<View> viewClick(final View view, long windowDuration, TimeUnit unit){
        return Observable.create(new ObservableOnSubscribe<View>() {
            @Override
            public void subscribe(final ObservableEmitter<View> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emitter.onNext(v);
                    }
                });
            }
        }).throttleFirst(windowDuration, unit);
    }

    /**
     * 文本变动
     * @param textView 控件
     */
    public static Observable<CharSequence> textChanges(final TextView textView){
        return Observable.create(new ObservableOnSubscribe<CharSequence>() {
            @Override
            public void subscribe(final ObservableEmitter<CharSequence> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }
                textView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
    }
}
