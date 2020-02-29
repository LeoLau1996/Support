package leo.work.support.Support.Common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import leo.work.support.Base.Service.BaseService;


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
public class ServiceSupper {
    private Activity activity;
    private Context context;
    public BaseService.MyBinder serviceBinder;
    public BaseService.CallBack callBack;
    public ServiceConnection connection;

    //初始化
    public ServiceSupper(Activity mActivity, Context context) {
        this.activity = mActivity;
        this.context = context;
        connection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                serviceBinder = (BaseService.MyBinder) iBinder;
                serviceBinder.setCallBack(activity.getClass().getName(), callBack);
            }

            //Service被迫关闭的时候调用，正常关闭不会调用
            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
    }

    //绑定服务
    public boolean bindService(Class mClass, BaseService.CallBack mCallBack) {
        try {
            callBack = mCallBack;
            activity.bindService(new Intent(context, mClass), connection, activity.BIND_AUTO_CREATE);//绑定服务
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //解除绑定
    public void unBind() {
        activity.unbindService(connection);
    }

    //向服务发送信息
    public boolean sendMsgToService(Object... ob) {
        if (serviceBinder != null) {
            serviceBinder.UsertoService(activity.getClass().getName(), callBack, ob);
            return true;
        } else
            return false;
    }
}
