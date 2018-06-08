package com.lodz.android.agiledev.ui.rv.snap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.NationBean;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.imageloader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Snap数据适配器
 * Created by zhouL on 2018/6/6.
 */
public class SnapAdapter extends BaseRecyclerViewAdapter<NationBean>{

    public SnapAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_snap_layout));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        NationBean bean = getItem(position);
        if (bean == null){
            return;
        }
        showItem((DataViewHolder) holder, bean);
    }

    private void showItem(DataViewHolder holder, NationBean bean) {
        showImg(bean.imgUrl, holder.nationImg);
        holder.titleTv.setText(bean.getTitle());
    }

    private void showImg(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        ImageLoader.create(getContext())
                .load(url)
                .useRoundCorner()
                .into(imageView);
    }

    class DataViewHolder extends RecyclerView.ViewHolder{

        /** 国旗图片 */
        @BindView(R.id.nation_img)
        ImageView nationImg;
        /** 国家名称 */
        @BindView(R.id.title)
        TextView titleTv;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
