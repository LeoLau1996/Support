package leo.work.support.support.toolSupport;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import java.io.File;

import leo.work.support.util.BaseUtil;

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
