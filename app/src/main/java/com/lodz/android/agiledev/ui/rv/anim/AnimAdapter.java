package com.lodz.android.agiledev.ui.rv.anim;

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
 * 动画测试适配器
 * Created by zhouL on 2017/9/5.
 */

public class AnimAdapter extends BaseRecyclerViewAdapter<String>{

    public static int[] mEmojiUnicode = {0x1F601, 0x1F602, 0x1F603, 0x1F604, 0x1F605, 0x1F606, 0x1F609, 0x1F60A,
            0x1F60B, 0x1F60C, 0x1F60D, 0x1F60E, 0x1F60F, 0x1F612, 0x1F613, 0x1F614, 0x1F616, 0x1F618, 0x1F61A,
            0x1F61C, 0x1F61D, 0x1F61E, 0x1F620, 0x1F621, 0x1F622, 0x1F623, 0x1F624, 0x1F625, 0x1F628, 0x1F629,
            0x1F62A, 0x1F62B, 0x1F62D, 0x1F630, 0x1F631, 0x1F632, 0x1F633, 0x1F634, 0x1F635, 0x1F637, 0x1F638,
            0x1F639, 0x1F63A, 0x1F63B, 0x1F63C, 0x1F63D, 0x1F63E, 0x1F63F,};


    public AnimAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnimViewHolder(getLayoutView(parent, R.layout.item_anim_layout));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        String name = getItem(position);
        if (TextUtils.isEmpty(name)) {
            return;
        }
        showItem((AnimViewHolder) holder, name, position);
    }

    private void showItem(AnimViewHolder holder, String time, int position) {
        Random random = new Random();
        holder.emojiTv.setText(new String(Character.toChars(mEmojiUnicode[(random.nextInt(100) + 1) % mEmojiUnicode.length])));
        holder.positionTv.setText(String.valueOf(position + 1));
        holder.timeTv.setText(time);
    }

    class AnimViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.emoji)
        TextView emojiTv;

        @BindView(R.id.position)
        TextView positionTv;

        @BindView(R.id.time)
        TextView timeTv;

        private AnimViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
