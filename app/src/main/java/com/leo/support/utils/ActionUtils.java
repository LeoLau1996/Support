package com.leo.support.utils;

import static com.leo.support.service.NewAccessibilityService.currentActivityClassName;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 动作
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/15
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class ActionUtils {

    private static final String TAG = ActionUtils.class.getSimpleName();

    public static final Map<String, Long> map = new HashMap<>();
    private static final long delayMillis = 1000;
    //private static final Handler handler = new Handler();

    public static boolean canDo(String id) {
        String key = String.format("%s_%s", currentActivityClassName, id);
        Long timeMillis = map.get(key);
        long currentTimeMillis = System.currentTimeMillis();
        boolean result = timeMillis == null || currentTimeMillis - timeMillis >= delayMillis;
        if (result) {
            map.put(key, currentTimeMillis);
        }
        return result;
    }

    public static void remove() {
        map.clear();
        //handler.removeCallbacksAndMessages(null);
    }


    // 点击 递归
    public static void click(AccessibilityNodeInfo nodeInfo, boolean recursive, String id) {
        if (nodeInfo == null) {
            return;
        }
        // 如果当前View不可点击，则点击他的父布局
        if (!nodeInfo.isClickable()) {
            if (recursive) {
                click(nodeInfo.getParent(), true, id);
            }
            return;
        }
        // 延迟点击
        //handler.postDelayed(() -> {
        //if (!canDo(id)) {
        //    return;
        //}
        Log.e(TAG, "点击成功");
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        //}, delayMillis);
    }

    // 点击
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void click(AccessibilityService service, float x, float y, String id) {
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x, y);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return;
        }
        //handler.postDelayed(() -> {
        if (!canDo(id)) {
            return;
        }
        Log.e(TAG, "点击成功");
        // addStroke是模拟的多指触摸，add一次表示一个手指，add多次表示多个手指。
        GestureDescription gesture = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0, 48)).build();
        service.dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {

            // 完成
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Log.e(TAG, "点击完成");
            }

            // 取消
            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                Log.e(TAG, "点击取消");
            }
        }, null);
        //}, delayMillis);
    }

    // 移动
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void move(AccessibilityService service, String id) {
        //发送一个点击事件
        Path path = new Path();//线性的path代表手势路径,点代表按下,封闭的没用
        // 起点
        path.moveTo(1000, 2000);
        // 终点
        path.lineTo(1000, 1400);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return;
        }
        //handler.postDelayed(() -> {
        if (!canDo(id)) {
            return;
        }
        Log.e(TAG, "移动成功");
        // addStroke是模拟的多指触摸，add一次表示一个手指，add多次表示多个手指。
        GestureDescription gesture = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 100, 500)).build();
        service.dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {

            // 完成
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Log.e(TAG, "移动完成");
            }

            // 取消
            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                Log.e(TAG, "移动取消");
            }
        }, null);
        //}, delayMillis);
    }

    // 设置文本
    private static void setText(AccessibilityNodeInfo nodeInfo, String text) {
        //handler.postDelayed(() -> {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
        //}, delayMillis);
    }

    // 返回键
    public static void clickBack(AccessibilityService service, String id) {
        //handler.postDelayed(() -> {
        if (!canDo(id)) {
            return;
        }
        Log.e(TAG, "返回键成功");
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        //}, delayMillis);
    }

    // Home键
    public static void clickHome(AccessibilityService service, String id) {
        //handler.postDelayed(() -> {
        if (!canDo(id)) {
            return;
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
        //}, delayMillis);
    }

    // 导航键
    public static void clickNotifications(AccessibilityService service, String id) {
        //handler.postDelayed(() -> {
        if (!canDo(id)) {
            return;
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
        //}, delayMillis);
    }

    // 拉出通知栏
    public static void recents(AccessibilityService service, String id) {
        //handler.postDelayed(() -> {
        if (!canDo(id)) {
            return;
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        //}, delayMillis);
    }
}
