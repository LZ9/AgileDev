package com.lodz.android.agiledev.ui.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseSimpleLoadMoreRecyclerViewAdapter;

/**
 * 刷新测试adapter
 * Created by zhouL on 2017/2/28.
 */

public class RefreshAdapter extends BaseSimpleLoadMoreRecyclerViewAdapter<String> {

    public RefreshAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_refresh_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        String str = getItem(position);
        if (TextUtils.isEmpty(str)){
            return;
        }
        showItem((DataViewHolder) holder, str);
    }

    private void showItem(DataViewHolder holder, String str) {
        holder.dataTextView.setText(str);
    }

    private class DataViewHolder extends RecyclerView.ViewHolder{
        private TextView dataTextView;
        public DataViewHolder(View itemView) {
            super(itemView);
            dataTextView = (TextView) itemView.findViewById(R.id.data_text);
        }
    }
}
