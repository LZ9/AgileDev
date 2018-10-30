package com.lodz.android.agiledev.ui.share;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.ShareAnimBean;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 共享动画列表适配器
 * Created by zhouL on 2018/5/24.
 */
public class ShareAnimAdapter extends BaseRecyclerViewAdapter<ShareAnimBean>{

    public ShareAnimAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_share_anim_layout));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        ShareAnimBean bean = getItem(position);
        if (bean == null){
            return;
        }
        showItem((DataViewHolder) holder, bean);
    }

    private void showItem(DataViewHolder holder, ShareAnimBean bean) {
        holder.img.setImageResource(bean.imgRes);
        holder.titleTv.setText(bean.title);
    }

    class DataViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.title)
        TextView titleTv;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
