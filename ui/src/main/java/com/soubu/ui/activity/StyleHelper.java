package com.soubu.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.common.util.LogUtil;
import com.soubu.ui.exception.NullStyleException;
import com.soubu.ui.navigation.Navigation;

/**
 * 作者：余天然 on 2017/3/15 上午9:22
 */
public class StyleHelper<N extends Navigation> extends LifeCallback {

    private LinearLayout rootView;
    private Callback<N> styleCallback;
    private N navigation;

    public StyleHelper(AppCompatActivity activity) {
        super(activity);
    }

    public interface Callback<NT extends Navigation> {

        String createTitleText();

        NT createNavigation();

        int createLayoutId();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtil.print("");
        rootView = new LinearLayout(getActivity());
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(layoutParams);
        rootView.setOrientation(LinearLayout.VERTICAL);
        getActivity().setContentView(rootView);
        if (styleCallback == null) {
            throw new NullStyleException();
        }
        initNavigation();
        initContentView();
    }

    private void initNavigation() {
        LogUtil.print("");
        navigation = styleCallback.createNavigation();
        if (navigation == null) {
            return;
        }
        int navigationId = navigation.getLayoutId();
        View navigationView = LayoutInflater.from(getActivity()).inflate(navigationId, rootView, true);
        navigation.init(navigationView);

        int backId = navigation.getBackId();
        if (backId != 0) {
            getActivity().findViewById(backId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }

        int titleId = navigation.getTitleId();
        String titleText = styleCallback.createTitleText();
        if (titleId != 0 && titleText != null) {
            TextView tvTitle = (TextView) getActivity().findViewById(titleId);
            tvTitle.setText(titleText);
        }

    }

    private void initContentView() {
        LogUtil.print("");
        int contentId = styleCallback.createLayoutId();
        if (contentId == 0) {
            return;
        }
        View contentView = LayoutInflater.from(getActivity()).inflate(contentId, rootView, true);
    }

    public LinearLayout getRootView() {
        return rootView;
    }

    public N getNavigation() {
        return navigation;
    }

    public void setStyleCallback(Callback<N> styleCallback) {
        this.styleCallback = styleCallback;
    }

}
