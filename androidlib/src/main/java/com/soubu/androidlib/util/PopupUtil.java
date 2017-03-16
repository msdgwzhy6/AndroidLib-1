package com.soubu.androidlib.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * PopupWindow工具类
 */
public class PopupUtil {

    public interface ShowListener {
        void onShow(View popupView, PopupWindow popupWindow);
    }

    /*居中显示PopupWindow*/
    public static void showInCenter(Activity activity, int resourcesId, ShowListener showListener) {
        if (activity.isFinishing()) {
            return;
        }
        View popupView = LayoutInflater.from(activity).inflate(resourcesId, null, false);
        PopupWindow popupWindow = createPopupWindow(activity, popupView);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        showListener.onShow(popupView, popupWindow);
    }

    /*底部显示PopupWindow*/
    public static void showInBottom(Activity activity, int resourcesId, ShowListener showListener) {
        if (activity.isFinishing()) {
            return;
        }
        View popupView = LayoutInflater.from(activity).inflate(resourcesId, null, false);
        PopupWindow popupWindow = createPopupWindow(activity, popupView);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        showListener.onShow(popupView, popupWindow);
    }

    /*顶部显示PopupWindow*/
    public static void showInTop(Activity activity, View popupView, ShowListener showListener) {
        if (activity.isFinishing()) {
            return;
        }
        PopupWindow popupWindow = createPopupWindow(activity, popupView);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);
        showListener.onShow(popupView, popupWindow);
    }

    /*下方显示PopupWindow*/
    public static void showInDrop(Activity activity, int resourcesId, View anchor, ShowListener showListener) {
        if (activity.isFinishing()) {
            return;
        }
        View popupView = LayoutInflater.from(activity).inflate(resourcesId, null, false);
        PopupWindow popupWindow = createPopupWindow(activity, popupView);
        popupWindow.showAsDropDown(anchor);
        showListener.onShow(popupView, popupWindow);
    }

    /*下方显示PopupWindow,有偏移值*/
    public static void showInDrop(Activity activity, int resourcesId, View anchor, int x, int y, ShowListener showListener) {
        if (activity.isFinishing()) {
            return;
        }
        View popupView = LayoutInflater.from(activity).inflate(resourcesId, null, false);
        PopupWindow popupWindow = createPopupWindow(activity, popupView);
        popupWindow.showAsDropDown(anchor, x, y);
        showListener.onShow(popupView, popupWindow);
    }

    /*下方显示PopupWindow,有偏移值*/
    public static void showInDropWhite(Activity activity, int resourcesId, View anchor, int x, int y, ShowListener showListener) {
        if (activity.isFinishing()) {
            return;
        }
        View popupView = LayoutInflater.from(activity).inflate(resourcesId, null, false);
        PopupWindow popupWindow = createPopupWindow(activity, popupView);
        popupWindow.showAsDropDown(anchor, x, y);
        showListener.onShow(popupView, popupWindow);
    }

    /*创建PopupWindow*/
    public static PopupWindow createPopupWindow(Activity activity, View popupView) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        PopupWindow popupWindow = new PopupWindow(popupView, display.getWidth(), display.getHeight(), true);

        final Window window = activity.getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 0.7f;
        window.setAttributes(params);

//        window.setBackgroundDrawable(new ColorDrawable(0x4dffffff));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = 1f;
                window.setAttributes(params);
//                window.setBackgroundDrawable(new ColorDrawable(0xfff3f3f3));
            }
        });

        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return popupWindow;

    }

}
