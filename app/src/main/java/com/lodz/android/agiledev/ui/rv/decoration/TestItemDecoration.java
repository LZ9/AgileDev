package com.lodz.android.agiledev.ui.rv.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lodz.android.agiledev.R;
import com.lodz.android.core.utils.DensityUtils;

/**
 * 测试ItemDecoration
 * Created by zhouL on 2018/2/7.
 */

public class TestItemDecoration extends RecyclerView.ItemDecoration{

    //----------------------------- 分割线 ---------------------------

    private int dividerHeight;
    private Paint dividerPaint;

    public TestItemDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.color_ea5e5e));
        dividerHeight = DensityUtils.dp2px(context, 1);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }


    //----------------------------- 标签 ---------------------------

//    private int tagWidth;
//    private Paint leftPaint;
//    private Paint rightPaint;
//
//    public TestItemDecoration(Context context) {
//        leftPaint = new Paint();
//        leftPaint.setColor(context.getResources().getColor(R.color.color_ea5e5e));
//        rightPaint = new Paint();
//        rightPaint.setColor(context.getResources().getColor(R.color.color_3981ef));
//        tagWidth = DensityUtils.dp2px(context, 15);
//    }
//
//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            int pos = parent.getChildAdapterPosition(child);
//            boolean isLeft = pos % 2 == 0;
//            if (isLeft) {
//                float left = child.getLeft();
//                float right = left + tagWidth;
//                float top = child.getTop();
//                float bottom = child.getBottom();
//                c.drawRect(left, top, right, bottom, leftPaint);
//            } else {
//                float right = child.getRight();
//                float left = right - tagWidth;
//                float top = child.getTop();
//                float bottom = child.getBottom();
//                c.drawRect(left, top, right, bottom, rightPaint);
//
//            }
//        }
//    }

    //----------------------------- section ---------------------------

//    private static final String TAG = "SectionDecoration";
//
//    private DecorationCallback callback;
//    private TextPaint textPaint;
//    private Paint paint;
//    private int topGap;
//    private Paint.FontMetrics fontMetrics;
//
//
//    public TestItemDecoration(Context context, DecorationCallback decorationCallback) {
//        Resources res = context.getResources();
//        this.callback = decorationCallback;
//
//        paint = new Paint();
//        paint.setColor(res.getColor(R.color.color_ea5e5e));
//
//        textPaint = new TextPaint();
//        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        textPaint.setAntiAlias(true);
//        textPaint.setTextSize(80);
//        textPaint.setColor(Color.BLACK);
//        textPaint.getFontMetrics(fontMetrics);
//        textPaint.setTextAlign(Paint.Align.LEFT);
//        fontMetrics = new Paint.FontMetrics();
//        topGap = DensityUtils.dp2px(context, 32);//32dp
//
//
//    }
//
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//        int pos = parent.getChildAdapterPosition(view);
//        Log.i(TAG, "getItemOffsets：" + pos);
//        long groupId = callback.getGroupId(pos);
//        if (groupId < 0) return;
//        if (pos == 0 || isFirstInGroup(pos)) {//同组的第一个才添加padding
//            outRect.top = topGap;
//        } else {
//            outRect.top = 0;
//        }
//    }
//
//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View view = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(view);
//            long groupId = callback.getGroupId(position);
//            if (groupId < 0) return;
//            String textLine = callback.getGroupFirstLine(position).toUpperCase();
//            if (position == 0 || isFirstInGroup(position)) {
//                float top = view.getTop() - topGap;
//                float bottom = view.getTop();
//                c.drawRect(left, top, right, bottom, paint);//绘制红色矩形
//                c.drawText(textLine, left, bottom, textPaint);//绘制文本
//            }
//        }
//    }
//
//
//    private boolean isFirstInGroup(int pos) {
//        if (pos == 0) {
//            return true;
//        } else {
//            long prevGroupId = callback.getGroupId(pos - 1);
//            long groupId = callback.getGroupId(pos);
//            return prevGroupId != groupId;
//        }
//    }
//
//    public interface DecorationCallback {
//
//        long getGroupId(int position);
//
//        String getGroupFirstLine(int position);
//    }


    //----------------------------- StickyHeader ---------------------------

//    private static final String TAG = "PinnedSectionDecoration";
//
//    private DecorationCallback callback;
//    private TextPaint textPaint;
//    private Paint paint;
//    private int topGap;
//    private Paint.FontMetrics fontMetrics;
//
//
//    public TestItemDecoration(Context context, DecorationCallback decorationCallback) {
//        Resources res = context.getResources();
//        this.callback = decorationCallback;
//
//        paint = new Paint();
//        paint.setColor(res.getColor(R.color.color_ea5e5e));
//
//        textPaint = new TextPaint();
//        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        textPaint.setAntiAlias(true);
//        textPaint.setTextSize(80);
//        textPaint.setColor(Color.BLACK);
//        textPaint.getFontMetrics(fontMetrics);
//        textPaint.setTextAlign(Paint.Align.LEFT);
//        fontMetrics = new Paint.FontMetrics();
//        topGap = DensityUtils.dp2px(context, 32);
//
//
//    }
//
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//        int pos = parent.getChildAdapterPosition(view);
//        long groupId = callback.getGroupId(pos);
//        if (groupId < 0) return;
//        if (pos == 0 || isFirstInGroup(pos)) {
//            outRect.top = topGap;
//        } else {
//            outRect.top = 0;
//        }
//    }
//
//
//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
//        int itemCount = state.getItemCount();
//        int childCount = parent.getChildCount();
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;
//
//        long preGroupId, groupId = -1;
//        for (int i = 0; i < childCount; i++) {
//            View view = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(view);
//
//            preGroupId = groupId;
//            groupId = callback.getGroupId(position);
//            if (groupId < 0 || groupId == preGroupId) continue;
//
//            String textLine = callback.getGroupFirstLine(position).toUpperCase();
//            if (TextUtils.isEmpty(textLine)) continue;
//
//            int viewBottom = view.getBottom();
//            float textY = Math.max(topGap, view.getTop());
//            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
//                long nextGroupId = callback.getGroupId(position + 1);
//                if (nextGroupId != groupId && viewBottom < textY ) {//组内最后一个view进入了header
//                    textY = viewBottom;
//                }
//            }
//            c.drawRect(left, textY - topGap, right, textY, paint);
//            c.drawText(textLine, left, textY, textPaint);
//        }
//
//    }
//
//    private boolean isFirstInGroup(int pos) {
//        if (pos == 0) {
//            return true;
//        } else {
//            long prevGroupId = callback.getGroupId(pos - 1);
//            long groupId = callback.getGroupId(pos);
//            return prevGroupId != groupId;
//        }
//    }
//
//    public interface DecorationCallback {
//        long getGroupId(int position);
//        String getGroupFirstLine(int position);
//    }
}
