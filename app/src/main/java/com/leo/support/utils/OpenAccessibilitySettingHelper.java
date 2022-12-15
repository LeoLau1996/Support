package com.leo.support.utils;

import static com.leo.support.service.NewAccessibilityService.currentActivityClassName;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.leo.support.biz.OnMatchCallBack;
import com.leo.support.model.MultiText;
import com.leo.support.service.NewAccessibilityService;
import com.surgery.scalpel.util.A2BSupport;
import com.surgery.scalpel.util.Is;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/14
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class OpenAccessibilitySettingHelper {

    private static String TAG = OpenAccessibilitySettingHelper.class.getSimpleName();

    public static final int EQUALS_TEXT = 0;
    public static final int CONTAINS_TEXT = 1;
    public static final int EQUALS_ID = 2;
    public static final int CONTAINS_ID = 3;


    // 判断自定义辅助功能服务是否开启
    public static boolean isAccessibilitySettingsOn(Context context, String className) {
        if (context == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);// 获取正在运行的服务列表
            if (runningServices.size() < 0) {
                return false;
            }
            for (int i = 0; i < runningServices.size(); i++) {
                ComponentName service = runningServices.get(i).service;
                if (service.getClassName().equals(className)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    // 跳转到设置页面无障碍服务开启自定义辅助功能服务
    public static void jumpToSettingPage(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String getEventTypeText(int eventType) {
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED: {
                return String.format("eventType = %s(%s)", eventType, "点击-TYPE_VIEW_CLICKED");
            }
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED: {
                return String.format("eventType = %s(%s)", eventType, "长按-TYPE_VIEW_LONG_CLICKED");
            }
            case AccessibilityEvent.TYPE_VIEW_SELECTED: {
                return String.format("eventType = %s(%s)", eventType, "选中-TYPE_VIEW_SELECTED");
            }
            case AccessibilityEvent.TYPE_VIEW_FOCUSED: {
                return String.format("eventType = %s(%s)", eventType, "获得焦点-TYPE_VIEW_FOCUSED");
            }
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED: {
                return String.format("eventType = %s(%s)", eventType, "文本变化-TYPE_VIEW_TEXT_CHANGED");
            }
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED: {
                return String.format("eventType = %s(%s)", eventType, "窗口状态变化-TYPE_WINDOW_STATE_CHANGED");
            }
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED: {
                return String.format("eventType = %s(%s)", eventType, "通知状态变化-TYPE_NOTIFICATION_STATE_CHANGED");
            }
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER: {
                return String.format("eventType = %s(%s)", eventType, "进入悬停-TYPE_VIEW_HOVER_ENTER");
            }
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT: {
                return String.format("eventType = %s(%s)", eventType, "退出悬停-TYPE_VIEW_HOVER_EXIT");
            }
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START: {
                return String.format("eventType = %s(%s)", eventType, "触摸浏览开始-TYPE_TOUCH_EXPLORATION_GESTURE_START");
            }
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END: {
                return String.format("eventType = %s(%s)", eventType, "触摸浏览结束-TYPE_TOUCH_EXPLORATION_GESTURE_END");
            }
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED: {
                return String.format("eventType = %s(%s)", eventType, "窗口的内容发生变化-TYPE_WINDOW_CONTENT_CHANGED");
            }
            case AccessibilityEvent.TYPE_VIEW_SCROLLED: {
                return String.format("eventType = %s(%s)", eventType, "滚动-TYPE_VIEW_SCROLLED");
            }
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED: {
                return String.format("eventType = %s(%s)", eventType, "文字选中发生改变-TYPE_VIEW_TEXT_SELECTION_CHANGED");
            }
            case AccessibilityEvent.TYPE_ANNOUNCEMENT: {
                return String.format("eventType = %s(%s)", eventType, "应用产生一个通知-TYPE_ANNOUNCEMENT");
            }
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED: {
                return String.format("eventType = %s(%s)", eventType, "获得无障碍焦点-TYPE_VIEW_ACCESSIBILITY_FOCUSED");
            }
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED: {
                return String.format("eventType = %s(%s)", eventType, "无障碍焦点清除-TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED");
            }
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY: {
                return String.format("eventType = %s(%s)", eventType, "在给定的移动粒度下遍历视图文本-TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY");
            }
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START: {
                return String.format("eventType = %s(%s)", eventType, "开始手势监测-TYPE_GESTURE_DETECTION_START");
            }
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END: {
                return String.format("eventType = %s(%s)", eventType, "结束手势监测-TYPE_GESTURE_DETECTION_END");
            }
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START: {
                return String.format("eventType = %s(%s)", eventType, "触摸屏幕开始-TYPE_TOUCH_INTERACTION_START");
            }
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END: {
                return String.format("eventType = %s(%s)", eventType, "触摸屏幕结束-TYPE_TOUCH_INTERACTION_END");
            }
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED: {
                return String.format("eventType = %s(%s)", eventType, "屏幕上的窗口变化-TYPE_WINDOWS_CHANGED");
            }
            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED: {
                return String.format("eventType = %s(%s)", eventType, "View中的上下文点击-TYPE_VIEW_CONTEXT_CLICKED");
            }
            case AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT: {
                return String.format("eventType = %s(%s)", eventType, "辅助用户读取当前屏幕-TYPE_ASSIST_READING_CONTEXT");
            }
            default: {
                return String.format("eventType = %s(%s)", eventType, "未知事件");
            }
        }
    }


    public static void analysisNode(AccessibilityNodeInfo nodeInfo, OnMatchCallBack callBack) {
        analysisNode(nodeInfo, null, null, null, null, callBack);
    }

    // 解析
    public static void analysisNode(AccessibilityNodeInfo nodeInfo, MultiText equalsText, MultiText containsText, MultiText equalsId, MultiText containsId, OnMatchCallBack callBack) {
        analysis(nodeInfo, equalsText, containsText, equalsId, containsId, 0, callBack);
        if (callBack != null) {
            callBack.matchEnd();
        }
    }

    private static void analysis(AccessibilityNodeInfo nodeInfo, MultiText equalsText, MultiText containsText, MultiText equalsId, MultiText containsId, int count, OnMatchCallBack callBack) {
        if (nodeInfo == null) {
            return;
        }
        String nodeClassName = A2BSupport.toString(nodeInfo.getClassName());
        String id = nodeInfo.getViewIdResourceName();
        String nodeText = A2BSupport.toString(nodeInfo.getText());
        int childCount = nodeInfo.getChildCount();
        Rect rect = new Rect();
        nodeInfo.getBoundsInScreen(rect);

        if (callBack != null) {
            callBack.onEvent(nodeInfo, nodeClassName, id, nodeText, childCount, rect);
        }


        // 文字相等
        for (int index = 0; callBack != null && equalsText != null && index < equalsText.texts.length; index++) {
            String content = equalsText.texts[index];
            if (Is.isEquals(nodeText, content)) {
                boolean result = callBack.match(EQUALS_TEXT, content, count++, nodeInfo);
                if (result) {
                    return;
                }
            }
        }

        // 包含文字
        for (int index = 0; callBack != null && containsText != null && index < containsText.texts.length; index++) {
            String content = containsText.texts[index];
            if (content != null && nodeText != null && nodeText.contains(content)) {
                boolean result = callBack.match(CONTAINS_TEXT, content, count++, nodeInfo);
                if (result) {
                    return;
                }
            }
        }

        // ID相等
        for (int index = 0; callBack != null && equalsId != null && index < equalsId.texts.length; index++) {
            String content = equalsId.texts[index];
            if (Is.isEquals(id, content)) {
                boolean result = callBack.match(EQUALS_ID, content, count++, nodeInfo);
                if (result) {
                    return;
                }
            }
        }

        // 包含Id
        for (int index = 0; callBack != null && containsId != null && index < containsId.texts.length; index++) {
            String content = containsId.texts[index];
            if (content != null && id != null && id.contains(content)) {
                boolean result = callBack.match(CONTAINS_ID, content, count++, nodeInfo);
                if (result) {
                    return;
                }
            }
        }

        //if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
        //
        //} else {
        Log.i(TAG, String.format("nodeClassName = %s    id = %s    nodeText = %s    childCount = %s    Rect = %s", nodeClassName, id, nodeText, childCount, rect));
        //}
        for (int index = 0; index < childCount; index++) {
            AccessibilityNodeInfo childNode = nodeInfo.getChild(index);
            analysis(childNode, equalsText, containsText, equalsId, containsId, count, callBack);
        }
    }


}
