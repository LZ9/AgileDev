package com.lodz.android.component.widget.adapter.recycler;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.adapter.bean.SwipeMenuBean;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.DensityUtils;

import java.util.List;

/**
 * 简单实现的BaseSwipeRVAdapter
 * Created by zhouL on 2017/12/19.
 */

public abstract class SimpleSwipeRVAdapter<T> extends BaseSwipeRVAdapter<T>{

    private List<SwipeMenuBean> mLeftList;

    private List<SwipeMenuBean> mRightList;

    /**
     * 侧滑菜单适配器
     * @param context 上下文
     * @param leftList 左侧菜单列表
     * @param rightList 右侧菜单列表
     */
    public SimpleSwipeRVAdapter(Context context, List<SwipeMenuBean> leftList, List<SwipeMenuBean> rightList) {
        super(context);
        mLeftList = leftList;
        mRightList = rightList;
    }

    /**
     * 侧滑菜单适配器
     * @param context 上下文
     * @param list 菜单列表
     * @param isRight 列表是否右侧菜单
     */
    public SimpleSwipeRVAdapter(Context context, List<SwipeMenuBean> list, boolean isRight) {
        super(context);
        if (isRight){
            mRightList = list;
        }else {
            mLeftList = list;
        }
    }

    @Override
    protected int getRightLayout() {
        return R.layout.component_item_swipe_right_layout;
    }

    @Override
    protected int getLeftLayout() {
        return R.layout.component_item_swipe_left_layout;
    }

    @Override
    protected void configSwipeViewHolder(SwipeViewHolder holder) {
        super.configSwipeViewHolder(holder);
        getSwipeMenuLayout(holder).setSwipeEnable(ArrayUtils.getSize(mLeftList) > 0 || ArrayUtils.getSize(mRightList) > 0);//没有侧滑菜单禁止滑动
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        bindListData((SimpleSwipeRVAdapter.SimpleSwipeViewHolder) holder, mLeftList, mRightList);
        onBindContent(holder, position);
    }

    /** 绑定内容数据 */
    protected abstract void onBindContent(RecyclerView.ViewHolder holder, int position);

    private void bindListData(SimpleSwipeRVAdapter.SimpleSwipeViewHolder holder, List<SwipeMenuBean> leftList, List<SwipeMenuBean> rightList) {
        configLeftMenu(holder, leftList);
        configRightMenu(holder, rightList);
    }

    /** 配置左侧按钮UI */
    private void configLeftMenu(SimpleSwipeRVAdapter.SimpleSwipeViewHolder holder, List<SwipeMenuBean> leftList) {
        holder.leftFirstLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(leftList) > 0){
            holder.leftFirstLayout.setVisibility(View.VISIBLE);
            configMenu(leftList, holder.leftFirstLayout, holder.leftFirstImg, holder.leftFirstGapView, holder.leftFirstNameTv, 0);
        }
        holder.leftSecondLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(leftList) > 1){
            holder.leftSecondLayout.setVisibility(View.VISIBLE);
            configMenu(leftList, holder.leftSecondLayout, holder.leftSecondImg, holder.leftSecondGapView, holder.leftSecondNameTv, 1);
        }
        holder.leftThirdLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(leftList) > 2){
            holder.leftThirdLayout.setVisibility(View.VISIBLE);
            configMenu(leftList, holder.leftThirdLayout, holder.leftThirdImg, holder.leftThirdGapView, holder.leftThirdNameTv, 2);
        }
    }


    /** 配置右侧按钮UI */
    private void configRightMenu(SimpleSwipeRVAdapter.SimpleSwipeViewHolder holder, List<SwipeMenuBean> rightList) {
        holder.rightFirstLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(rightList) > 0){
            holder.rightFirstLayout.setVisibility(View.VISIBLE);
            configMenu(rightList, holder.rightFirstLayout, holder.rightFirstImg, holder.rightFirstGapView, holder.rightFirstNameTv, 0);
        }
        holder.rightSecondLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(rightList) > 1){
            holder.rightSecondLayout.setVisibility(View.VISIBLE);
            configMenu(rightList, holder.rightSecondLayout, holder.rightSecondImg, holder.rightSecondGapView, holder.rightSecondNameTv, 1);
        }
        holder.rightThirdLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(rightList) > 2){
            holder.rightThirdLayout.setVisibility(View.VISIBLE);
            configMenu(rightList, holder.rightThirdLayout, holder.rightThirdImg, holder.rightThirdGapView, holder.rightThirdNameTv, 2);
        }
    }

    /** 配置按钮 */
    private void configMenu(List<SwipeMenuBean> list, ViewGroup layout, ImageView img, View gapView, TextView textView, int position){
        layout.setBackgroundColor(list.get(position).bgColor > 0
                ? ContextCompat.getColor(getContext(), list.get(position).bgColor) : Color.WHITE);
        // 设置图片
        img.setVisibility(list.get(position).imgRes > 0 ? View.VISIBLE : View.GONE);
        img.setImageResource(list.get(position).imgRes > 0 ? list.get(position).imgRes : R.drawable.component_ic_launcher);
        ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), list.get(position).imgSizeDp);
        layoutParams.width = DensityUtils.dp2px(getContext(), list.get(position).imgSizeDp);
        // 有图片和文字时才显示间隔
        gapView.setVisibility(list.get(position).imgRes > 0 && !TextUtils.isEmpty(list.get(position).text) ? View.VISIBLE : View.GONE);
        // 设置文字
        textView.setVisibility(TextUtils.isEmpty(list.get(position).text) ? View.GONE : View.VISIBLE);
        textView.setText(TextUtils.isEmpty(list.get(position).text) ? "" : list.get(position).text);
        textView.setTextColor(list.get(position).textColor > 0
                ? ContextCompat.getColor(getContext(), list.get(position).textColor) : Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, list.get(position).textSizeSp);
    }

    protected class SimpleSwipeViewHolder extends SwipeViewHolder{

        private ViewGroup leftFirstLayout;
        private ImageView leftFirstImg;
        private View leftFirstGapView;
        private TextView leftFirstNameTv;

        private ViewGroup leftSecondLayout;
        private ImageView leftSecondImg;
        private View leftSecondGapView;
        private TextView leftSecondNameTv;

        private ViewGroup leftThirdLayout;
        private ImageView leftThirdImg;
        private View leftThirdGapView;
        private TextView leftThirdNameTv;

        private ViewGroup rightFirstLayout;
        private ImageView rightFirstImg;
        private View rightFirstGapView;
        private TextView rightFirstNameTv;

        private ViewGroup rightSecondLayout;
        private ImageView rightSecondImg;
        private View rightSecondGapView;
        private TextView rightSecondNameTv;

        private ViewGroup rightThirdLayout;
        private ImageView rightThirdImg;
        private View rightThirdGapView;
        private TextView rightThirdNameTv;

        protected SimpleSwipeViewHolder(View itemView) {
            super(itemView);
        }

        protected void bindView(){
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
}
