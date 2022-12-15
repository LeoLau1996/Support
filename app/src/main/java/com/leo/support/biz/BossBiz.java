package com.leo.support.biz;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import static com.leo.support.service.NewAccessibilityService.currentActivityClassName;
import static com.leo.support.utils.OpenAccessibilitySettingHelper.*;
import static com.leo.support.utils.ActionUtils.*;

import com.leo.support.info.AppInfo;
import com.leo.support.utils.OpenAccessibilitySettingHelper;
import com.surgery.scalpel.util.Is;

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

    // xxx
    private AccessibilityService service;
    // 包名
    private String packageName;


    public BossBiz(AccessibilityService service, String packageName) {
        this.service = service;
        this.packageName = packageName;
    }

    @Override
    public void onEvent(AccessibilityNodeInfo nodeInfo, String nodeClassName, String id, String nodeText, int childCount, Rect rect) {
        /**
         * 在哪个APP、在什么时候、什么页面、什么文字
         */

        if (!Is.isEquals(packageName, AppInfo.PACKAGE.BOSS直聘)) {
            return;
        }


    }

    @Override
    public boolean match(int type, String text, int count, AccessibilityNodeInfo nodeInfo) {
        if (!Is.isEquals(packageName, AppInfo.PACKAGE.BOSS直聘)) {
            return false;
        }
        // 寻找职位
        if (Is.isEquals(currentActivityClassName, AppInfo.ACTIVITY.BOSS.首页)) {
            if (type == EQUALS_ID && Is.isEquals(text, AppInfo.ID.BOSS.首页_职位列表_职位名称)) {
                Log.e(TAG, String.format("点击职位:%s", text));
                click(nodeInfo, true, text);
                return true;
            }
        }

        // 点击立即沟通
        if (Is.isEquals(currentActivityClassName, AppInfo.ACTIVITY.BOSS.职位详情)) {
            if (type == EQUALS_ID && Is.isEquals(text, AppInfo.ID.BOSS.职位详情_沟通)) {
                if (Is.isEquals(text, "立即沟通")) {
                    Log.e(TAG, String.format("点击立即沟通:%s", text));
                    click(nodeInfo, true, text);
                } else {
                    // 返回
                    Log.e(TAG, "点击返回");
                    clickBack(service, text);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void matchEnd() {
        service = null;
    }

}
