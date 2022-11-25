package com.surgery.scalpel.biz;

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
import android.hardware.display.VirtualDisplay;
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

import com.surgery.scalpel.R;
import com.surgery.scalpel.support.file.FileSupport;
import com.surgery.scalpel.util.Is;
import com.surgery.scalpel.util.NotifyUtils;
import com.surgery.scalpel.util.SocketUtils;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 屏幕录制前台服务
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/11
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注: <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
 * 帧类型：https://blog.csdn.net/zhaoyun_zzz/article/details/87302600
 * MediaProjection：https://developer.android.google.cn/reference/android/media/projection/MediaProjection
 * ---------------------------------------------------------------------------------------------
 **/
public class MediaProjectionService extends Service {

    public static void startService(Activity activity, int socketType, String path, int width, int height, int dpi, int resultCode, Intent data) {
        Intent intent = new Intent(activity, MediaProjectionService.class);
        intent.setAction(ACTION_START);
        intent.putExtra("socketType", socketType);
        intent.putExtra("path", path);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        intent.putExtra("dpi", dpi);
        intent.putExtra("resultCode", resultCode);
        intent.putExtra("data", data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(intent);
        } else {
            activity.startService(intent);
        }
    }

    public static void stop(Activity activity) {
        Intent intent = new Intent(activity, MediaProjectionService.class);
        intent.setAction(ACTION_END);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(intent);
        } else {
            activity.startService(intent);
        }
    }

    // 媒体类型 ---- 视频
    public static byte TYPE_VIDEO = 0;
    // 媒体类型 ---- 音频
    public static byte TYPE_AUDIO = 1;
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
    private int dpi;
    // 配置帧缓存
    private byte[] configFrameCase;
    // xxx
    private VirtualDisplay virtualDisplay;

    // 开始录频
    private static final String ACTION_START = "ACTION_START";
    // 结束录频
    private static final String ACTION_END = "ACTION_END";
    private String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME";
    private String NOTIFICATION_CHANNEL_ID = "MediaProjection";
    private String NOTIFICATION_TICKER = "NOTIFICATION_TICKER";
    private String NOTIFICATION_CHANNEL_DESC = "NOTIFICATION_CHANNEL_DESC";
    private int NOTIFICATION_ID = 12345;
    private final String TAG = MediaProjectionService.class.getSimpleName();
    // 录制状态
    private volatile boolean recording;
    // 类型
    private int socketType;
    // 服务端
    public static int SOCKE_TYPE_SERVICE = 0;
    // 客户端
    public static int SOCKE_TYPE_CLIENT = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        NotifyUtils.createNotifyChannel(this, NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            // 开始录频
            case ACTION_START: {
                recording = true;
                notification();
                socketType = intent.getIntExtra("socketType", SOCKE_TYPE_SERVICE);
                path = intent.getStringExtra("path");
                width = intent.getIntExtra("width", 0);
                height = intent.getIntExtra("height", 0);
                dpi = intent.getIntExtra("dpi", 120);
                int resultCode = intent.getIntExtra("resultCode", 0);
                Intent data = intent.getParcelableExtra("data");
                record(resultCode, data);
                break;
            }
            // 结束录频
            case ACTION_END: {
                recording = false;
                break;
            }
        }

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
        // 创建录频实例
        if (manager == null) {
            manager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        }
        //
        mediaProjection = manager.getMediaProjection(resultCode, data);
        // 初始化
        initMediaCodec();
        // 录制
        inPut();
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
        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
        // 设置码率(一般就是宽*高) 你可以理解码率位压缩等级  码率越高质量越好
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, width * height);
        // 数据来源
        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        // 最后一个参数 标志位flag  默认0 代表 解码   CONFIGURE_FLAG_ENCODE代表编码
        mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
    }

    // 输入内容
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void inPut() {
        if (mediaProjection == null || mediaCodec == null) {
            return;
        }
        // 名称保持唯一即可，不为空
        String name = String.format("record_%s", System.currentTimeMillis());
        // 公开的
        int flags = DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;
        // 请求 Surface 用作编码器的输入，以代替输入缓冲区。
        Surface surface = mediaCodec.createInputSurface();
        // 创建场地
        virtualDisplay = mediaProjection.createVirtualDisplay(name, width, height, dpi, flags, surface, null, null);
        outPut();
    }

    // 输出内容
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void outPut() {
        new Thread(() -> {
            // xxx
            mediaCodec.start();

            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            // 这里输出即可 输入数据的过程谷歌已经帮我们实现了
            while (recording) {
                // 获得输出下标
                int dequeueOutputBufferIndex = mediaCodec.dequeueOutputBuffer(info, 10 * 1000);
                if (dequeueOutputBufferIndex < 0) {

                    continue;
                }
                // 获得输出内容
                ByteBuffer byteBuffer = mediaCodec.getOutputBuffer(dequeueOutputBufferIndex);
                // 提取数据保存到data数组
                byte[] data = new byte[info.size];
                byteBuffer.get(data);
                Log.e(TAG, String.format("写入数据    dequeueOutputBufferIndex = %s    写入长度 = %s", dequeueOutputBufferIndex, data.length));
                handlerData(data);
                // xxxx
                mediaCodec.releaseOutputBuffer(dequeueOutputBufferIndex, false);
            }

            virtualDisplay.release();

            mediaProjection.stop();

            mediaCodec.stop();
            mediaCodec.release();

            NotifyUtils.cancelNotification(this, NOTIFICATION_ID);


        }).start();
    }

    private static final int NAL_I = 5;
    private static final int NAL_SPS = 7;

    public void handlerData(byte[] data) {
        // 把data写到本地文件
        if (!Is.isEmpty(path)) {
            FileSupport.writeBytes(path, true, data);
            return;
        }
        int index = 4;
        // 适配：有些分隔符是00 00 01
        if (data[2] == 0x01) {
            index = 3;
        }

        int type = (data[index] & 0x1f); // 0x1f 也就是0001 1111 通过与运算就可以得到类型
        Log.e("liu1017", String.format("type = %s", type));
        switch (type) {
            // 配置帧
            case NAL_SPS: {
                // 缓存配置帧
                configFrameCase = data;
                break;
            }
            // I帧
            case NAL_I: {
                byte[] newData = new byte[configFrameCase.length + data.length + 1];
                Log.e("liu1017", String.format("configFrameCase = %s    data = %s    newData = %s", configFrameCase.length, data.length, newData.length));
                newData[0] = TYPE_VIDEO;
                System.arraycopy(configFrameCase, 0, newData, 1, configFrameCase.length);
                System.arraycopy(data, 0, newData, (1 + configFrameCase.length), data.length);
                if (socketType == SOCKE_TYPE_SERVICE) {
                    SocketUtils.getInstance().serverSend(newData);
                } else {
                    SocketUtils.getInstance().clientSend(newData);
                }
                break;
            }
            //
            default: {
                byte[] newData = new byte[data.length + 1];
                newData[0] = TYPE_VIDEO;
                System.arraycopy(data, 0, newData, 1, data.length);
                if (socketType == SOCKE_TYPE_SERVICE) {
                    SocketUtils.getInstance().serverSend(newData);
                } else {
                    SocketUtils.getInstance().clientSend(newData);
                }
                break;
            }
        }

    }


}
