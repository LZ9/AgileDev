package com.lodz.android.agiledev.ui.rv.refresh;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 刷新测试adapter
 * Created by zhouL on 2017/2/28.
 */

public class RefreshAdapter extends SimpleLoadMoreRVAdapter<String> {

    private Listener mListener;

    public RefreshAdapter(Context context) {
        super(context);
        setIndeterminateDrawable(R.drawable.anims_custom_progress);//自定义加载动画资源
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
        setItemViewWidth(holder.itemView, ScreenUtils.getScreenWidth(getContext()) / 3);
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

    class DataViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.data_text)
        TextView dataTextView;

        @BindView(R.id.delete_btn)
        Button deleteBtn;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onClickDelete(int position);
    }
}
