package com.soubu.sample;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.soubu.sample.fragment.ChatFragment;
import com.soubu.sample.fragment.FindFragment;
import com.soubu.sample.fragment.HomeFragment;
import com.soubu.sample.fragment.MineFragment;
import com.soubu.ui.activity.TabActivity;

/**
 * 作者：余天然 on 2017/3/15 上午11:29
 */
public class TestTabActivity extends TabActivity {

    private TextView[] tabs;

    @Override
    public String createTitleText() {
        return "不可滑动的Fragment";
    }

    @Override
    public int createLayoutId() {
        return R.layout.activity_tab;
    }

    @Override
    public void init() {

    }

    @Override
    public void initButton() {
        TextView tvHome = (TextView) findViewById(R.id.tv_home);
        TextView tvChat = (TextView) findViewById(R.id.tv_chat);
        TextView tvFind = (TextView) findViewById(R.id.tv_find);
        TextView tvMine = (TextView) findViewById(R.id.tv_mine);
        tabs = new TextView[]{tvHome, tvChat, tvFind, tvMine};

        for (int i = 0; i < tabs.length; i++) {
            final int finalI = i;
            tabs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchTab(finalI);
                }
            });
        }
    }

    @Override
    public void switchButton(int pos) {
        for (int i = 0; i < tabs.length; i++) {
            if (i == pos) {
                tabs[i].setSelected(true);
            } else {
                tabs[i].setSelected(false);
            }
        }
    }

    @Override
    public int getFragmentViewId() {
        return R.id.fr_fragment;
    }

    @Override
    public int getFragmentSize() {
        return 4;
    }

    @Override
    public Fragment getFragment(int pos) {
        switch (pos) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ChatFragment();
            case 2:
                return new FindFragment();
            case 3:
                return new MineFragment();
        }
        return null;
    }
}
