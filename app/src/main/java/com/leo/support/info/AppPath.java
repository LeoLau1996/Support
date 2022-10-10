package com.leo.support.info;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import com.leo.support.app.MyApp;


import java.io.File;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/9/9
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class AppPath {

    /**
     * 获取APP缓存地址
     *
     * @return path
     */
    public static String getAppCache() {
        String path;
        path = MyApp.getContext().getExternalFilesDir(DIRECTORY_DOWNLOADS).getAbsolutePath() + "/case/";
        return isExistDir(path);
    }

    //检查文件夹
    private static String isExistDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

}
