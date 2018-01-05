package com.lodz.android.agiledev.ui.rv.swipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.bean.SwipeMenuBean;
import com.lodz.android.component.widget.adapter.recycler.SimpleSwipeRVAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 侧滑适配器
 * Created by zhouL on 2017/12/19.
 */

public class SwipeSimpleAdapter extends SimpleSwipeRVAdapter<String>{

    public SwipeSimpleAdapter(Context context, List<SwipeMenuBean> leftList, List<SwipeMenuBean> rightList) {
        super(context, leftList, rightList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DataViewHolder holder = new DataViewHolder(getSwipeItemView(parent));
        configSwipeViewHolder(holder);
        holder.bindView();
        return holder;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.item_swipe_content_layout;
    }

    @Override
    protected void onBindContent(RecyclerView.ViewHolder holder, int position) {
        String str = getItem(position);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        showItem((SwipeSimpleAdapter.DataViewHolder) holder, str);
    }

    private void showItem(SwipeSimpleAdapter.DataViewHolder holder, String str) {
        holder.contentTv.setText(str);
    }

    class DataViewHolder extends SimpleSwipeViewHolder{

        @BindView(R.id.content_txt)
        TextView contentTv;

        private DataViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView() {
            super.bindView();
            ButterKnife.bind(this, itemView);
        }
    }

}
