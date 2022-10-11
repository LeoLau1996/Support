package leo.work.support.biz;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.display.DisplayManager;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.nio.ByteBuffer;

import leo.work.support.R;
import leo.work.support.support.file.FileSupport;
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

    public static void startService(Activity activity, String path, int width, int height, int resultCode, Intent data) {
        Intent intent = new Intent(activity, MediaProjectionService.class);
        intent.putExtra("path", path);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        intent.putExtra("resultCode", resultCode);
        intent.putExtra("data", data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(intent);
        }
    }

    // 录频实例
    private MediaProjectionManager manager;
    // 数据源
    private MediaProjection mediaProjection;
    // 编码器
    private MediaCodec mediaCodec;
    // 保存路径
    private String path;
    // 视频宽高
    private int width, height;

    private String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME";
    private String NOTIFICATION_CHANNEL_ID = "MediaProjection";
    private String NOTIFICATION_TICKER = "NOTIFICATION_TICKER";
    private String NOTIFICATION_CHANNEL_DESC = "NOTIFICATION_CHANNEL_DESC";
    private int NOTIFICATION_ID = 12345;
    private final String TAG = MediaProjectionService.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        NotifyUtils.createNotifyChannel(this, NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME);

        // 创建录频实例
        manager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notification();
        path = intent.getStringExtra("path");
        width = intent.getIntExtra("width", 0);
        height = intent.getIntExtra("height", 0);
        int resultCode = intent.getIntExtra("resultCode", 0);
        Intent data = intent.getParcelableExtra("data");
        record(resultCode, data);
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void record(int resultCode, Intent data) {
        if (manager == null) {
            return;
        }
        //
        mediaProjection = manager.getMediaProjection(resultCode, data);
        // 录制
        initRecord();
    }

    // 开始录频 生成一个H264
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initRecord() {
        initMediaCodec();
        if (mediaProjection == null || mediaCodec == null) {
            return;
        }


        // 名称保持唯一即可，不为空
        String name = "ttttt";
        // 越大越清晰
        int dpi = 2;
        // 公开的
        int flags = DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;
        // 创建一个虚拟的屏幕
        Surface surface = mediaCodec.createInputSurface();
        mediaProjection.createVirtualDisplay(name, width, height, dpi, flags, surface, null, null);

        startRecord();
    }

    // 初始化编解码器
    private void initMediaCodec() {
        // 初始化解码器
        try {
            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
        } catch (IOException e) {
            Log.e(TAG, String.format("record    IOException:%s", e.getMessage()));
        }
        if (mediaCodec == null) {
            return;
        }
        // 录制信息 这些信息都会保存到配置帧sps
        MediaFormat mediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height);
        // 录制信息 ---- 帧率（实际上帧率可以随便传，他只是会在PTS上按照帧率的时间间隔递增比如20帧也就是间隔(1000/20 = 50ms) PTS就会按照50ms递增）
        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 20);
        // 设置I帧间隔
        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 30);
        // 设置码率(一般就是宽*高) 你可以理解码率位压缩等级  码率越高质量越好
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, width * height);
        // 数据来源
        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        // 最后一个参数 标志位flag  默认0 代表 解码   CONFIGURE_FLAG_ENCODE代表编码
        mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
    }

    // 开始录制 异步线程
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startRecord() {
        new Thread(() -> {
            // xxx
            mediaCodec.start();

            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            // 这里输出即可 输入数据的过程谷歌已经帮我们实现了
            while (true) {
                // 获得输出下标
                int dequeueOutputBufferIndex = mediaCodec.dequeueOutputBuffer(info, 100 * 1000);
                if (dequeueOutputBufferIndex < 0) {
                    continue;
                }
                // 获得输出内容
                ByteBuffer byteBuffer = mediaCodec.getOutputBuffer(dequeueOutputBufferIndex);
                // 提取数据保存到data数组
                byte[] data = new byte[info.size];
                byteBuffer.get(data);
                // 把data写到本地文件
                FileSupport.writeBytes(path, true, data);
            }
        }).start();
    }
}
