package com.lodz.android.agiledev.ui.rv.drag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 拖拽适配器
 * Created by zhouL on 2017/3/6.
 */
public class DragAdapter extends BaseRecyclerViewAdapter<String>{

    public DragAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DragViewHolder(getLayoutView(parent, R.layout.item_drag_recycler_view_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        String text = getItem(position);
        if (TextUtils.isEmpty(text)){
            return;
        }
        showItem((DragViewHolder) holder, text);
    }

    private void showItem(DragViewHolder holder, final String text) {
        setItemViewWidth(holder.itemView, ScreenUtils.getScreenWidth(getContext()) / 4);
        holder.indexTextView.setText(text);
    }

    class DragViewHolder extends RecyclerView.ViewHolder{
        /** 序号 */
        @BindView(R.id.index_text)
        TextView indexTextView;

        private DragViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
