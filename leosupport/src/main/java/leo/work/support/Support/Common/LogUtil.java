package leo.work.support.Support.Common;

import android.util.Log;

import leo.work.support.Base.Application.BaseApplication;

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

    public static void e(String detail) {
        if (!BaseApplication.isDebug) {
            return;
        }
        print(TAG, detail);
    }

    public static void e(String tag, String detail) {
        if (!BaseApplication.isDebug) {
            return;
        }
        print(tag, detail);
    }

    private static void print(String tag, String result) {
        if (!BaseApplication.isDebug) {
            return;
        }

        if (result.length() > 4000) {
            int chunkCount = result.length() / 4000;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= result.length()) {
                    Log.e(tag, "chunk " + i + " of " + chunkCount + ":" + result.substring(4000 * i));
                } else {
                    Log.e(tag, "chunk " + i + " of " + chunkCount + ":" + result.substring(4000 * i, max));
                }
            }
        } else {
            Log.e(tag, result);
        }
    }
}
