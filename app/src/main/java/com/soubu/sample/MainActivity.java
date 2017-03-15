package com.soubu.sample;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.soubu.ui.activity.StyleActivity;
import com.soubu.ui.navigation.SettingNavigation;


public class MainActivity extends StyleActivity<SettingNavigation> {

    @Override
    public void init() {
        TextView tvMsg = (TextView) findViewById(R.id.tv_msg);
        tvMsg.setText("默认");
        tvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestTabActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public SettingNavigation createNavigation() {
        return new SettingNavigation("测试", new SettingNavigation.CallBack() {
            @Override
            public void onClickSetting() {
                ToastHelper.showShort("点击了测试");
            }
        });
    }

    @Override
    public String createTitleText() {
        return "主页";
    }

    @Override
    public int createLayoutId() {
        return R.layout.activity_default;
    }


}

