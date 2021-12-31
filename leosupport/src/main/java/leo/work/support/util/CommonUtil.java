package leo.work.support.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 常用的工具类
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/31
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonUtil {

    //复制
    public static void copy(Activity activity, String content) {
        copy(activity, "Label", content);
    }

    //复制
    public static void copy(Activity activity, String label, String content) {
        //获取剪贴板管理器：
        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) {
            return;
        }
        // 将ClipData内容放到系统剪贴板里。
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, content));
    }

}
