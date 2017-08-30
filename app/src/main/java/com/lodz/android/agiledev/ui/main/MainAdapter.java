package com.lodz.android.agiledev.ui.main;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.utils.ScreenUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页的列表
 * Created by zhouL on 2017/8/30.
 */

public class MainAdapter extends BaseRecyclerViewAdapter<String> {

    @ColorRes
    private static final int mColors[] = {R.color.color_00a0e9, R.color.color_ea8380, R.color.color_ea413c,
            R.color.color_303f9f, R.color.color_ff4081, R.color.color_d28928, R.color.color_464646};

    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(getLayoutView(parent, R.layout.item_main_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        String name = getItem(position);
        if (TextUtils.isEmpty(name)) {
            return;
        }
        showItem((MainViewHolder) holder, name);
    }

    private void showItem(MainViewHolder holder, String name) {
        setItemViewWidth(holder.itemView, ScreenUtils.getScreenWidth(getContext()));
        Random random = new Random();
        holder.nameTv.setText(name);
        holder.nameTv.setTextColor(ContextCompat.getColor(getContext(), mColors[(random.nextInt(100) + 1) % mColors.length]));
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name)
        TextView nameTv;

        private MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
