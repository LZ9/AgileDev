package com.lodz.android.agiledev.ui.rv.drag;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.utils.ScreenUtils;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 拖拽适配器
 * Created by zhouL on 2017/3/6.
 */
public class DragAdapter extends BaseRecyclerViewAdapter<String>{

    private ItemTouchHelper mItemTouchHelper;

    public DragAdapter(Context context) {
        super(context);
    }

    public void setItemTouchHelper(ItemTouchHelper helper){
        mItemTouchHelper = helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DragViewHolder(getLayoutView(parent, R.layout.item_drag_recycler_view_layout));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        String text = getItem(position);
        if (TextUtils.isEmpty(text)){
            return;
        }
        showItem((DragViewHolder) holder, text);
    }

    private void showItem(DragViewHolder holder, final String text) {
        setItemViewWidth(holder.itemView, ScreenUtils.getScreenWidth(getContext()) / 4);
        holder.indexTextView.setText(text);
    }

    class DragViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{
        /** 序号 */
        @BindView(R.id.index_text)
        TextView indexTextView;

        private DragViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            indexTextView.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    if (mItemTouchHelper != null){
                        mItemTouchHelper.startDrag(this);
                    }
                    break;
                }
            }
            return false;
        }
    }
}
