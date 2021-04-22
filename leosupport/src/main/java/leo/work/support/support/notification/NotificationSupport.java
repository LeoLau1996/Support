package leo.work.support.support.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;


import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by 刘桂安 on 2018/7/22.
 * 可参考：https://blog.csdn.net/ss1168805219/article/details/52445371
 */

public class NotificationSupport {
    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private Notification notification;
    private RemoteViews mRemoteViews;
    private NotificationEdit notificationEdit;
    //数据
    private int logo;
    private int notification_ID;
    private String channel_ID = "my_channel_01";
    private String channel_Name = "彩虹播放器";


    //初始化
    public NotificationSupport(Context mContext, int mLogo, int layout, NotificationEdit edit) {
        this.context = mContext;
        this.logo = mLogo;
        notification_ID = (int) new Date().getTime();
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channel_ID, channel_Name, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        mBuilder = new NotificationCompat.Builder(context, channel_ID);
        mRemoteViews = new RemoteViews(context.getPackageName(), layout);
        notificationEdit = edit;
    }

    //显示
    public void showNotification() {
        notificationEdit.initNotificationView(mRemoteViews);
        mBuilder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示
        mBuilder.setContentIntent(getDefalutIntent(0));
        mBuilder.setSmallIcon(logo);
        mBuilder.setDefaults(NotificationCompat.COLOR_DEFAULT);
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);// 设置该通知优先级
        mBuilder.setOnlyAlertOnce(true);
        //mBuilder.setContent(mRemoteViews);
        mBuilder.setCustomBigContentView(mRemoteViews);
        notification = mBuilder.build();
        mNotificationManager.notify(notification_ID, notification);
    }


    //清除当前创建的通知栏
    public void clearNotify() {
        mNotificationManager.cancel(notification_ID);
    }

    //清楚所有的通知栏
    public void clearAllNotify() {
        mNotificationManager.cancelAll();
    }

    //获取默认的pendingIntent,为了防止2.3及以下版本报错 flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT 点击去除： Notification.FLAG_AUTO_CANCEL
    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }


}
