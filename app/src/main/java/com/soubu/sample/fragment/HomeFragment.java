package com.soubu.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.soubu.androidlib.fragment.LazyFragment;
import com.soubu.sample.R;


/**
 * 作者：余天然 on 2017/3/13 上午11:20
 */
public class HomeFragment extends LazyFragment {
    @Override
    protected int createLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("首页");
    }

    @Override
    protected void onStateFirst() {
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("发现");
    }

}
