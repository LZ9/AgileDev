package com.lodz.android.component.widget.adapter.recycler;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.core.utils.ScreenUtils;

/**
 * 简单实现的BaseLoadMoreRecyclerViewAdapter
 * Created by zhouL on 2017/2/28.
 */
public abstract class SimpleLoadMoreRVAdapter<T> extends BaseLoadMoreRVAdapter<T> {

    /** 完成加载提示语 */
    private String mFinishText = getContext().getString(R.string.component_load_finish_tips);
    /** 完成加载提示语大小 */
    private int mFinishTextSizeSp = 12;
    /** 完成加载提示语颜色 */
    @ColorRes
    private int mFinishTextColor = android.R.color.black;
    /** 完成加载背景色颜色 */
    @ColorRes
    private int mFinishBackgroundColor = android.R.color.transparent;

    /** 正在加载提示语 */
    private String mLoadingMoreText = getContext().getString(R.string.component_loading_more_tips);
    /** 正在加载提示语大小 */
    private int mLoadingMoreTextSizeSp = 12;
    /** 正在加载提示语颜色 */
    @ColorRes
    private int mLoadingMoreTextColor = android.R.color.black;
    /** 正在加载背景色颜色 */
    @ColorRes
    private int mLoadingMoreBackgroundColor = android.R.color.transparent;
    /** 正在加载动画资源资源 */
    @DrawableRes
    private int indeterminateDrawable = 0;

    /** 加载失败提示语 */
    private String mLoadFailText = getContext().getString(R.string.component_load_fail_tips);
    /** 加载失败提示语大小 */
    private int mLoadFailTextSizeSp = 12;
    /** 加载失败提示语颜色 */
    @ColorRes
    private int mLoadFailTextColor = android.R.color.holo_red_light;
    /** 加载失败背景色颜色 */
    @ColorRes
    private int mLoadFailBackgroundColor = android.R.color.transparent;

    public SimpleLoadMoreRVAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLoadFinishLayoutId() {
        return R.layout.component_item_load_finish_layout;
    }

    @Override
    protected int getLoadingMoreLayoutId() {
        return R.layout.component_item_loading_more_layout;
    }

    @Override
    protected int getLoadFailLayoutId() {
        return R.layout.component_item_load_fail_layout;
    }

    @Override
    public void showLoadFinish(RecyclerView.ViewHolder holder) {
        if (holder == null) {
            return;
        }
        if (isGridLayoutManager){
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.width = ScreenUtils.getScreenWidth(getContext());
        }

        holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), mFinishBackgroundColor));

        TextView loadFinishTextView = holder.itemView.findViewById(R.id.load_finish_text_view);
        loadFinishTextView.setText(mFinishText);
        loadFinishTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mFinishTextSizeSp);
        loadFinishTextView.setTextColor(ContextCompat.getColor(getContext(), mFinishTextColor));
    }

    @Override
    public void showLoadFail(RecyclerView.ViewHolder holder) {
        if (holder == null) {
            return;
        }
        if (isGridLayoutManager){
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.width = ScreenUtils.getScreenWidth(getContext());
        }

        holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), mLoadFailBackgroundColor));

        TextView textView = holder.itemView.findViewById(R.id.load_fail_text_view);
        textView.setText(mLoadFailText);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLoadFailTextSizeSp);
        textView.setTextColor(ContextCompat.getColor(getContext(), mLoadFailTextColor));
    }

    @Override
    public void showLoadingMore(RecyclerView.ViewHolder holder) {
        if (holder == null) {
            return;
        }
        if (isGridLayoutManager){
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.width = ScreenUtils.getScreenWidth(getContext());
        }

        holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), mLoadingMoreBackgroundColor));

        TextView loadFinishTextView = holder.itemView.findViewById(R.id.loading_more_text_view);
        loadFinishTextView.setText(mLoadingMoreText);
        loadFinishTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLoadingMoreTextSizeSp);
        loadFinishTextView.setTextColor(ContextCompat.getColor(getContext(), mLoadingMoreTextColor));

        ProgressBar progressBar = holder.itemView.findViewById(R.id.loading_progressbar);
        progressBar.setIndeterminate(true);
        if (indeterminateDrawable != 0){
            progressBar.setIndeterminateDrawable(ContextCompat.getDrawable(getContext(), indeterminateDrawable));
            indeterminateDrawable = 0;//设置过以后就清空数据，避免展示异常
        }
    }

    /**
     * 设置完成加载时的提示语
     * @param text 提示语
     */
    public void setFinishText(String text) {
        this.mFinishText = text;
    }

    /**
     * 设置完成加载提示语大小
     * @param sizeSp 文字大小，单位SP
     */
    public void setFinishTextSizeSp(int sizeSp) {
        this.mFinishTextSizeSp = sizeSp;
    }

    /**
     * 设置完成加载提示语颜色
     * @param textColor 文字颜色
     */
    public void setFinishTextColor(@ColorRes int textColor) {
        this.mFinishTextColor = textColor;
    }

    /**
     * 设置加载完成背景色
     * @param backgroundColor 背景色
     */
    public void setFinishBackgroundColor(@ColorRes int backgroundColor) {
        this.mFinishBackgroundColor = backgroundColor;
    }

    /**
     * 设置正在加载提示语
     * @param text 提示语
     */
    public void setLoadingMoreText(String text) {
        this.mLoadingMoreText = text;
    }

    /**
     * 设置正在加载文字大小
     * @param sizeSp 文字大小
     */
    public void setLoadingMoreTextSizeSp(int sizeSp) {
        this.mLoadingMoreTextSizeSp = sizeSp;
    }

    /**
     * 设置正在加载文字颜色
     * @param textColor 文字颜色
     */
    public void setLoadingMoreTextColor(@ColorRes int textColor) {
        this.mLoadingMoreTextColor = textColor;
    }

    /**
     * 设置正在加载背景色
     * @param backgroundColor 背景色
     */
    public void setLoadingMoreBackgroundColor(@ColorRes int backgroundColor) {
        this.mLoadingMoreBackgroundColor = backgroundColor;
    }

    /**
     * 设置正在加载动画资源
     * @param resId 资源id
     */
    public void setIndeterminateDrawable(@DrawableRes int resId){
        this.indeterminateDrawable = resId;
    }

    /**
     * 设置加载失败提示语
     * @param text 加载失败提示语
     */
    public void setLoadFailText(String text) {
        this.mLoadFailText = text;
    }

    /**
     * 设置加载失败提示语大小
     * @param sizeSp 文字大小
     */
    public void setLoadFailTextSizeSp(int sizeSp) {
        this.mLoadFailTextSizeSp = sizeSp;
    }

    /**
     * 设置加载失败提示语文字颜色
     * @param textColor 文字颜色
     */
    public void setLoadFailTextColor(@ColorRes int textColor) {
        this.mLoadFailTextColor = textColor;
    }

    /**
     * 设置加载失败提示语背景大小
     * @param backgroundColor 背景色
     */
    public void setLoadFailBackgroundColor(@ColorRes int backgroundColor) {
        this.mLoadFailBackgroundColor = backgroundColor;
    }

}
