package com.lodz.android.component.widget.adapter.bean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.component.R;

/**
 * 简单实现的侧滑ViewHolder
 * Created by zhouL on 2018/2/9.
 */

public class SimpleSwipeViewHolder extends SwipeViewHolder{

    public ViewGroup leftFirstLayout;
    public ImageView leftFirstImg;
    public View leftFirstGapView;
    public TextView leftFirstNameTv;

    public ViewGroup leftSecondLayout;
    public ImageView leftSecondImg;
    public View leftSecondGapView;
    public TextView leftSecondNameTv;

    public ViewGroup leftThirdLayout;
    public ImageView leftThirdImg;
    public View leftThirdGapView;
    public TextView leftThirdNameTv;

    public ViewGroup rightFirstLayout;
    public ImageView rightFirstImg;
    public View rightFirstGapView;
    public TextView rightFirstNameTv;

    public ViewGroup rightSecondLayout;
    public ImageView rightSecondImg;
    public View rightSecondGapView;
    public TextView rightSecondNameTv;

    public ViewGroup rightThirdLayout;
    public ImageView rightThirdImg;
    public View rightThirdGapView;
    public TextView rightThirdNameTv;

    public SimpleSwipeViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView() {
        super.bindView();
        // 左侧菜单按钮
        leftFirstLayout = itemView.findViewById(R.id.left_first_layout);
        leftFirstImg = itemView.findViewById(R.id.left_first_img);
        leftFirstGapView = itemView.findViewById(R.id.left_first_gap);
        leftFirstNameTv = itemView.findViewById(R.id.left_first_name);

        leftSecondLayout = itemView.findViewById(R.id.left_second_layout);
        leftSecondImg = itemView.findViewById(R.id.left_second_img);
        leftSecondGapView = itemView.findViewById(R.id.left_second_gap);
        leftSecondNameTv = itemView.findViewById(R.id.left_second_name);

        leftThirdLayout = itemView.findViewById(R.id.left_third_layout);
        leftThirdImg = itemView.findViewById(R.id.left_third_img);
        leftThirdGapView = itemView.findViewById(R.id.left_third_gap);
        leftThirdNameTv = itemView.findViewById(R.id.left_third_name);

        // 右侧菜单按钮
        rightFirstLayout = itemView.findViewById(R.id.right_first_layout);
        rightFirstImg = itemView.findViewById(R.id.right_first_img);
        rightFirstGapView = itemView.findViewById(R.id.right_first_gap);
        rightFirstNameTv = itemView.findViewById(R.id.right_first_name);

        rightSecondLayout = itemView.findViewById(R.id.right_second_layout);
        rightSecondImg = itemView.findViewById(R.id.right_second_img);
        rightSecondGapView = itemView.findViewById(R.id.right_second_gap);
        rightSecondNameTv = itemView.findViewById(R.id.right_second_name);

        rightThirdLayout = itemView.findViewById(R.id.right_third_layout);
        rightThirdImg = itemView.findViewById(R.id.right_third_img);
        rightThirdGapView = itemView.findViewById(R.id.right_third_gap);
        rightThirdNameTv = itemView.findViewById(R.id.right_third_name);
    }
}
