package leo.work.support.biz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;
import leo.work.support.support.file.FileSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 录频业务
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/9
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * 需要权限：
 * <uses-permission android:name="android.permission.CAMERA" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * ---------------------------------------------------------------------------------------------
 **/
public class MediaProjectionBiz extends CommonLifeBiz {

    private static final String TAG = MediaProjectionBiz.class.getSimpleName();
    // 录频实例
    private MediaProjectionManager manager;
    // 数据源
    private MediaProjection mediaProjection;
    // 编码器
    private MediaCodec mediaCodec;
    // 保存路径
    private String path;
    // 视频宽高
    private int width = 720, height = 1280;
    private PermissionBiz permissionBiz;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MediaProjectionBiz(LifeControlInterface lifeControlInterface) {
        super(lifeControlInterface);
        // 创建录频实例
        manager = (MediaProjectionManager) getContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void start(String path, int width, int height) {
        if (manager == null) {
            return;
        }
        this.path = path;
        this.width = width;
        this.height = height;
        if (permissionBiz == null) {
            permissionBiz = new PermissionBiz(getLifeControlInterface());
        }
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permissionBiz.checkPermission(permissions, 10002, new PermissionBiz.OnPermissionBizCallBack() {
            @Override
            public void onPermissionSuccess(int requestCode, String[] permissions) {
                Intent intent = manager.createScreenCaptureIntent();
                getActivity().startActivityForResult(intent, 10001);
            }

            @Override
            public void onPermissionFail(int requestCode, String[] successPermissions, String[] failPermissions) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 10001: {
                //
                mediaProjection = manager.getMediaProjection(resultCode, data);
                // 录制
                initRecord();
                break;
            }
        }
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
