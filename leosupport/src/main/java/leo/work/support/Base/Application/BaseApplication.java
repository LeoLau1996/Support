package leo.work.support.Base.Application;

import android.app.Application;
import android.content.Context;


import leo.work.support.R;
import leo.work.support.Support.Common.Get;
import leo.work.support.Support.Common.Is;
import leo.work.support.Support.ToolSupport.LeoSupport;

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
    public static ApplicationInfo applicationInfo = null;
    public static boolean isDebug;
    private static ToastLayoutModel toastLayoutModel;


    @Override
    public void onCreate() {
        super.onCreate();
        if (Get.getCurrentProcessName().equals(getPackageName())) {
            isDebug = Is.isDebuggable();
            application = this;
            applicationInfo = setInfo();
            TopBarInfo topBarInfo = setTopBarInfo();
            if (topBarInfo != null) {
                LeoSupport.initTopBar(topBarInfo.getmBgColor(), topBarInfo.getmBackIcon(), topBarInfo.getmTitleColor(), topBarInfo.getmMenuTextColor());
            } else {
                LeoSupport.initTopBar();
            }

            toastLayoutModel = setToastLayoutModel();
            init();
        }

    }

    private ToastLayoutModel setToastLayoutModel() {
        return new ToastLayoutModel(R.layout.include_toast, 0.58f);
    }


    public static ToastLayoutModel getToastLayoutModel() {
        return toastLayoutModel;
    }

    protected abstract ApplicationInfo setInfo();

    protected abstract TopBarInfo setTopBarInfo();

    protected abstract void init();


    public static BaseApplication getApplication() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    public static ApplicationInfo getInfo() {
        return applicationInfo;
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

    //当Activity
    public static void onRestoreBiz() {

    }
}
