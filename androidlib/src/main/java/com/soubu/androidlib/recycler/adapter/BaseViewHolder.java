package com.soubu.androidlib.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ViewHolder基类
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public BaseViewHolder(View view) {
        super(view);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView() {
        return itemView;
    }

    public void setHint(int tvId, String text) {
        TextView tv = getView(tvId);
        tv.setHint(text);
    }

    public void setText(int tvId, String text) {
        TextView tv = getView(tvId);
        tv.setText(text);
    }

    public void setText(int tvId, int textId) {
        TextView tv = getView(tvId);
        tv.setText(textId);
    }

    public void setImage(int ivId, int iconRes) {
        ImageView iv = getView(ivId);
        iv.setImageResource(iconRes);
    }

    public void setOnClickListener( int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
    }

    public void setOnClickListener( View.OnClickListener listener) {
        View view = getRootView();
        view.setOnClickListener(listener);
    }
}