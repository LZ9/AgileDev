package com.lodz.android.agiledev.ui.index;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.NationBean;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 国籍适配器
 * Created by zhouL on 2018/6/1.
 */
public class NationAdapter extends BaseRecyclerViewAdapter<NationBean>{

    public NationAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_nation_layout));
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
        holder.nationNameTv.setText(bean.getTitle());
    }

    class DataViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.nation_name)
        TextView nationNameTv;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
