package com.soubu.sample.fragment;

import android.widget.TextView;

import com.soubu.androidlib.fragment.LazyFragment;
import com.soubu.sample.R;

/**
 * 作者：余天然 on 2017/3/13 上午11:20
 */
public class MineFragment extends LazyFragment {
    @Override
    protected int createLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void onStateFirst() {
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("我的");
    }

}
