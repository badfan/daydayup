package com.ff.common.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author zhy
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    private int mDividerHeight;
    private int mDividerWidth;
    private final int mDividerColor;
    private Paint mPaint;

    public DividerGridItemDecoration(Context context, int dividerHeight, int dividerWidth, int dividerColor) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mDividerHeight = dividerHeight;
        mDividerWidth = dividerWidth;
        mDividerColor = ContextCompat.getColor(context, dividerColor);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mDividerColor);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        drawHorizontal(c, parent);
        drawVertical(c, parent);

    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDividerWidth;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            if (mPaint != null) {
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDividerWidth;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);

            if (mPaint != null) {
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
//        int spanCount = getSpanCount(parent);
//        int childCount = parent.getAdapter().getItemCount();
//        if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
//        {
//            outRect.set(0, 0, mDividerWidth, 0);
//        } else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
//        {
//            outRect.set(0, 0, 0, mDividerWidth);
//        } else {
//            outRect.set(0, 0, mDividerWidth, mDividerHeight);
//        }


        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();

        boolean isLastRow = isLastRaw(parent, itemPosition, spanCount, childCount);

        int top = 0;
        int left;
        int right;
        int bottom;

        int eachWidth = (spanCount - 1) * mDividerWidth / spanCount;

        left = itemPosition % spanCount * (mDividerWidth - eachWidth);
        right = eachWidth - left;
        bottom = mDividerWidth;
        if (isLastRow){
            bottom = 0;
        }
        outRect.set(left, top, right, bottom);
    }
}