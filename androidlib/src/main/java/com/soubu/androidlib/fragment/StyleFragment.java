package com.soubu.androidlib.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：余天然 on 2017/3/13 下午2:44
 */
public abstract class StyleFragment extends StateFragment {
    private View rootView;

    protected abstract int createLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = createLayoutId();
        rootView = inflater.inflate(rootLayoutId, container, false);
        return rootView;
    }

    protected View findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }

    protected View getRootView() {
        return rootView;
    }

}
