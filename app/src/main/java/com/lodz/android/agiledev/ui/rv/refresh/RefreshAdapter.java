package com.lodz.android.agiledev.ui.rv.refresh;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.rv.drag.LayoutManagerPopupWindow;
import com.lodz.android.component.widget.adapter.recycler.SimpleLoadMoreRVAdapter;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.ScreenUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 刷新测试adapter
 * Created by zhouL on 2017/2/28.
 */

public class RefreshAdapter extends SimpleLoadMoreRVAdapter<String> {

    @ColorRes
    private static final int mColors[] = {R.color.color_00a0e9, R.color.color_ea8380, R.color.color_ea413c,
            R.color.color_303f9f, R.color.color_ff4081, R.color.color_d28928, R.color.color_464646};

    private Listener mListener;

    /** 当前布局 */
    @LayoutManagerPopupWindow.LayoutManagerType
    private int mManagerType = LayoutManagerPopupWindow.TYPE_LINEAR;

    public RefreshAdapter(Context context) {
        super(context);
        setIndeterminateDrawable(R.drawable.anims_custom_progress);//自定义加载动画资源
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_refresh_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        String str = getItem(position);
        if (TextUtils.isEmpty(str)){
            return;
        }
        showItem((DataViewHolder) holder, str, position);
    }

    private void showItem(DataViewHolder holder, String str, final int position) {
        if (mManagerType == LayoutManagerPopupWindow.TYPE_LINEAR){
            setItemViewWidth(holder.itemView, ScreenUtils.getScreenWidth(getContext()));
            setItemViewHeight(holder.itemView, DensityUtils.dp2px(getContext(), 100));
        }
        if (mManagerType == LayoutManagerPopupWindow.TYPE_GRID){
            setItemViewWidth(holder.itemView, ScreenUtils.getScreenWidth(getContext()) / 3);
            setItemViewHeight(holder.itemView, DensityUtils.dp2px(getContext(), 100));
        }
        if (mManagerType == LayoutManagerPopupWindow.TYPE_STAGGERED){
            setItemViewWidth(holder.itemView, ScreenUtils.getScreenWidth(getContext()) / 3);
            if (position % 3 == 0){
                setItemViewHeight(holder.itemView, DensityUtils.dp2px(getContext(), 300));
            }else if (position % 3 == 1){
                setItemViewHeight(holder.itemView, DensityUtils.dp2px(getContext(), 100));
            }else {
                setItemViewHeight(holder.itemView, DensityUtils.dp2px(getContext(), 200));
            }
        }

        Random random = new Random();
        int corlor = ContextCompat.getColor(getContext(), mColors[(random.nextInt(100) + 1) % mColors.length]);
        holder.mRootLayout.setBackgroundColor(corlor);
        holder.dataTextView.setText(str);
        holder.dataTextView.setTextColor(corlor);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickDelete(position);
                }
            }
        });
        holder.deleteBtn.setBackgroundColor(corlor);
    }

    class DataViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.root_layout)
        ViewGroup mRootLayout;

        @BindView(R.id.data_text)
        TextView dataTextView;

        @BindView(R.id.delete_btn)
        TextView deleteBtn;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /** 设置布局类型 */
    public void setManagerType(@LayoutManagerPopupWindow.LayoutManagerType int type){
        mManagerType = type;
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onClickDelete(int position);
    }
}
