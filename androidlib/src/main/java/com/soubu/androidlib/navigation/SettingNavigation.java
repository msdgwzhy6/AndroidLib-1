package com.soubu.androidlib.navigation;

import android.view.View;
import android.widget.TextView;

import com.soubu.androidlib.R;


/**
 * 作者：余天然 on 2017/3/14 下午11:46
 */
public class SettingNavigation extends Navigation {

    private String setting;
    private CallBack callBack;

    public SettingNavigation(String setting, CallBack callBack) {
        this.setting = setting;
        this.callBack = callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.navigation_setting;
    }

    @Override
    public int getTitleId() {
        return R.id.tv_title;
    }

    @Override
    public int getBackId() {
        return R.id.fr_back;
    }

    @Override
    public void init(View navigationView) {
        TextView tvSetting = (TextView) navigationView.findViewById(R.id.tv_setting);
        tvSetting.setText(setting);
        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onClickSetting();
                }
            }
        });
    }

    public interface CallBack {
        void onClickSetting();
    }
}
