package com.lodz.android.agiledev.bean.base;

import com.alibaba.fastjson.JSON;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 基础请求类
 * Created by zhouL on 2018/2/6.
 */

public class BaseRequestBean {

    /** 创建一个请求对象 */
    public RequestBody createRequestBody(){
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(this));
    }

    /** 创建默认请求对象 */
    public static RequestBody createDefaultRequestBody(){
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{}");
    }

    /**
     * 创建List请求对象
     * @param list 数组
     */
    public static RequestBody createListRequestBody(List list){
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(list));
    }
}
