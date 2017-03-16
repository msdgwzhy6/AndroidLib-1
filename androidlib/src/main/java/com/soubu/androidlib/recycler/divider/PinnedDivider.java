package com.soubu.androidlib.recycler.divider;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soubu.androidlib.recycler.adapter.BaseViewHolder;

import java.util.List;

/**
 * 分组悬停视图
 * <p>
 * 1、显示简单Text
 * 2、显示复杂的xml
 * <p>
 * 当设置了xml时，简单的Text会失效
 * 作者：余天然 on 2016/12/21 下午6:51
 */
public class PinnedDivider<T extends Pinnable> extends RecyclerView.ItemDecoration {

    private Paint paint;//画笔
    private Rect rect = new Rect();//用于存放测量文字Rect
    private Drawable divider;//分割线颜色

    private Builder<T> builder;
    private BaseViewHolder viewHolder;

    private ShowType type;//1-简单Text,2-复杂xml

    private PinnedDivider(Builder builder) {
        this.builder = builder;
        if (builder.displayer != null) {
            type = ShowType.XML;
        } else {
            type = ShowType.TEXT;
        }
        this.paint = new Paint();
        this.paint.setTextSize(builder.tagTextSize);
        this.paint.setAntiAlias(true);
        this.divider = new ColorDrawable(builder.dividerColor);
    }

    /**
     * 设置分组悬停视图的显示区域
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (type == ShowType.XML && viewHolder == null) {
            viewHolder = createViewHolder(parent);
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        int position = params.getViewLayoutPosition() - builder.headerCount;

        //防止越界
        if (position > builder.data.size() - 1 || position < 0) {
            return;
        }
        //第1项肯定要有tag
        if (position == 0) {
            outRect.set(0, builder.tagHeight, 0, 0);
        }
        //其余项，不为空且跟前一个tag不一样了，说明是新的分类，也要tag
        else if (!builder.data.get(position).getPinnedTag().equals(builder.data.get(position - 1).getPinnedTag())) {
            outRect.set(0, builder.tagHeight, 0, 0);
        }
        //和下一项一样的，都需要分割线
        for (int i = 0; i < builder.data.size() - 1; i++) {
            String tag1 = builder.data.get(i).getPinnedTag();
            String tag2 = builder.data.get(i + 1).getPinnedTag();
            if (tag1.equals(tag2)) {
                int top = outRect.top;
                outRect.set(0, top, 0, builder.dividerHeight);
            }
        }
    }

    /**
     * 绘制最底层-跟随滚动的tag
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        //绘制随着滚动的分组视图
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition() - builder.headerCount;

            //防止越界
            if (position > builder.data.size() - 1 || position < 0) {
                continue;
            }

            String tag = builder.data.get(position).getPinnedTag();

            int top = child.getTop() - params.topMargin - builder.tagHeight;
            int bottom = child.getTop() - params.topMargin;

            //等于0肯定要有title的
            if (position == 0) {
                dispatchDraw(c, position, parent, top, bottom);
            }
            //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
            else if (null != tag && !tag.equals(builder.data.get(position - 1).getPinnedTag())) {


                dispatchDraw(c, position, parent, top, bottom);
            }
        }

        //和下一项一样的，都需要分割线
        int left, top, right, bottom;
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            String tag1 = builder.data.get(position).getPinnedTag();
            String tag2 = builder.data.get(position + 1).getPinnedTag();
            if (tag1.equals(tag2)) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                left = parent.getPaddingLeft() + builder.dividerPaddingLeft;
                right = parent.getWidth() - parent.getPaddingRight();
                top = child.getBottom() + params.bottomMargin;
                bottom = top + builder.dividerHeight;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

    /**
     * 绘制最上层-绘制悬停的tag
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        int next = findNextTagPosition(position);

        //绘制悬停的分组视图
        boolean flag = false;
        if (next != -1) {
            View child = parent.findViewHolderForLayoutPosition(next).itemView;
            int dy = child.getTop() - builder.tagHeight * 2;
            if (dy <= 0) {
                c.save();
                c.translate(0, dy);
                flag = true;
            }
        }
        dispatchDraw(c, position, parent, parent.getPaddingTop(), parent.getPaddingTop() + builder.tagHeight);
        if (flag) {
            c.restore();
        }
    }

    /**
     * 创建ViewHolder
     */
    private BaseViewHolder createViewHolder(RecyclerView parent) {
        View toDrawView = LayoutInflater.from(builder.context).inflate(builder.displayer.layoutId, parent, false);

        int toDrawWidthSpec;//用于测量的widthMeasureSpec
        int toDrawHeightSpec;//用于测量的heightMeasureSpec
        //拿到复杂布局的LayoutParams，如果为空，就new一个。
        // 后面需要根据这个lp 构建toDrawWidthSpec，toDrawHeightSpec
        ViewGroup.LayoutParams lp = toDrawView.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//这里是根据复杂布局layout的width height，new一个Lp
            toDrawView.setLayoutParams(lp);
        }
        if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            //如果是MATCH_PARENT，则用父控件能分配的最大宽度和EXACTLY构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.EXACTLY);
        } else if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //如果是WRAP_CONTENT，则用父控件能分配的最大宽度和AT_MOST构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
        } else {
            //否则则是具体的宽度数值，则用这个宽度和EXACTLY构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(lp.width, View.MeasureSpec.EXACTLY);
        }
        //高度同理
        if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.EXACTLY);
        } else if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.AT_MOST);
        } else {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(lp.height, View.MeasureSpec.EXACTLY);
        }
        //依次调用 measure,tagDisplayer,draw方法，将复杂头部显示在屏幕上。
        toDrawView.measure(toDrawWidthSpec, toDrawHeightSpec);
        toDrawView.layout(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getPaddingLeft() + toDrawView.getMeasuredWidth(), parent.getPaddingTop() + toDrawView.getMeasuredHeight());
        viewHolder = new BaseViewHolder(toDrawView);
        builder.tagHeight = toDrawView.getMeasuredHeight();
        return viewHolder;
    }

    /**
     * 查找下一个Tag的位置
     */
    private int findNextTagPosition(int position) {
        String curTag = builder.data.get(position).getPinnedTag();
        for (int i = position + 1; i < builder.data.size(); i++) {
            String tag = builder.data.get(i).getPinnedTag();
            if (!tag.equals(curTag)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 分发绘制
     */
    private void dispatchDraw(Canvas c, int position, RecyclerView parent, int top, int bottom) {
        //简单的Text
        if (type == ShowType.TEXT) {
            drawText(c, position, parent, top, bottom);
        }
        //复杂的xml
        else {
            drawXML(c, position, parent, top, bottom);
        }
    }

    /**
     * 绘制xml加载的分组悬停视图
     */
    private void drawXML(Canvas c, int position, RecyclerView parent, int top, int bottom) {
        builder.displayer.showData(viewHolder, builder.data.get(position));
        c.save();
        c.translate(0, top);
        viewHolder.getRootView().draw(c);
        c.restore();
        parent.postInvalidate();
//        parent.requestLayout();
    }

    /**
     * 绘制手动绘制的分组悬停视图
     */
    private void drawText(Canvas c, int position, RecyclerView parent, int top, int bottom) {
        //绘制背景
        paint.setColor(builder.tagBgColor);
        c.drawRect(parent.getPaddingLeft(), top, parent.getRight() - parent.getPaddingRight(), bottom, paint);
        //绘制文字
        String tag = builder.data.get(position).getPinnedTag();
        paint.setColor(builder.tagTextColor);
        paint.getTextBounds(tag, 0, tag.length(), rect);
        c.drawText(tag, parent.getPaddingLeft() + builder.tagTextLeftPadding, bottom - (builder.tagHeight - rect.height()) / 2, paint);
    }


    public static class Builder<BT extends Pinnable> {
        //必填部分
        private Context context;
        private Resources resources;
        private List<BT> data;

        //可选部分
        private int headerCount;//其余的HeaderView数量

        //复杂xml
        private TagDisplayer displayer;

        //普通Text
        private int tagBgColor = 0x000000;
        private int tagHeight = 0;
        private int tagTextColor = 0x00000000;
        private int tagTextSize = 0;
        private int tagTextLeftPadding = 0;

        //分割线
        private int dividerColor = 0x000000;
        private int dividerHeight = 0;
        private int dividerPaddingLeft = 0;

        public Builder(Context context, @NonNull List<BT> data) {
            this.context = context;
            this.resources = context.getResources();
            this.data = data;
            if (data == null || data.isEmpty()) {
                throw new RuntimeException("data can not be empty");
            }
        }

        public Builder<BT> tagBgColor(@ColorRes int tagBgColor) {
            this.tagBgColor = resources.getColor(tagBgColor);
            return this;
        }

        public Builder<BT> tagHeight(@DimenRes int tagHeight) {
            this.tagHeight = resources.getDimensionPixelSize(tagHeight);
            return this;
        }

        public Builder<BT> tagTextColor(@ColorRes int tagTextColor) {
            this.tagTextColor = resources.getColor(tagTextColor);
            return this;
        }

        public Builder<BT> tagTextSize(@DimenRes int tagTextSize) {
            this.tagTextSize = resources.getDimensionPixelSize(tagTextSize);
            return this;
        }

        public Builder<BT> tagTextLeftPadding(@DimenRes int tagTextLeftPadding) {
            this.tagTextLeftPadding = resources.getDimensionPixelSize(tagTextLeftPadding);
            return this;
        }

        public Builder<BT> dividerColor(@ColorRes int dividerColor) {
            this.dividerColor = resources.getColor(dividerColor);
            return this;
        }

        public Builder<BT> dividerHeight(@DimenRes int dividerHeight) {
            this.dividerHeight = resources.getDimensionPixelSize(dividerHeight);
            return this;
        }

        public Builder<BT> dividerPaddingLeft(@DimenRes int dividerPaddingLeft) {
            this.dividerPaddingLeft = resources.getDimensionPixelSize(dividerPaddingLeft);
            return this;
        }

        public Builder<BT> tagDisplayer(@NonNull TagDisplayer<BT> displayer) {
            this.displayer = displayer;
            if (displayer == null) {
                throw new RuntimeException("displayer can not be null");
            }
            return this;
        }

        public PinnedDivider<BT> build() {
            return new PinnedDivider<BT>(this);
        }


    }

    public static abstract class TagDisplayer<DT extends Pinnable> {
        private int layoutId;

        public TagDisplayer(@LayoutRes int layoutId) {
            this.layoutId = layoutId;
        }

        public abstract void showData(BaseViewHolder viewHolder, DT item);
    }

    public enum ShowType {
        TEXT,
        XML
    }

}
