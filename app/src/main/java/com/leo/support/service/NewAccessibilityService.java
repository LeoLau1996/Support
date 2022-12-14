package com.leo.support.service;


import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.gson.Gson;
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
        analysisNode(nodeInfo);


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
    public static void analysisNode(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        CharSequence nodeClassName = nodeInfo.getClassName();
        String id = nodeInfo.getViewIdResourceName();
        CharSequence nodeText = nodeInfo.getText();
        int childCount = nodeInfo.getChildCount();
        if (nodeText != null && nodeText.toString().equals("你点我一下试试")) {
            click(nodeInfo);
        }
        Log.i(TAG, String.format("nodeClassName = %s    id = %s    nodeText = %s    childCount = %s", nodeClassName, id, nodeText, childCount));
        for (int index = 0; index < childCount; index++) {
            AccessibilityNodeInfo childNode = nodeInfo.getChild(index);
            analysisNode(childNode);
        }
    }

    // 点击
    private static void click(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

}
