package com.leo.support.service;


import static com.leo.support.utils.OpenAccessibilitySettingHelper.*;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.gson.Gson;
import com.leo.support.biz.BossBiz;
import com.leo.support.info.AppInfo;
import com.leo.support.model.MultiText;
import com.leo.support.utils.OpenAccessibilitySettingHelper;
import com.leo.support.utils.OpenAccessibilitySettingHelper.*;
import com.surgery.scalpel.util.A2BSupport;
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
    // 当前ClassName
    private String currentClassName;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "onServiceConnected");
    }

    // 事件回调
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        String packageName = A2BSupport.toString(event.getPackageName());
        String className = A2BSupport.toString(event.getClassName());
        List<CharSequence> textList = event.getText();
        // 资源信息
        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo == null) {
            nodeInfo = getRootInActiveWindow();
        }
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            currentClassName = className;
        }
        if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            // Log.i(TAG, String.format("onAccessibilityEvent    %s\npackageName = %s    className = %s\ntextList = %s", getEventTypeText(eventType), packageName, className, new Gson().toJson(textList)));
        } else {
            Log.e(TAG, String.format("onAccessibilityEvent    %s\npackageName = %s    className = %s\ntextList = %s", getEventTypeText(eventType), packageName, className, new Gson().toJson(textList)));
        }


        //analysisNode(nodeInfo);
        //// 点击测试1
        //if (Is.isEquals(packageName, "com.leo.support")) {
        //    analysisNode(nodeInfo, new MultiText("你点我一下试试"), null, null, new MultiText("btnTest"), 0, (type, text, count, nodeInfo1) -> {
        //        click(nodeInfo1, true);
        //        return false;
        //    });
        //}


        // 解析节点
        analysisNode(eventType, nodeInfo,
                new MultiText(), new MultiText(),
                new MultiText(AppInfo.ID.BOSS.首页_职位列表_职位名称, AppInfo.ID.BOSS.首页_职位列表_价格Id), new MultiText(),
                new BossBiz(packageName, currentClassName));

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

}
