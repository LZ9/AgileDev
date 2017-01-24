package com.snxun.component.widget.adapter.binder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerBinder基类
 * Created by zhouL on 2017/1/17.
 */
public abstract class RecyclerBinder<T> {

    private Context mContext;

    private int mBinderType;

    public RecyclerBinder(Context context, int binderType) {
        this.mContext = context;
        this.mBinderType = binderType;
    }

    public Context getContext(){
        return mContext;
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public int getViewType(){
        return mBinderType;
    }

    public abstract T getData(int position);

    public abstract int getItemCount();

    /** 在onCreateViewHolder方法中根据layoutId获取View */
    protected View getLayoutView(ViewGroup parent, @LayoutRes int layoutId){
        return LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
    }

}
