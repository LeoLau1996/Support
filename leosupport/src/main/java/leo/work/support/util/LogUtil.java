package leo.work.support.util;

import android.util.Log;

import leo.work.support.util.Is;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-09-06
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class LogUtil {

    private static final String TAG = "leowork";

    private static Boolean isDebug = null;

    public static void e(String detail) {
        e(TAG, detail);
    }

    public static void e(String tag, String text) {
        if (isDebug == null) {
            isDebug = Is.isDebuggable();
        }
        if (!isDebug) {
            return;
        }

        if (text.length() > 4000) {
            int chunkCount = text.length() / 4000;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= text.length()) {
                    Log.e(tag, "chunk " + i + " of " + chunkCount + ":" + text.substring(4000 * i));
                } else {
                    Log.e(tag, "chunk " + i + " of " + chunkCount + ":" + text.substring(4000 * i, max));
                }
            }
        } else {
            Log.e(tag, text);
        }
    }

}
