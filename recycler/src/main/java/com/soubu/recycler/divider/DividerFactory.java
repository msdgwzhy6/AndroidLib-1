package com.soubu.recycler.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.recycler.R;

/**
 * 分割线工厂
 * <p>
 * 作者：余天然 on 2016/12/21 下午4:50
 */
public class DividerFactory {

    public static void setDefaultDivider(RecyclerView view, @DimenRes int size) {
        setDefaultDivider(view, DividerType.ITEM_FOOT, size);
    }

    public static void setDefaultDivider(RecyclerView view, DividerType type, @DimenRes int size) {
        setDivider(view, type, R.color.gray_divider, size, LinearLayoutManager.VERTICAL);
    }

    public static void setDivider(RecyclerView view, DividerType type, @ColorRes int color, @DimenRes int size, int orientation) {
        Context context = view.getContext();
        switch (type) {
            case HEAD:
                view.addItemDecoration(new HeadDivider(context, color, size, orientation));
                break;
            case ITEM:
                view.addItemDecoration(new ItemDivider(context, color, size, orientation));
                break;
            case FOOT:
                view.addItemDecoration(new FootDivider(context, color, size, orientation));
                break;
            case HEAD_FOOT:
                view.addItemDecoration(new HeadDivider(context, color, size, orientation));
                view.addItemDecoration(new FootDivider(context, color, size, orientation));
                break;
            case ITEM_HEAD:
                view.addItemDecoration(new ItemDivider(context, color, size, orientation));
                view.addItemDecoration(new HeadDivider(context, color, size, orientation));
                break;
            case ITEM_FOOT:
                view.addItemDecoration(new ItemDivider(context, color, size, orientation));
                view.addItemDecoration(new FootDivider(context, color, size, orientation));
                break;
            case ITEM_HEAD_FOOT:
                view.addItemDecoration(new ItemDivider(context, color, size, orientation));
                view.addItemDecoration(new HeadDivider(context, color, size, orientation));
                view.addItemDecoration(new FootDivider(context, color, size, orientation));
                break;
        }
    }

}
