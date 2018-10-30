package com.lodz.android.agiledev.ui.rv.decoration;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;

import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 装饰器测试适配器
 * Created by zhouL on 2018/2/7.
 */

public class ItemDecorationTestAdapter extends BaseRecyclerViewAdapter<String>{

    public ItemDecorationTestAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_decoration_test_layout));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        String str = getItem(position);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        showItem((DataViewHolder) holder, str);
    }

    private void showItem(DataViewHolder holder, String str) {
        Random random = new Random();
        int num = random.nextInt(5) + 5;
//        setItemViewWidth(holder.itemView, DensityUtils.dp2px(getContext(), num * 9));
//        setItemViewHeight(holder.itemView, DensityUtils.dp2px(getContext(), num * 10));
        holder.strTv.setText(str);
    }

    class DataViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.str)
        TextView strTv;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
