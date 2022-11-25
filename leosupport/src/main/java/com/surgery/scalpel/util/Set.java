package com.surgery.scalpel.util;

import static com.surgery.scalpel.util.Get.getStatusBarHeight;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/18
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class Set {

    //设置沉浸式状态栏
    public static void setAppStatusBar(Activity activity, int color) {
        //设置 paddingTop
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(activity.getResources().getColor(color));
            decorView.addView(statusBarView, lp);
        }
    }

    //设置控件宽高
    public static void setParams(View view, int width, int height) {
        //调整
        ViewGroup.LayoutParams linearParams = view.getLayoutParams();
        if (width != -1) {
            linearParams.width = width;
        }
        if (height != -1) {
            linearParams.height = height;
        }
        view.setLayoutParams(linearParams);
    }

    // 设置外边距
    public static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) layoutParams).setMargins(left, top, right, bottom);
        }
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) layoutParams).setMargins(left, top, right, bottom);
        }
        //
        view.setLayoutParams(layoutParams);
    }
}
