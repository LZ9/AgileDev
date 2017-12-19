package com.lodz.android.agiledev.ui.rv.swipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseSwipeRVAdapter;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 侧滑适配器
 * Created by zhouL on 2017/12/18.
 */

public class SwipeAdapter extends BaseSwipeRVAdapter<String>{

    public SwipeAdapter(Context context) {
        super(context);
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
    protected int getRightLayout() {
        return R.layout.item_swipe_right_layout;
    }

    @Override
    protected int getLeftLayout() {
        return R.layout.item_swipe_left_layout;
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        String str = getItem(position);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        showItem((DataViewHolder) holder, str);
    }

    private void showItem(final DataViewHolder holder, final String str) {
        holder.contentTv.setText(str);
        holder.leftDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), "数据 " + str);
            }
        });

        holder.leftDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), "详情 " + str);
            }
        });

        holder.leftRankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), "排行 " + str);
            }
        });

        holder.rightAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), "新增 " + str);
                smoothCloseMenu(holder);
            }
        });

        holder.rightDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), "删除 " + str);
                smoothCloseMenu(holder);
            }
        });
    }

    class DataViewHolder extends SwipeViewHolder{

        @BindView(R.id.content_txt)
        TextView contentTv;

        @BindView(R.id.detail_btn)
        Button leftDetailBtn;
        @BindView(R.id.data_btn)
        Button leftDataBtn;
        @BindView(R.id.rank_btn)
        Button leftRankBtn;

        @BindView(R.id.add_btn)
        Button rightAddBtn;
        @BindView(R.id.delete_btn)
        Button rightDeleteBtn;

        private DataViewHolder(View itemView) {
            super(itemView);
        }

        private void bindView(){
            ButterKnife.bind(this, itemView);
        }
    }

}
