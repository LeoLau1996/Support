package com.surgery.scalpel.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 常用的工具类
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/31
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonUtil {

    //复制
    public static void copy(Activity activity, String content) {
        copy(activity, "Label", content);
    }

    //复制
    public static void copy(Activity activity, String label, String content) {
        //获取剪贴板管理器：
        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) {
            return;
        }
        // 将ClipData内容放到系统剪贴板里。
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, content));
    }

    //显示虚拟键盘
    public static void showKeyboard(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    //隐藏虚拟键盘(只适用于Activity，不适用于Fragment)
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            return;
        }
        hideKeyboard(activity, view);
    }

    //隐藏虚拟键盘
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //隐藏虚拟键盘(只适用于Dialog)
    public static void hideKeyboard(Dialog dialog) {
        View view = dialog.getCurrentFocus();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) dialog.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //隐藏软键盘(可用于Activity，Fragment)
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        for (View view : viewList) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
