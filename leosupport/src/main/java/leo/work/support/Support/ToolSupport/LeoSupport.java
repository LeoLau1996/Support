package leo.work.support.Support.ToolSupport;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import java.io.File;
import java.io.IOException;

import leo.work.support.Base.Util.BaseUtil;

import static leo.work.support.Support.Common.Get.getStatusBarHeight;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:一般支持
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/8
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class LeoSupport extends BaseUtil {

    //设置沉浸式状态栏
    public static void setAppStatusBar(Activity activity, int color) {
        //设置 paddingTop
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(activity.getResources().getColor(color));
            decorView.addView(statusBarView, lp);
        }
    }

    //改变顶部状态栏字体颜色
    public static void changeStatusBarTextColor(Activity activity, boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(isBlack ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_VISIBLE);//设置状态栏黑色字体
        }
    }

    //隐藏虚拟键盘
    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            v.clearFocus();
        }
    }

    //显示虚拟键盘
    public static void showKeyboard(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    public static void openURL(Activity activity, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
        } catch (Exception e) {

        }
    }

    //设置控件宽高
    public static void setParams(View view, int width, int height) {
        //调整
        ViewGroup.LayoutParams linearParams = view.getLayoutParams();
        if (width != -1) {
            linearParams.width = width;
        }
        if (height != -1) {
            linearParams.height = height;
        }
        view.setLayoutParams(linearParams);
    }

    /**
     * 提升读写权限
     *
     * @param filePath 文件路径
     *                 这里需要注意一点
     *                 在下载完成后需要提升一下文件的读写权限
     *                 否则在安装的时候会出现apk解析失败的页面,就是别人访问不了我们的apk文件
     *                 chmod 是Linux下设置文件权限的命令,后面的三个数字每一个代表不同的用户组
     *                 权限分为三种:读(r=4),写(w=2),执行(x=1)
     *                 那么这三种权限就可以组成7种不同的权限,分别用1-7这几个数字代表,例如7 = 4 + 2 + 1,那么就代表该组用户拥有可读,可写,可执行的权限;5 = 4 + 1,就代表可读可执行权限
     *                 而三位数字就带包,该登陆用户,它所在的组,以及其他人
     */
    public static void setPermission(String filePath) {
        try {
            String command = "chmod " + "777" + " " + filePath;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //安装APK
    public static void installNormal(Context context,String authority, String apkPath) throws Exception {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//由于没有在Activity环境下启动Activity,设置下面的标签
            Uri apkUri = FileProvider.getUriForFile(context, authority, file);//参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    //复制
    public static void copy(Activity activity, String txt) {
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(txt);
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param blackStatusBarText 状态栏系统字体图标是否为黑色
     * @url https://blog.csdn.net/brian512/article/details/52096445
     */

    public static void fullScreen(Activity activity, boolean blackStatusBarText) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //在6.0增加了View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR，这个字段就是把状态栏标记为浅色，然后状态栏的字体颜色自动转换为深色
            if (blackStatusBarText) {
                option = option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //导航栏颜色也可以正常设置
            //window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            attributes.flags |= flagTranslucentStatus;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
    }
}
