package com.soubu.refresh.pullrefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.soubu.refresh.R;
import com.soubu.refresh.pullrefresh.core.PullFooter;


/**
 * 作者：余天然 on 2016/10/18 下午7:11
 */
public class FooterView extends FrameLayout implements PullFooter {

    public TextView tv;

    public FooterView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_footer, this, true);
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void onUpBefore(int scrollY) {
        tv.setText("上拉加载更多");
    }

    @Override
    public void onUpAfter(int scrollY) {
        tv.setText("松开加载更多");
    }

    @Override
    public void onLoadScrolling(int scrollY) {
        tv.setText("准备加载");
    }

    @Override
    public void onLoadDoing(int scrollY) {
        tv.setText("正在加载……");
    }

    @Override
    public void onLoadCompleteScrolling(int scrollY, boolean isLoadSuccess) {
        tv.setText(isLoadSuccess ? "加载成功" : "加载失败");
    }

    @Override
    public void onLoadCancelScrolling(int scrollY) {
        tv.setText("加载取消");
    }
}
