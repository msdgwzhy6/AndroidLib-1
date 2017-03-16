package com.soubu.androidlib.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * 使用hide/show时，由Fragment自己来管理自己的Hidden状态
 * <p>
 * 作者：余天然 on 2017/3/13 下午1:55
 */
public abstract class StateFragment extends Fragment {
    private static final String HIDDEN_STATE = "HIDDEN_STATE";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            boolean hidden = savedInstanceState.getBoolean(HIDDEN_STATE);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (hidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        onStateFirst();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(HIDDEN_STATE, isHidden());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onStateHideen();
        } else {
            onStateVisiable();
        }
    }

    /**
     * 第一次显示
     */
    protected void onStateFirst() {
    }

    /**
     * 显示时（第一次除外）
     */
    protected void onStateVisiable() {
    }

    /**
     * 隐藏时
     */
    protected void onStateHideen() {

    }


}
