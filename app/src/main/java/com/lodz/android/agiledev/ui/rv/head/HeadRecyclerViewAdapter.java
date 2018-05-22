package com.lodz.android.agiledev.ui.rv.head;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseHeaderFooterRVAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 带头部的适配器
 * Created by zhouL on 2017/4/6.
 */
public class HeadRecyclerViewAdapter extends BaseHeaderFooterRVAdapter<String, String, String> {

    public HeadRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(getLayoutView(parent, R.layout.item_head_layout));
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(getLayoutView(parent, R.layout.item_drag_recycler_view_layout));
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(ViewGroup parent) {
        return new FooterViewHolder(getLayoutView(parent, R.layout.item_footer_layout));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder){
            showHeaderItem((HeaderViewHolder) holder);
            return;
        }
        if (holder instanceof FooterViewHolder){
            showFooterItem((FooterViewHolder) holder);
            return;
        }
        showItem((ItemViewHolder) holder, position);
    }

    private void showHeaderItem(HeaderViewHolder holder) {
        String data = getHeaderData();
        holder.titleTv.setText(data);
    }

    private void showItem(ItemViewHolder holder, int position) {
        String str = getItem(position);
        holder.indexTv.setText(str);
    }

    private void showFooterItem(FooterViewHolder holder) {
        String data = getFooterData();
        holder.titleTv.setText(data);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{
        /** 标题 */
        @BindView(R.id.header_title)
        TextView titleTv;

        private HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        /** 序号 */
        @BindView(R.id.index_text)
        TextView indexTv;

        private ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{
        /** 标题 */
        @BindView(R.id.footer_title)
        TextView titleTv;

        private FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
