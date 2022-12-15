package com.leo.support.biz;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

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

    // 包名
    private String packageName;
    // 当前Activity
    private String currentClassName;

    public BossBiz(String packageName, String currentClassName) {
        this.packageName = packageName;
        this.currentClassName = currentClassName;
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
        if (Is.isEquals(currentClassName, AppInfo.ACTIVITY.BOSS.首页) && Is.isEquals(AppInfo.ID.BOSS.首页_职位列表_职位名称)) {

        }


        return false;
    }

    @Override
    public void matchEnd() {

    }

}
