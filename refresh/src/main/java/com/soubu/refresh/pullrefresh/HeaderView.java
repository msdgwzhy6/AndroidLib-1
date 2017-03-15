package com.soubu.refresh.pullrefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soubu.refresh.R;
import com.soubu.refresh.pullrefresh.core.PullHeader;


/**
 * 作者：余天然 on 2016/10/18 下午7:10
 */
public class HeaderView extends FrameLayout implements PullHeader {

    public TextView tv;
    public ProgressBar pb;

    public HeaderView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_header, this, true);
        tv = (TextView) findViewById(R.id.tv);
        pb = (ProgressBar) findViewById(R.id.pb);
    }

    @Override
    public void onDownBefore(int scrollY) {
        tv.setText("下拉刷新");
    }

    @Override
    public void onDownAfter(int scrollY) {
        tv.setText("松开刷新");
    }

    @Override
    public void onRefreshScrolling(int scrollY) {
        tv.setText("准备刷新");
    }

    @Override
    public void onRefreshDoing(int scrollY) {
        tv.setText("正在刷新……");
    }

    @Override
    public void onRefreshCompleteScrolling(int scrollY, boolean isLoadSuccess) {
        tv.setText(isLoadSuccess ? "刷新成功" : "刷新失败");
    }

    @Override
    public void onRefreshCancelScrolling(int scrollY) {
        tv.setText("取消刷新");
    }
}
