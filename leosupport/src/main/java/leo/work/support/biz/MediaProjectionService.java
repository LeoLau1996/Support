package leo.work.support.biz;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import leo.work.support.R;
import leo.work.support.util.NotifyUtils;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 屏幕录制前台服务
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/11
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注: <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
 * ---------------------------------------------------------------------------------------------
 **/
public class MediaProjectionService extends Service {

    private String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME";
    private String NOTIFICATION_CHANNEL_ID = "MediaProjection";
    private String NOTIFICATION_TICKER = "NOTIFICATION_TICKER";
    private String NOTIFICATION_CHANNEL_DESC = "NOTIFICATION_CHANNEL_DESC";
    private int NOTIFICATION_ID = 12345;
    private final String TAG = MediaProjectionService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        NotifyUtils.createNotifyChannel(this, NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void notification() {
        Log.i(TAG, "notification: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Call Start foreground with notification
            Intent notificationIntent = new Intent(this, MediaProjectionService.class);
            PendingIntent pendingIntent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(this, 123, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(this, 123, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
            }


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_back))
                    .setSmallIcon(R.drawable.icon_back)
                    .setContentTitle("我是标题")
                    .setContentText("我是内容")
                    .setTicker(NOTIFICATION_TICKER)
                    .setContentIntent(pendingIntent);
            Notification notification = notificationBuilder.build();
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(NOTIFICATION_CHANNEL_DESC);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            //notificationManager.notify(NOTIFICATION_ID, notification);
            startForeground(NOTIFICATION_ID, notification); //必须使用此方法显示通知，不能使用notificationManager.notify，否则还是会报上面的错误
        }
    }

}
