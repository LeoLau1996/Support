package com.surgery.scalpel.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:是否
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/16
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class Is extends BaseUtil {

    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    public static boolean isEquals(String... texts) {
        if (texts.length < 2) {
            return false;
        }
        for (String text : texts) {
            if (isEmpty(text)) {
                return false;
            }
        }
        String firstText = texts[0];
        for (int i = 1; i < texts.length; i++) {
            if (!firstText.equals(texts[i])) {
                return false;
            }
        }
        return true;
    }

    // 是否数组越界
    public static boolean isIndexOutOf(List list, int position) {
        // 如果数组是空的
        if (list == null || list.isEmpty()) {
            return true;
        }
        if (position < 0) {
            return true;
        }
        if (position >= list.size()) {
            return true;
        }
        return false;
    }

    // 判断是否安装某个应用
    public static boolean isInstallApp(String pkgName) {
        if (Is.isEmpty(pkgName)) {
            return false;
        }
        try {
            getContext().getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception x) {
            return false;
        }
        return true;
    }

    //是否在主线程
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    //是否为Debug
    public static boolean isDebuggable() {
        boolean debuggable = false;
        PackageManager pm = getContext().getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(getContext().getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /*debuggable variable will remain false*/
        }
        return debuggable;
    }

    /**
     * 判断某个Activity 界面是否在前台
     *
     * @param className 某个界面名称
     * @return
     */
    public static boolean isForeground(String className) {
        if (getContext() == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断软键盘是否显示方法
     *
     * @param activity
     * @return
     */
    public static boolean isSoftShowing(Activity activity) {
        //获取当屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom + Get.getSoftButtonsBarHeight(activity);
    }

    //该是否为平板
    public static boolean isPad() {
        return (getContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) {
        // 下载位置
        File downloadFile = new File(saveDir);

        if (!downloadFile.exists()) {
            boolean b = downloadFile.mkdirs();
        }

        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    //服务是否还活着
    public static boolean isServiceRunning(String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否是网络链接
     */
    public static boolean isURL(String url) {
        if (isEmpty(url)) {
            return false;
        }
        if (url.startsWith("http")) {
            return true;
        }
        String regular = "(http|https)://[\\S]*";
        return Pattern.matches(regular, url);
    }

    /**
     * 检测文件链接
     */
    public static boolean isFileURL(String url) {
        if (isEmpty(url)) {
            return false;
        }
        String regular = "(file)://[\\S]*";
        return Pattern.matches(regular, url);
    }

    //
    public static boolean isShouldHideInput(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] leftTop = {0, 0};
            view.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                //保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 判断是否为整数
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
