package com.soubu.androidlib.recycler.adapter;

import android.content.Context;

/**
 * Created by dingsigang on 16-12-8.
 */

public abstract class FastScrollRvAdapter<T> extends SingleAdapter<T> {

    public FastScrollRvAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, T item, int position) {

    }
}
