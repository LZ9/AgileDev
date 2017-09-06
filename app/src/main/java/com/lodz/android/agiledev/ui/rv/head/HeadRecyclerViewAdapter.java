package com.lodz.android.agiledev.ui.rv.head;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseHeadRecyclerViewAdapter;

/**
 * 带头部的适配器
 * Created by zhouL on 2017/4/6.
 */
public class HeadRecyclerViewAdapter extends BaseHeadRecyclerViewAdapter<String, String>{

    public HeadRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder getHeadViewHolder(ViewGroup parent) {
        return new HeadViewHolder(getLayoutView(parent, R.layout.item_head_layout));
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent) {
        return new ItemViewHolder(getLayoutView(parent, R.layout.item_drag_recycler_view_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder){
            showHeadItem((HeadViewHolder) holder);
            return;
        }
        showItem((ItemViewHolder) holder, position);
    }

    private void showHeadItem(HeadViewHolder holder) {
        String headStr = getHeadData();
        holder.headTextView.setText(headStr);
    }

    private void showItem(ItemViewHolder holder, int position) {
        String str = getItemUnderHead(position);
        holder.indexTextView.setText(str);
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder{
        /** 标题 */
        private TextView headTextView;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headTextView = (TextView) itemView.findViewById(R.id.head_title);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        /** 序号 */
        private TextView indexTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            indexTextView = (TextView) itemView.findViewById(R.id.index_text);
        }
    }

}
