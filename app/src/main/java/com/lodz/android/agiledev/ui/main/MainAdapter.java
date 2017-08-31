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

    public static int[] mEmojiUnicode = {0x1F601, 0x1F602, 0x1F603, 0x1F604, 0x1F605, 0x1F606, 0x1F609, 0x1F60A,
            0x1F60B, 0x1F60C, 0x1F60D, 0x1F60E, 0x1F60F, 0x1F612, 0x1F613, 0x1F614, 0x1F616, 0x1F618, 0x1F61A,
            0x1F61C, 0x1F61D, 0x1F61E, 0x1F620, 0x1F621, 0x1F622, 0x1F623, 0x1F624, 0x1F625, 0x1F628, 0x1F629,
            0x1F62A, 0x1F62B, 0x1F62D, 0x1F630, 0x1F631, 0x1F632, 0x1F633, 0x1F634, 0x1F635, 0x1F637, 0x1F638,
            0x1F639, 0x1F63A, 0x1F63B, 0x1F63C, 0x1F63D, 0x1F63E, 0x1F63F,};


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
        holder.nameTv.setTextColor(ContextCompat.getColor(getContext(), mColors[(random.nextInt(100) + 1) % mColors.length]));
        String str = new String(Character.toChars( mEmojiUnicode[(random.nextInt(100) + 1) % mEmojiUnicode.length])) + "   " + name;
        holder.nameTv.setText(str);
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
