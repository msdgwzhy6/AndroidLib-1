package com.soubu.sample;

import android.widget.Toast;

/**
 * 作者：余天然 on 2017/3/15 下午1:33
 */
public class ToastHelper {

    public static void showShort(String msg) {
        Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
