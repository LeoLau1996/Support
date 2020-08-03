package leo.work.support.Base.Service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import leo.work.support.Support.Common.LogUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/4/28.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
@Deprecated
public abstract class BaseService extends Service {
    public Context context;
    private MyBinder mBinder = new MyBinder();
    //返回信息给工具类
    public CallBack callBack = null;

    public String usedName;
    public boolean isLoading = false;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e("=======================>" + this.getClass().getName());
        context = this;
        initData();
        initBiz();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //解绑
    @Override
    public boolean onUnbind(Intent intent) {
        unBind();
        return super.onUnbind(intent);
    }


    //关闭服务
    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceDie();
    }


    protected abstract void initData();

    protected abstract void initBiz();

    protected abstract void SendToService(Object... obj);

    protected abstract void unBind();

    protected abstract void serviceDie();

    //接收使用者的信息 －－－ 1
    public class MyBinder extends Binder {
        /**
         * 使用者通过该接口传递信息给Service
         *
         * @param mUsedName 使用者名字
         * @param mCallBack 使用者回传接口
         * @param obj       信息
         */
        public void UsertoService(String mUsedName, CallBack mCallBack, Object... obj) {
            if (!usedName.equals(mUsedName)) {
                callBack = mCallBack;
                usedName = mUsedName;
            }
            SendToService(obj);
        }

        /**
         * 设置回传通道
         *
         * @param mUsedName 使用者名字
         * @param mCallBack 使用者回传接口
         */
        public void setCallBack(String mUsedName, CallBack mCallBack) {
            callBack = mCallBack;
            usedName = mUsedName;
        }
    }

    //返回信息给使用者（先给ServiceUtil  ServiceUtil再给使用者）
    public interface CallBack {
        public void ServiceToUser(Object... obj);
    }


    /**
     * 工具
     */

    /**
     * 1.判断某个服务是否正在运行的方法
     *
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
