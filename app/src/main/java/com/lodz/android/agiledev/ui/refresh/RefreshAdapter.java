package com.lodz.android.agiledev.ui.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.SimpleLoadMoreRVAdapter;
import com.lodz.android.core.utils.ScreenUtils;

/**
 * 刷新测试adapter
 * Created by zhouL on 2017/2/28.
 */

public class RefreshAdapter extends SimpleLoadMoreRVAdapter<String> {

    private Listener mListener;

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
        showItem((DataViewHolder) holder, str, position);
    }

    private void showItem(DataViewHolder holder, String str, final int position) {
//        if (position % 2 == 0){
//            setItemViewHeight(holder.itemView, DensityUtils.dp2px(getContext(), 200));
//        }else {
//            setItemViewHeight(holder.itemView, DensityUtils.dp2px(getContext(), 400));
//        }
        setItemViewWidth(holder.itemView, ScreenUtils.getScreenWidth(getContext()));
        holder.dataTextView.setText(str);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickDelete(position);
                }
            }
        });
    }

    private class DataViewHolder extends RecyclerView.ViewHolder{
        private TextView dataTextView;
        private Button deleteBtn;
        public DataViewHolder(View itemView) {
            super(itemView);
            dataTextView = (TextView) itemView.findViewById(R.id.data_text);
            deleteBtn = (Button) itemView.findViewById(R.id.delete_btn);
        }
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onClickDelete(int position);
    }
}
