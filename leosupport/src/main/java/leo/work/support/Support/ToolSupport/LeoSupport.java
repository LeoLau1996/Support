package leo.work.support.Support.ToolSupport;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import leo.work.support.Base.Util.BaseUtil;
import leo.work.support.Info.RefreshInfo;
import leo.work.support.R;
import leo.work.support.Support.Common.Get;
import leo.work.support.Support.Common.Talk;
import leo.work.support.Support.Thread.ThreadSupport;

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
    public static int bgColor;
    public static int backIcon;
    public static int titleColor;
    public static int menuTextColor;

    public static void initTopBar() {
        initTopBar(0, 0, 0, 0);
    }

    public static void initTopBar(int mBgColor, int mBackIcon, int mTitleColor, int mMenuTextColor) {
        bgColor = mBgColor == 0 ? R.color.bule : mBgColor;
        backIcon = mBackIcon == 0 ? R.drawable.icon_back : mBackIcon;
        titleColor = mTitleColor == 0 ? R.color.white : mTitleColor;
        menuTextColor = menuTextColor == 0 ? R.color.textBlack : mMenuTextColor;
    }


    /**
     * 适用于正常的刷新数据
     *
     * @param nowData        原来的数据
     * @param newData        新增的数据
     * @param isSaveLastData 是否保存原来的数据
     * @param <T>
     */
    public static <T> void setList(List<T> nowData, List<T> newData, boolean isSaveLastData) {
        if (!isSaveLastData) {
            nowData.clear();
        }
        nowData.addAll(newData);
    }

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
            Talk.talkShort("打开网页失败，请检查网络链接是否正确。");
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


}
