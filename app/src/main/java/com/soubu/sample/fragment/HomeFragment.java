package com.soubu.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.soubu.sample.R;
import com.soubu.androidlib.fragment.BaseFragment;


/**
 * 作者：余天然 on 2017/3/13 上午11:20
 */
public class HomeFragment extends BaseFragment {
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_item;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("首页");
    }

}
