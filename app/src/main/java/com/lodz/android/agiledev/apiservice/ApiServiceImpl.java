package com.lodz.android.agiledev.apiservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lodz.android.agiledev.bean.SpotBean;
import com.lodz.android.agiledev.bean.base.ResponseBean;
import com.lodz.android.core.utils.ReflectUtils;
import com.lodz.android.core.utils.UiHandler;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.RequestBody;

/**
 * 接口
 * Created by zhouL on 2018/2/6.
 */

public class ApiServiceImpl implements ApiService{

    public static ApiServiceImpl get(){
        return new ApiServiceImpl();
    }

    private ApiServiceImpl() {
    }

    @Override
    public Observable<ResponseBean<SpotBean>> getSpot(String id, String output) {
        return Observable.create(new ObservableOnSubscribe<ResponseBean<SpotBean>>() {
            @Override
            public void subscribe(final ObservableEmitter<ResponseBean<SpotBean>> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }
                try {
                    UiHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ResponseBean<SpotBean> responseBean = new ResponseBean<>();
                            responseBean.code = ResponseBean.SUCCESS;
                            responseBean.msg = "success";
                            responseBean.data = new SpotBean();
                            responseBean.data.spotName = "鼓浪屿";
                            responseBean.data.star = "★★★★★";
                            emitter.onNext(responseBean);
                            emitter.onComplete();
                        }
                    }, 2000);
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }

            }
        });
    }

    @Override
    public Observable<ResponseBean<SpotBean>> postSpot(String id, String output) {
        return Observable.create(new ObservableOnSubscribe<ResponseBean<SpotBean>>() {
            @Override
            public void subscribe(final ObservableEmitter<ResponseBean<SpotBean>> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }
                try {
                    UiHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ResponseBean<SpotBean> responseBean = new ResponseBean<>();
                            responseBean.code = ResponseBean.SUCCESS;
                            responseBean.msg = "success";
                            responseBean.data = new SpotBean();
                            responseBean.data.spotName = "环岛路";
                            responseBean.data.star = "★★★★";
                            emitter.onNext(responseBean);
                            emitter.onComplete();
                        }
                    }, 2000);
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<ResponseBean<List<SpotBean>>> querySpot(final RequestBody requestBody) {
        return Observable.create(new ObservableOnSubscribe<ResponseBean<List<SpotBean>>>() {
            @Override
            public void subscribe(final ObservableEmitter<ResponseBean<List<SpotBean>>> emitter) throws Exception {
                if (emitter.isDisposed()){
                    return;
                }
                try {
                    UiHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String json = "{\"code\":200,\"msg\":\"success\",\"data\":[]}";
                            ResponseBean<List<SpotBean>> responseBean = JSON.parseObject(json, new TypeReference<ResponseBean<List<SpotBean>>>(){});
                            responseBean.data = new ArrayList<>();
                            SpotBean spotBean = new SpotBean();
                            spotBean.spotName = getJsonByRequestBody(requestBody);
                            spotBean.star = "★★★";
                            responseBean.data.add(spotBean);
                            emitter.onNext(responseBean);
                            emitter.onComplete();
                        }
                    }, 2000);
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    /** 将RequestBody的请求内容取出 */
    private String getJsonByRequestBody(RequestBody requestBody) {
        try {
            Class<?> c =  requestBody.getClass();
            List<String> list = ReflectUtils.getFieldName(c);
            for (String name : list) {
                Object o = ReflectUtils.getFieldValue(c, requestBody, name);
                if (o instanceof byte[]){
                    byte[] bytes = (byte[]) o;
                    return new String(bytes);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

//    /** 人员核查历史明细 */
//    @Override
//    public Observable<ResponseBean<PersonDetailBean>> getPersonHistoryDetail(@Body final RequestBody requestBody) {
//        return Observable.create(new ObservableOnSubscribe<ResponseBean<PersonDetailBean>>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<ResponseBean<PersonDetailBean>> emitter) throws Exception {
//                if (emitter.isDisposed()){
//                    return;
//                }
//                try {
//                    String reuslt = StRequestHelper.post(App.get(), StApiConstant.GET_PERSON_RECORD_APP, getJsonByRequestBody(requestBody));
//                    ResponseBean<PersonDetailBean> responseBean = JSON.parseObject(reuslt, new TypeReference<ResponseBean<PersonDetailBean>>(){});
//                    if (emitter.isDisposed()){
//                        return;
//                    }
//                    if (responseBean == null || !responseBean.isSuccess()){
//                        DataException dataException = new DataException("data error");
//                        if (responseBean != null){
//                            dataException.setData(responseBean);
//                        }
//                        emitter.onError(dataException);
//                        return;
//                    }
//                    emitter.onNext(responseBean);
//                    emitter.onComplete();
//                }catch (Exception e){
//                    e.printStackTrace();
//                    emitter.onError(e);
//                }
//            }
//        });
//    }
}
