package com.lodz.android.agiledev.ui.rv.swipe;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.bean.SimpleSwipeViewHolder;
import com.lodz.android.component.widget.adapter.bean.SwipeMenuBean;
import com.lodz.android.component.widget.adapter.recycler.SimpleSwipeRVAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 侧滑适配器
 * Created by zhouL on 2017/12/19.
 */

public class SwipeSimpleAdapter extends SimpleSwipeRVAdapter<String, SwipeSimpleAdapter.DataViewHolder>{

    public SwipeSimpleAdapter(Context context, List<SwipeMenuBean> leftList, List<SwipeMenuBean> rightList) {
        super(context, leftList, rightList);
    }

    @Override
    protected SimpleSwipeViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new DataViewHolder(getSwipeItemView(parent));
    }

    @Override
    protected int getContentLayout() {
        return R.layout.item_swipe_content_layout;
    }

    @Override
    protected void onBindContent(DataViewHolder holder, int position) {
        String str = getItem(position);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        showItem(holder, str);
    }

    private void showItem(SwipeSimpleAdapter.DataViewHolder holder, String str) {
        holder.contentTv.setText(str);
    }

    class DataViewHolder extends SimpleSwipeViewHolder{

        @BindView(R.id.content_txt)
        TextView contentTv;

        public DataViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView() {
            super.bindView();
            ButterKnife.bind(this, itemView);
        }
    }

}
