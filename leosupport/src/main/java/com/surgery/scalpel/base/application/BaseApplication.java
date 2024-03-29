package com.surgery.scalpel.base.application;

import android.app.Application;
import android.content.Context;


import com.surgery.scalpel.util.Get;

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
public abstract class BaseApplication extends Application {

    public static BaseApplication application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!Get.getCurrentProcessName().equals(getPackageName())) {
            return;
        }
        application = this;
    }

    public static BaseApplication getApplication() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

}
