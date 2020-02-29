package leo.work.support.Support.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

import leo.work.support.Base.Util.BaseUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/16
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class Has extends BaseUtil {
    /**
     * 检测文件是否存在
     *
     * @param strFile 文件路径+文件名
     * @return
     */
    public static boolean hasFile(String strFile) {
        boolean b = true;
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                b = false;
            }

        } catch (Exception e) {
            b = false;
        }

        return b;
    }


    //是否有网络
    public static boolean hasNetwork() {
        return hasNetwork(true);
    }


    //是否有网络
    public static boolean hasNetwork(boolean hasShowToast) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean b = activeNetworkInfo != null && activeNetworkInfo.isAvailable();
        if (!b && hasShowToast) {
            Talk.talkShort("网络连接尚未打开");
        }
        return b;
    }

}
