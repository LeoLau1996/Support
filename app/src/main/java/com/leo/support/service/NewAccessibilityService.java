package com.leo.support.service;


import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.gson.Gson;
import com.leo.support.model.MultiText;
import com.surgery.scalpel.util.Is;

import java.util.List;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 无障碍功能
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/14
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * 官方文档：https://developer.android.google.cn/reference/kotlin/android/accessibilityservice/AccessibilityService#event-types
 * 辅助功能AccessibilityService笔记：https://www.jianshu.com/p/ef01ce654302
 * ---------------------------------------------------------------------------------------------
 **/
public class NewAccessibilityService extends AccessibilityService {

    private static String TAG = NewAccessibilityService.class.getSimpleName();

    public static final int EQUALS_TEXT = 0;
    public static final int CONTAINS_TEXT = 1;
    public static final int EQUALS_ID = 2;
    public static final int CONTAINS_ID = 3;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "onServiceConnected");
    }

    // 事件回调
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        CharSequence packageName = event.getPackageName();
        CharSequence className = event.getClassName();
        List<CharSequence> textList = event.getText();

        // 资源信息
        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo == null) {
            nodeInfo = getRootInActiveWindow();
        }
        if (packageName != null && packageName.toString().equals("com.leo.support")) {
            analysisNode(nodeInfo, new MultiText("你点我一下试试"), null, null, new MultiText("btnTest"), 0, (type, text, count, nodeInfo1) -> {
                click(nodeInfo1);
                return false;
            });
        }


        Object contentDescription = null;
        Object contentChangeTypes = null;
        Log.e(TAG, String.format("onAccessibilityEvent    %s\npackageName = %s    className = %s\ntextList = %s",
                getEventTypeText(eventType),
                packageName, className,
                new Gson().toJson(textList)
        ));
    }

    // feedback 被打断
    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt    feedback 被打断");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }


    /**
     * 工具方法
     */

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

    // 解析
    public static void analysisNode(AccessibilityNodeInfo nodeInfo, MultiText equalsText, MultiText containsText, MultiText equalsId, MultiText containsId, int count, OnMatchCallBack callBack) {
        if (nodeInfo == null) {
            return;
        }
        CharSequence nodeClassName = nodeInfo.getClassName();
        String id = nodeInfo.getViewIdResourceName();
        CharSequence nodeText = nodeInfo.getText();
        int childCount = nodeInfo.getChildCount();

        // 文字相等
        for (int index = 0; equalsText != null && index < equalsText.texts.length; index++) {
            String content = equalsText.texts[index];
            if (content != null && nodeText != null && nodeText.toString().equals(content)) {
                boolean result = callBack.match(EQUALS_TEXT, content, count++, nodeInfo);
                if (result) {
                    return;
                }
            }
        }

        // 包含文字
        for (int index = 0; containsText != null && index < containsText.texts.length; index++) {
            String content = containsText.texts[index];
            if (content != null && nodeText != null && nodeText.toString().contains(content)) {
                boolean result = callBack.match(CONTAINS_TEXT, content, count++, nodeInfo);
                if (result) {
                    return;
                }
            }
        }

        // ID相等
        for (int index = 0; equalsId != null && index < equalsId.texts.length; index++) {
            String content = equalsId.texts[index];
            if (content != null && Is.isEquals(id, content)) {
                boolean result = callBack.match(EQUALS_ID, content, count++, nodeInfo);
                if (result) {
                    return;
                }
            }
        }

        // 包含Id
        for (int index = 0; containsId != null && index < containsId.texts.length; index++) {
            String content = containsId.texts[index];
            if (content != null && id != null && id.contains(content)) {
                boolean result = callBack.match(CONTAINS_ID, content, count++, nodeInfo);
                if (result) {
                    return;
                }
            }
        }

        Log.i(TAG, String.format("nodeClassName = %s    id = %s    nodeText = %s    childCount = %s", nodeClassName, id, nodeText, childCount));
        for (int index = 0; index < childCount; index++) {
            AccessibilityNodeInfo childNode = nodeInfo.getChild(index);
            analysisNode(childNode, equalsText, containsText, equalsId, containsId, count, callBack);
        }
    }

    // 匹配
    public interface OnMatchCallBack {

        boolean match(int type, String text, int count, AccessibilityNodeInfo nodeInfo);

    }

    // 点击
    private static void click(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    // 设置文本
    private static void setText(AccessibilityNodeInfo nodeInfo, String text) {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
        }
    }

    // 返回键
    public void clickBack() {
        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    // Home键
    private void clickHome() {
        performGlobalAction(GLOBAL_ACTION_HOME);
    }

    // 导航键
    private void clickNotifications() {
        performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
    }

    // 拉出通知栏
    private void recents() {
        performGlobalAction(GLOBAL_ACTION_RECENTS);
    }
}
