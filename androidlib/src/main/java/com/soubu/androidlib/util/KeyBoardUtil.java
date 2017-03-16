package com.soubu.androidlib.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * 作者：余天然 on 16/4/7 上午10:56
 * 邮箱：yutianran1993@163.com
 * 博客：http://blog.csdn.net/fisher0113
 * Github：https://github.com/Fishyer
 * 座右铭:知识来自积累,经验源于总结
 */
public class KeyBoardUtil {
    /**
     * 打卡软键盘
     */
    public static void openKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(activity.getWindow().getDecorView(), InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }
}
