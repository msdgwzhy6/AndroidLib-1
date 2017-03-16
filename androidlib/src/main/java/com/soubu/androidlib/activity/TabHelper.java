package com.soubu.androidlib.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * 作者：余天然 on 2017/3/15 上午11:24
 */
public class TabHelper extends LifeCallback {

    private static String CUR_TAB = "CUR_TAB";

    private int curTab;//当前的tab的位置
    private int tabNum;//tab的个数

    private Callback tabCallback;

    public void setTabCallback(Callback tabCallback) {
        this.tabCallback = tabCallback;
    }

    public Callback getTabCallback() {
        return tabCallback;
    }

    public interface Callback {

        int getFragmentViewId();

        int getFragmentSize();

        Fragment getFragment(int pos);

        void initButton();

        void switchButton(int pos);
    }

    public TabHelper(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            curTab = savedInstanceState.getInt(CUR_TAB, 0);
        }
        initTab();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CUR_TAB, curTab);
    }

    public void initTab() {
        tabNum = tabCallback.getFragmentSize();
        tabCallback.initButton();
        switchTab(curTab);
    }

    public void switchTab(int pos) {
        tabCallback.switchButton(pos);
        switchFragment(pos);
        curTab = pos;
    }


    private void switchFragment(int index) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < tabNum; i++) {
            Fragment fragment = getFragmentWithCache(i);
            if (i == index) {
                if (fragment.isAdded()) {
                    transaction = transaction.show(fragment);
                } else {
                    transaction = transaction.add(tabCallback.getFragmentViewId(), fragment, String.valueOf(i));
                }
            } else {
                if (fragment.isAdded()) {
                    transaction = transaction.hide(fragment);
                }
            }
        }
        transaction.commit();
    }

    private Fragment getFragmentWithCache(int pos) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(pos));
        return fragment == null ? tabCallback.getFragment(pos) : fragment;
    }


}
