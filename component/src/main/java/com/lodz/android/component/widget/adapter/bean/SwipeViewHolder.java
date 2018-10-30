package com.lodz.android.component.widget.adapter.bean;

import android.view.View;
import android.view.ViewGroup;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.adapter.swipe.SwipeMenuLayout;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 侧滑ViewHolder
 * Created by zhouL on 2018/2/9.
 */

public class SwipeViewHolder extends RecyclerView.ViewHolder{
    /** 侧滑布局 */
    public SwipeMenuLayout swipeMenuLayout;
    /** 内容布局 */
    public ViewGroup contentLayout;
    /** 右侧布局 */
    public ViewGroup rightLayout;
    /** 左侧布局 */
    public ViewGroup leftLayout;

    public SwipeViewHolder(View itemView) {
        super(itemView);
        swipeMenuLayout = itemView.findViewById(R.id.swipe_menu_layout);
        contentLayout = itemView.findViewById(R.id.content_view);
        rightLayout = itemView.findViewById(R.id.right_view);
        leftLayout = itemView.findViewById(R.id.left_view);
    }

    public void bindView(){
    }
}
