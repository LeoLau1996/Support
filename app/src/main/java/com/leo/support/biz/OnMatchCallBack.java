package com.leo.support.biz;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/15
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public interface OnMatchCallBack {

    void onEvent(AccessibilityNodeInfo nodeInfo, String nodeClassName, String id, String nodeText, int childCount, Rect rect);

    boolean match(int type, String text, int count, AccessibilityNodeInfo nodeInfo);

    void matchEnd();

}
