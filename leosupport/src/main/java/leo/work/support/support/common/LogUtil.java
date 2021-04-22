package leo.work.support.support.common;

import android.util.Log;

import leo.work.support.base.application.BaseApplication;

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
    private static StringBuffer stringBuffer;

    public static void e(String detail) {
        e(TAG, detail);
    }

    public static void e(String tag, String text) {
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        stringBuffer.append(DateSupport.toString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        stringBuffer.append(":");
        stringBuffer.append(text);
        stringBuffer.append("\n");
        if (!BaseApplication.isDebug) {
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

    public static String getLog() {
        return stringBuffer != null ? stringBuffer.toString() : "";
    }
}
