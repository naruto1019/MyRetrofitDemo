package com.example.dou.myretrofitdemo.itemDecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.example.dou.myretrofitdemo.R;
import com.orhanobut.logger.Logger;

/**
 * Created by doudou on 2017/11/21.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {

    private DecorationCallback mCallback;
    private TextPaint mTextPaint;
    private Paint mPaint;
    private int topGap;
    private Paint.FontMetrics mFontMetrics;

    public SectionDecoration(Context context, DecorationCallback callback){
        Resources res = context.getResources();
        this.mCallback = callback;

        mPaint = new Paint();
        mPaint.setColor(res.getColor(R.color.colorAccent));

        mTextPaint = new TextPaint();
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(20);
        mTextPaint.setColor(Color.BLACK);
        mFontMetrics = new Paint.FontMetrics();
        mTextPaint.getFontMetrics(mFontMetrics);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        topGap = res.getDimensionPixelSize(R.dimen.sectioned_top);//32dp
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        long groupId = mCallback.getGroupId(pos);
        if (groupId < 0) {
            return;
        }
        if (isFirstInGroup(pos)) {//同组的第一个才添加padding
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }

//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View view = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(view);
//            long groupId = mCallback.getGroupId(position);
//            if (groupId < 0) {
//                return;
//            }
//            String textLine = mCallback.getGroupFirstLine(position).toUpperCase();
//            if (isFirstInGroup(position)) {
//                float top = view.getTop() - topGap;
//                float bottom = view.getTop();
//                c.drawRect(left, top, right, bottom, mPaint);//绘制红色矩形
//                c.drawText(textLine, left, bottom, mTextPaint);//绘制文本
//            }
//        }
//    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        Logger.i("item:"+itemCount+"child:"+childCount);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = mTextPaint.getTextSize() + mFontMetrics.descent;

        long preGroupId, groupId = -1;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = mCallback.getGroupId(position);
            if (groupId < 0 || groupId == preGroupId) {
                continue;
            }
            String textLine = mCallback.getGroupFirstLine(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) {
                continue;
            }
            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                long nextGroupId = mCallback.getGroupId(position + 1);
                if (nextGroupId != groupId && viewBottom < textY ) {//组内最后一个view进入了header
                    textY = viewBottom;
                }
            }
            c.drawRect(left, textY - topGap, right, textY, mPaint);
            double baseline = (textY + Math.max(0, view.getTop() - topGap) - mFontMetrics.bottom - mFontMetrics.top) / 2;
            c.drawText(textLine, left, (int)baseline, mTextPaint);
        }
    }

    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            long prevGroupId = mCallback.getGroupId(pos - 1);
            long groupId = mCallback.getGroupId(pos);
            return prevGroupId != groupId;
        }
    }

    public interface DecorationCallback {

        long getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}
