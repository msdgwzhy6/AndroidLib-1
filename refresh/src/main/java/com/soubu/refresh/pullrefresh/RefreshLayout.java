package com.soubu.refresh.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.soubu.refresh.pullrefresh.core.PullLayout;


/**
 * 作者：余天然 on 2016/10/18 下午9:15
 */
public class RefreshLayout extends PullLayout {

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        HeaderView header = new HeaderView(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);

        setHeader(header);
        setFooter(footer);
    }

}
