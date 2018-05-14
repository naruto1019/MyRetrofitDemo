package com.example.dou.myretrofitdemo.itemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.dou.myretrofitdemo.R;

/**
 * Created by doudou on 2017/11/1.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;

    public MyItemDecoration(Context context){
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.text_color_blue));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = 3;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int count = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i=0;i<count-1;i++){
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom()+3;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
//        int count = parent.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View child = parent.getChildAt(i);
//            int pos = parent.getChildAdapterPosition(child);
//            boolean isLeft = pos % 2 == 0;
//            if (isLeft) {
//                float left = child.getLeft();
//                float right = left + 10;
//                float top = child.getTop();
//                float bottom = child.getBottom();
//                c.drawRect(left, top, right, bottom, mPaint);
//            } else {
//                float right = child.getRight();
//                float left = right - 10;
//                float top = child.getTop();
//                float bottom = child.getBottom();
//                c.drawRect(left, top, right, bottom, mPaint);
//            }
//        }
    }
}
