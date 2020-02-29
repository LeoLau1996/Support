package leo.work.support.Base.Util;

import android.app.Application;
import android.content.Context;

import leo.work.support.Base.Application.BaseApplication;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-06-14
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class BaseUtil {

    private static volatile Context mContext;
    protected static final Object mLockObject = new Object();

    public static Context getContext() {
        if (null == mContext) {
            synchronized (mLockObject) {
                Application app = BaseApplication.getApplication();
                if (app == null) {
                    app = getApplication();
                }
                if (app != null) {
                    mContext = app.getApplicationContext();
                }
            }
        }
        return mContext;
    }

    protected static Application getApplication() {
        Application app = null;
        try {
            app = (Application) Class
                .forName("android.app.ActivityThread")
                .getMethod("currentApplication")
                .invoke(null, (Object[]) null);
        } catch (Exception e) {
        } finally {
            if (app == null) {
                try {
                    app = (Application) Class
                        .forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication")
                        .invoke(null, (Object[]) null);
                } catch (Exception e) {
                }
            }
        }
        return app;
    }

}
