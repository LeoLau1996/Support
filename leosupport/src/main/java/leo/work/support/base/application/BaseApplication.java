package leo.work.support.base.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;


import leo.work.support.support.common.Get;
import leo.work.support.support.common.Is;

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
        init();
    }

    protected abstract void init();

    public static BaseApplication getApplication() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
    }
}
