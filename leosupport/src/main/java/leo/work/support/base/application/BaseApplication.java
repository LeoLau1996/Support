package leo.work.support.base.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;


import leo.work.support.R;
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
    public static boolean isDebug;
    private static ToastLayoutModel toastLayoutModel;


    @Override
    public void onCreate() {
        super.onCreate();
        if (!Get.getCurrentProcessName().equals(getPackageName())) {
            return;
        }
        isDebug = Is.isDebuggable();
        application = this;
        toastLayoutModel = setToastLayoutModel();
        init();
    }

    private ToastLayoutModel setToastLayoutModel() {
        return new ToastLayoutModel(R.layout.include_toast, 0.58f);
    }


    public static ToastLayoutModel getToastLayoutModel() {
        return toastLayoutModel;
    }

    protected abstract void init();

    public static BaseApplication getApplication() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    public class ToastLayoutModel {
        private int layout;
        private float y;

        public ToastLayoutModel(int layout, float y) {
            this.layout = layout;
            this.y = y;
        }

        public int getLayout() {
            return layout;
        }

        public void setLayout(int layout) {
            this.layout = layout;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
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
