package com.soubu.androidlib.recycler.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dingsigang on 16-12-8.
 */

public abstract class FooterSingleAdapter<T> extends SingleAdapter<T> {

    //默认一页显式10项数据
    final int PAGE_SIZE = 20;
    int mFootLayoutId;
    final int TYPE_FOOT = 0x10;

    public FooterSingleAdapter(Context context, int layoutId, int footerLayoutId) {
        super(context, layoutId);
        mShowFooter = true;
        mFootLayoutId = footerLayoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            return new FooterViewHolder(inflater.inflate(mFootLayoutId, parent, false));
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    /**
     * 是否显示加载更多视图
     */
    boolean mShowFooter = false;

    public void setShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public class FooterViewHolder extends BaseViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public void setData(List<T> list, boolean isRefresh) {
        if (isRefresh) {
            if (!data.isEmpty()) {
                data.clear();
            }
        }
        if (list.size() < PAGE_SIZE) {
            setShowFooter(false);
        } else {
            setShowFooter(true);
        }
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            if (isShowFooter()) {
                return TYPE_FOOT;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }
}
