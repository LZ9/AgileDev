package com.lodz.android.component.widget.adapter.binder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 默认的RecyclerBinder
 * Created by zhouL on 2018/3/9.
 */

public class DefaultRecyclerBinder extends RecyclerBinder<String>{

    public DefaultRecyclerBinder(Context context, int binderType) {
        super(context, binderType);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new DefaultViewHolder(new LinearLayout(getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public String getData(int position) {
        return "";
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class DefaultViewHolder extends RecyclerView.ViewHolder{
        private DefaultViewHolder(View itemView) {
            super(itemView);
        }
    }
}
