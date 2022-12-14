package com.leo.support.app;

import android.util.Log;

import com.auto.apt_processor.LayoutViewsGroupConfig;
import com.auto.apt_processor.Utils;
import com.leo.support.BuildConfig;
import com.leo.support.R;

import com.surgery.scalpel.base.application.BaseApplication;
import com.surgery.scalpel.util.A2BSupport;
import com.surgery.scalpel.widget.bar.TitleBarDefaultInfo;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/17
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
@LayoutViewsGroupConfig(packageName = BuildConfig.APPLICATION_ID, layoutPaths = {
        "/Users/leolau/Documents/LeoWork/Support/app/src/main/res/layout"
})
public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        TitleBarDefaultInfo.setTitleBarDefaultInfo(new TitleBarDefaultInfo(A2BSupport.dp2px(14), R.mipmap.ic_back, R.color.white, R.color.color_01c4b6, A2BSupport.dp2px(44), R.color.color_01c4b6));
    }

    // 获取根目录
    private String getRootPath() {
        URL xmlpath = this.getClass().getClassLoader().getResource("selected.txt");
        Log.e("liu1213", "xmlpath = " + xmlpath);

        try {
            File directory = new File("");//参数为空
            String courseFile = directory.getCanonicalPath();
            Log.e("liu1213", "courseFile = " + courseFile);


        } catch (IOException e) {
            Log.e("liu1213", "IOException = " + e.getMessage());
        }

        URL url = getClass().getResource("/");
        if (url == null) {
            return "???";
        }
        try {
            /**
             * MacOS示例值：file:/Users/leolau/Documents/LeoWork/AptDemo/apt-processor/build/libs/apt-processor.jar!/com/auto/apt_processor/
             */
            String path = url.getFile();
            Log.e("liu1213", "getRootPath    getFile = " + path);
            // 去除前段
            path = path.substring(path.indexOf("file:") + 5);
            // 文件夹后退
            path = new File(path, "../../../../../../../").getCanonicalPath();
            return path;
        } catch (IOException e) {
            Log.e("liu1213", "getLayoutPath    IOException:" + e.getMessage());
        }
        return "";
    }
}
