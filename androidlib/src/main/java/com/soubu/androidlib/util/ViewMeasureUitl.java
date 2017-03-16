package com.soubu.androidlib.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * 与View有关的计算工具
 * Created by dingsigang on 16-8-2.
 */
public class ViewMeasureUitl {
    /**
     * 在onCreate()即可强行获取View的尺寸
     */
    public static int[] forceGetViewSize(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    /**
     * ListView中提前测量View尺寸
     * 如headerView，用的时候拷贝到ListView中
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = View.MeasureSpec.makeMeasureSpec(tempHeight,
                    View.MeasureSpec.EXACTLY);
        } else {
            height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }
}
