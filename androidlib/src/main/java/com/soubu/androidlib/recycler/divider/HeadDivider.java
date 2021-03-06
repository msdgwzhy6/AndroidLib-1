package com.soubu.androidlib.recycler.divider;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 头部分割线
 * <p>
 * 作者：余天然 on 2016/12/21 下午3:21
 */
public class HeadDivider extends RecyclerView.ItemDecoration {

    private Drawable divider;//分割线颜色
    private int size;//分割线尺寸
    private int orientation;//方向

    public HeadDivider(Context context, @ColorRes int color, @DimenRes int size, int orientation) {
        Resources resources = context.getResources();
        divider = new ColorDrawable(resources.getColor(color));
        this.size = resources.getDimensionPixelSize(size);
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left, top, right, bottom;
        //注意：这里不能用parent.getChildCount()！
        int childCount = parent.getAdapter().getItemCount();
        //水平方向
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            //只绘制头部的分割线
            View child = parent.getChildAt(0);
            if (child == null) {
                return;
            }
            RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            right = child.getLeft() - params.leftMargin;
            left = right - size;
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
        //竖直方向
        else {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            //只绘制头部的分割线
            View child = parent.getChildAt(0);
            if (child == null) {
                return;
            }
            RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            bottom = child.getTop() - params.topMargin;
            top = bottom - size;
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //只设置头部的分割线的区域
        if (parent.getChildAdapterPosition(view) == 0) {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.set(size, 0, 0, 0);
            } else {
                outRect.set(0, size, 0, 0);
            }
        }

    }
}
