package com.soubu.recycler.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 单布局的Adapter
 * ---------------
 * 数据类型是泛型
 */
public abstract class SingleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    Context context;
    LayoutInflater inflater;
    public List<T> data = new ArrayList<>();
    private int layoutId;
    private SparseArray<Boolean> listenerMap = new SparseArray<>();


    public SingleAdapter(Context context, int layoutId) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseViewHolder holder = new BaseViewHolder(inflater.inflate(layoutId, parent, false));
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getLayoutPosition();
                onItemClick(holder, data.get(position), position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, data.get(position), position);
        boolean hasListener = listenerMap.get(position, false);
        if (!hasListener) {
            bindListener(holder, data.get(position), position);
            listenerMap.put(position, true);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Context getContext() {
        return context;
    }

    public void setData(List<T> list) {
        this.data = list;
        listenerMap.clear();
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
    }

    public void setDiffData(List<T> list) {
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(oldDatas, newDatas), true);
//        diffResult.dispatchUpdatesTo(mAdapter);
    }

    public List<T> getData() {
        return data;
    }

    /**
     * 绑定数据
     */
    protected abstract void bindData(BaseViewHolder holder, T item, int position);

    /**
     * 绑定点击事件
     */
    public void onItemClick(BaseViewHolder holder, T item, int position) {
    }

    /**
     * 绑定监听事件
     */
    protected void bindListener(BaseViewHolder holder, T item, int position) {
    }

}
