package leo.work.support.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 通知栏工具
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/11
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class NotifyUtils {

    private static final String TAG = NotifyUtils.class.getSimpleName();

    //创建通道
    public static void createNotifyChannel(Context context, String channelId, String channelName) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            Log.e(TAG, String.format("不需要创建通道    SDK_INT = %s", android.os.Build.VERSION.SDK_INT));
            return;
        }
        //从系统管理器中获取通知管理器
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }
        Log.e(TAG, String.format("创建通道    channelId = %s    channelName = %s    SDK_INT = %s", channelId, channelName, android.os.Build.VERSION.SDK_INT));
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // 设置桌面角标
        channel.enableLights(true);
        // 长按妆面图标时显示该渠道明细
        channel.setShowBadge(true);
        // 创建渠道
        notificationManager.createNotificationChannel(channel);
    }

}
