package leo.work.support.support.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Looper;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leo.work.support.base.util.BaseUtil;

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
    //验证座机
    public static boolean isPhone2(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean isPhone = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            isPhone = m.matches();
        } else {
            m = p2.matcher(str);
            isPhone = m.matches();
        }
        return isPhone;
    }

    //验证手机
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    /**
     * 判断手机是否安装某个应用
     *
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装
     */
    public static boolean isAnstallApp(String appPackageName) {
        PackageManager packageManager = getContext().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
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

    /**
     * 判断是否邮箱
     */
    public static boolean isEmail(String strObj) {
        if (isEmpty(strObj)) {
            return false;
        }
        String str = strObj + "";
        if (!str.endsWith(".com")) {
            return false;
        }
        String match = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
