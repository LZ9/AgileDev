package com.lodz.android.agiledev.ui.design.coordinator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简单数据适配器
 * Created by zhouL on 2018/3/23.
 */

public class SimpleDataAdapter extends BaseRecyclerViewAdapter<String>{

    public SimpleDataAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_simple_data_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        String data = getItem(position);
        if (TextUtils.isEmpty(data)) {
            return;
        }
        showItem((DataViewHolder) holder, data);
    }

    private void showItem(DataViewHolder holder, String data) {
        holder.dataTv.setText(data);
    }

    class DataViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.data)
        TextView dataTv;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
