package com.lodz.android.component.widget.adapter.recycler;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.adapter.bean.SimpleSwipeViewHolder;
import com.lodz.android.component.widget.adapter.bean.SwipeMenuBean;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.DensityUtils;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 简单实现的BaseSwipeRVAdapter
 * Created by zhouL on 2017/12/19.
 */

public abstract class SimpleSwipeRVAdapter<T, VH extends SimpleSwipeViewHolder> extends BaseSwipeRVAdapter<T, SimpleSwipeViewHolder>{

    private List<SwipeMenuBean> mLeftList;

    private List<SwipeMenuBean> mRightList;

    private OnMenuClickListener<T> mMenuClickListener;

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
    protected void configSwipeViewHolder(SimpleSwipeViewHolder holder) {
        super.configSwipeViewHolder(holder);
        getSwipeMenuLayout(holder).setSwipeEnable(ArrayUtils.getSize(mLeftList) > 0 || ArrayUtils.getSize(mRightList) > 0);//没有侧滑菜单禁止滑动
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void onBind(RecyclerView.ViewHolder holder, int position) {
        bindListData((VH) holder, mLeftList, mRightList, position);
        onBindContent((VH) holder, position);
    }

    /** 绑定内容数据 */
    public abstract void onBindContent(VH holder, int position);

    private void bindListData(VH holder, List<SwipeMenuBean> leftList, List<SwipeMenuBean> rightList, int position) {
        configLeftMenu(holder, leftList, position);
        configRightMenu(holder, rightList, position);
    }

    /** 配置左侧按钮UI */
    private void configLeftMenu(VH holder, List<SwipeMenuBean> leftList, int position) {
        holder.leftFirstLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(leftList) > 0){
            holder.leftFirstLayout.setVisibility(View.VISIBLE);
            configMenu(leftList, holder.leftFirstLayout, holder.leftFirstImg, holder.leftFirstGapView, holder.leftFirstNameTv, 0, position);
        }
        holder.leftSecondLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(leftList) > 1){
            holder.leftSecondLayout.setVisibility(View.VISIBLE);
            configMenu(leftList, holder.leftSecondLayout, holder.leftSecondImg, holder.leftSecondGapView, holder.leftSecondNameTv, 1, position);
        }
        holder.leftThirdLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(leftList) > 2){
            holder.leftThirdLayout.setVisibility(View.VISIBLE);
            configMenu(leftList, holder.leftThirdLayout, holder.leftThirdImg, holder.leftThirdGapView, holder.leftThirdNameTv, 2, position);
        }
    }


    /** 配置右侧按钮UI */
    private void configRightMenu(VH holder, List<SwipeMenuBean> rightList, int position) {
        holder.rightFirstLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(rightList) > 0){
            holder.rightFirstLayout.setVisibility(View.VISIBLE);
            configMenu(rightList, holder.rightFirstLayout, holder.rightFirstImg, holder.rightFirstGapView, holder.rightFirstNameTv, 0, position);
        }
        holder.rightSecondLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(rightList) > 1){
            holder.rightSecondLayout.setVisibility(View.VISIBLE);
            configMenu(rightList, holder.rightSecondLayout, holder.rightSecondImg, holder.rightSecondGapView, holder.rightSecondNameTv, 1, position);
        }
        holder.rightThirdLayout.setVisibility(View.GONE);
        if (ArrayUtils.getSize(rightList) > 2){
            holder.rightThirdLayout.setVisibility(View.VISIBLE);
            configMenu(rightList, holder.rightThirdLayout, holder.rightThirdImg, holder.rightThirdGapView, holder.rightThirdNameTv, 2, position);
        }
    }

    /** 配置按钮 */
    private void configMenu(List<SwipeMenuBean> list, ViewGroup layout, ImageView img, View gapView, TextView textView
            , int listPosition, int itemPosition){

        layout.setBackgroundColor(list.get(listPosition).bgColor > 0
                ? ContextCompat.getColor(getContext(), list.get(listPosition).bgColor) : Color.WHITE);
        // 设置图片
        img.setVisibility(list.get(listPosition).imgRes > 0 ? View.VISIBLE : View.GONE);
        img.setImageResource(list.get(listPosition).imgRes > 0 ? list.get(listPosition).imgRes : R.drawable.component_ic_launcher);
        ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), list.get(listPosition).imgSizeDp);
        layoutParams.width = DensityUtils.dp2px(getContext(), list.get(listPosition).imgSizeDp);
        // 有图片和文字时才显示间隔
        gapView.setVisibility(list.get(listPosition).imgRes > 0 && !TextUtils.isEmpty(list.get(listPosition).text) ? View.VISIBLE : View.GONE);
        // 设置文字
        textView.setVisibility(TextUtils.isEmpty(list.get(listPosition).text) ? View.GONE : View.VISIBLE);
        textView.setText(TextUtils.isEmpty(list.get(listPosition).text) ? "" : list.get(listPosition).text);
        textView.setTextColor(list.get(listPosition).textColor > 0
                ? ContextCompat.getColor(getContext(), list.get(listPosition).textColor) : Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, list.get(listPosition).textSizeSp);

        setMenuClick(layout, list.get(listPosition), itemPosition);
    }

    /** 设置菜单点击事件 */
    private void setMenuClick(ViewGroup layout, final SwipeMenuBean menuBean, final int position) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuClickListener != null){
                    T t = null;
                    try {
                        t = getItem(position);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mMenuClickListener.onMenuClick(t, menuBean, position);
                }
            }
        });
    }

    /** 设置菜单点击事件监听器 */
    public void setOnMenuClickListener(OnMenuClickListener<T> listener){
        this.mMenuClickListener = listener;
    }

    public interface OnMenuClickListener<T> {
        void onMenuClick(T item, SwipeMenuBean menu, int position);
    }

}
