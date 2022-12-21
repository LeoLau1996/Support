package com.leo.support.biz;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import static com.leo.support.service.NewAccessibilityService.currentActivityClassName;
import static com.leo.support.service.NewAccessibilityService.service;
import static com.leo.support.service.NewAccessibilityService.windowWidth;
import static com.leo.support.utils.OpenAccessibilitySettingHelper.*;
import static com.leo.support.utils.ActionUtils.*;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.leo.support.info.AppInfo;
import com.leo.support.service.NewAccessibilityService;
import com.leo.support.utils.OpenAccessibilitySettingHelper;
import com.surgery.scalpel.util.A2BSupport;
import com.surgery.scalpel.util.Is;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class BossBiz implements OnMatchCallBack {

    public final String TAG = BossBiz.class.getSimpleName();
    public static final List<String> jobList = new ArrayList<>();


    // 包名
    private String packageName;

    public static String sendText = "本人安卓开发工作经验五年，担任安卓技术组长。在硬件，蓝牙，金融，等方面都有丰富的经验。";
    private static final Handler handler = new Handler();
    private boolean clickJob = false;

    public BossBiz(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public void onEvent(AccessibilityNodeInfo nodeInfo, String nodeClassName, String id, String nodeText, int childCount, Rect rect) {
        /**
         * 在哪个APP、在什么时候、什么页面、什么文字
         */
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean match(int type, String text, int count, AccessibilityNodeInfo nodeInfo) {
        if (!Is.isEquals(packageName, AppInfo.PACKAGE.BOSS直聘)) {
            return false;
        }
        Log.e(TAG, String.format("match    type = %s    text = %s    count = %s", type, text, count));
        // 寻找职位
        if (Is.isEquals(currentActivityClassName, AppInfo.ACTIVITY.BOSS.首页)) {
            if (type == EQUALS_ID && Is.isEquals(text, AppInfo.ID.BOSS.首页_职位列表_职位名称)) {
                String jobName = A2BSupport.toString(nodeInfo.getText());
                boolean contains = jobList.contains(jobName);
                Log.e(TAG, String.format("jobName = %s    contains = %s    %s", jobName, contains, new Gson().toJson(jobList)));
                if (!contains) {
                    Log.e(TAG, String.format("点击职位:%s", jobName));
                    jobList.add(jobName);
                    clickJob = true;
                    click(nodeInfo, true);
                    return true;
                }
            }
        }

        // 点击立即沟通
        if (Is.isEquals(currentActivityClassName, AppInfo.ACTIVITY.BOSS.职位详情)) {
            if (type == EQUALS_ID && Is.isEquals(text, AppInfo.ID.BOSS.职位详情_沟通)) {
                if (Is.isEquals(A2BSupport.toString(nodeInfo.getText()), "立即沟通")) {
                    Log.e(TAG, "点击立即沟通");
                    click(nodeInfo, true);
                } else {
                    // 返回
                    Log.e(TAG, "点击返回");
                    clickBack(NewAccessibilityService.service);
                }
                return true;
            }
        }

        // 聊天
        if (Is.isEquals(currentActivityClassName, AppInfo.ACTIVITY.BOSS.聊天)) {
            if (type == EQUALS_TEXT && text.equals("新消息") && Is.isEquals(A2BSupport.toString(nodeInfo.getClassName()), "android.widget.EditText")) {
                // 输入文字
                Log.e(TAG, String.format("输入文字:%s", sendText));
                setText(nodeInfo, sendText + System.currentTimeMillis());
                return true;
            }
            if (type == CONTAINS_TEXT && text.contains(sendText) && Is.isEquals(A2BSupport.toString(nodeInfo.getClassName()), "android.widget.EditText")) {
                Log.e(TAG, "点击发送");
                // 点击发送
                click(NewAccessibilityService.service, 1015, 2288);
                Log.e(TAG, "点击返回");
                // 点击返回
                handler.postDelayed(() -> clickBack(NewAccessibilityService.service), 1000);
                return true;
            }
        }

        // 登陆
        if (Is.isEquals(currentActivityClassName, AppInfo.ACTIVITY.BOSS.登陆)) {
            if (type == EQUALS_TEXT && text.equals("同意")) {
                click(nodeInfo, true);
                return true;
            }
        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void matchEnd() {
        // 寻找职位
        if (Is.isEquals(currentActivityClassName, AppInfo.ACTIVITY.BOSS.首页) && !clickJob) {
            float x = NewAccessibilityService.windowWidth / 2f;
            move(service, x, 1600, x + new Random().nextInt((int) (windowWidth - x)), 1000);
            return;
        }

        // 登陆
        if (Is.isEquals(currentActivityClassName, AppInfo.ACTIVITY.BOSS.登陆)) {
            // 勾选
            Log.e(TAG, "勾选");
            click(NewAccessibilityService.service, 100, 700);
            // 点击下一步
            Log.e(TAG, "登陆");
            click(NewAccessibilityService.service, 500, 800);
            return;
        }
    }

}
