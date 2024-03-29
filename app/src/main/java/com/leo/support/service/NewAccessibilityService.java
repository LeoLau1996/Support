package com.leo.support.service;


import static com.leo.support.utils.OpenAccessibilitySettingHelper.*;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.gson.Gson;
import com.leo.support.biz.BossBiz;
import com.leo.support.biz.OnMatchCallBack;
import com.leo.support.info.AppInfo;
import com.leo.support.model.AccessibillityEvent;
import com.leo.support.model.MultiText;
import com.leo.support.utils.ActionUtils;
import com.surgery.scalpel.util.A2BSupport;
import com.surgery.scalpel.util.Get;
import com.surgery.scalpel.util.Is;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    public static String currentActivityClassName;
    // xxx
    public static AccessibilityService service;
    public static int windowWidth, windowHeight;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "onServiceConnected");
        service = this;
        EventBus.getDefault().register(this);
        windowWidth = Get.getWindowWidth(this);
        windowHeight = Get.getWindowHeight(this);
    }

    // 事件回调
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        String packageName = A2BSupport.toString(event.getPackageName());
        String className = A2BSupport.toString(event.getClassName());
        List<CharSequence> textList = event.getText();
        String nodeInfoPackageName = null;
        // 资源信息
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            nodeInfoPackageName = A2BSupport.toString(nodeInfo.getPackageName());
        }
        //Log.e(TAG, "nodeInfoPackageName:" + nodeInfoPackageName);
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !Is.isEquals(currentActivityClassName, className) && nodeInfoPackageName != null && className.contains(nodeInfoPackageName)) {
            currentActivityClassName = className;
            Log.e(TAG, String.format("currentActivityClassName = %s", currentActivityClassName));
        }
        //if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
        //    // Log.i(TAG, String.format("onAccessibilityEvent    %s\npackageName = %s    className = %s\ntextList = %s", getEventTypeText(eventType), packageName, className, new Gson().toJson(textList)));
        //} else {
        Log.e(TAG, String.format("onAccessibilityEvent    %s\npackageName = %s    className = %s\ntextList = %s", getEventTypeText(eventType), packageName, className, new Gson().toJson(textList)));
        //}


        //analysisNode(nodeInfo);
        //// 点击测试1
        //if (Is.isEquals(packageName, "com.leo.support")) {
        //    analysisNode(nodeInfo, new MultiText("你点我一下试试"), null, null, new MultiText("btnTest"), 0, (type, text, count, nodeInfo1) -> {
        //        click(nodeInfo1, true);
        //        return false;
        //    });
        //}

    }

    // feedback 被打断
    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt    feedback 被打断");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        EventBus.getDefault().unregister(this);
        service = null;
        return super.onUnbind(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAccessibillityEvent(AccessibillityEvent event) {
        // 资源信息
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            Log.e(TAG, "nodeInfo == null");
            return;
        }
        String packageName = A2BSupport.toString(nodeInfo.getPackageName());
        Log.e(TAG, String.format("packageName = %s    currentActivityClassName = %s", packageName, currentActivityClassName));

        //analysisNode(nodeInfo, new MultiText(), new MultiText(), new MultiText("com.leo.support:id/btnTest"), new MultiText(), new OnMatchCallBack() {
        //    @Override
        //    public void onEvent(AccessibilityNodeInfo nodeInfo, String nodeClassName, String id, String nodeText, int childCount, Rect rect) {
        //
        //    }
        //
        //    @Override
        //    public boolean match(int type, String text, int count, AccessibilityNodeInfo nodeInfo) {
        //        ActionUtils.click(nodeInfo, true, text);
        //        return false;
        //    }
        //
        //    @Override
        //    public void matchEnd() {
        //
        //    }
        //});

        // 解析节点
        analysisNode(nodeInfo,
                new MultiText("新消息", "同意"), new MultiText(BossBiz.sendText),
                new MultiText(AppInfo.ID.BOSS.首页_职位列表_职位名称, AppInfo.ID.BOSS.首页_职位列表_价格, AppInfo.ID.BOSS.职位详情_沟通), new MultiText(),
                new BossBiz(packageName));
    }

}
