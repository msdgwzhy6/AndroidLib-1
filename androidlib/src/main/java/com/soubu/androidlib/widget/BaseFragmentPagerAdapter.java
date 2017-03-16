package com.soubu.androidlib.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

//碎片适配器
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> data = new ArrayList<>();
    private String[] titles = {};

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Fragment> datas) {
        this.data = datas;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.isEmpty() ? 0 : data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }
}
