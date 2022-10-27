package com.leo.support.view.activity;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.leo.support.info.AppPath;

import java.io.IOException;
import java.nio.ByteBuffer;

import leo.work.support.support.file.FileSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/12
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * 官方文档：https://developer.android.com/guide/topics/media/camera
 * 官方API文档：https://developer.android.com/reference/android/hardware/Camera
 * ---------------------------------------------------------------------------------------------
 **/
public class LocalSurfaceView extends SurfaceView implements Camera.PreviewCallback {

    // 摄像头
    private Camera mCamera;
    private Camera.Size size;
    // 编解码器
    private MediaCodec mediaCodec;
    // 录制路径
    private String path;
    private MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
    // 录制状态
    private volatile boolean record;
    // 帧率
    private int frameRate = 15;
    // I帧间隔（秒）
    private int iFrameInterval = 2;
    // 数据缓存
    private byte[] nv12, yuv, outputBytes;
    // 当前帧
    private int index;
    // 日志
    private final String TAG = LocalSurfaceView.class.getSimpleName();

    public LocalSurfaceView(Context context) {
        super(context);
    }

    public LocalSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "LocalSurfaceView");
        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                Log.e(TAG, "surfaceCreated");
                startPreview();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });
    }

    public LocalSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 开始预览
     * 谷歌爸爸：https://developer.android.com/guide/topics/media/camera#capture-video
     */
    private void startPreview() {
        // * 打开相机 ---- 后置摄像头
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        // 获取相机参数
        Camera.Parameters parameters = mCamera.getParameters();
        size = parameters.getPreviewSize();
        Log.e(TAG, String.format("size    width = %s    height = %s", size.width, size.height));

        initMediaCodec();


        try {
            // * 连接预览
            mCamera.setPreviewDisplay(getHolder());
            // 设置预览角度
            mCamera.setDisplayOrientation(90);
            // 监听每一帧的数据
            mCamera.setPreviewCallbackWithBuffer(this);
            // * 开始预览
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, String.format("startPreview    IOException = %s", e.getMessage()));
        }
    }

    // 初始化编码器
    private void initMediaCodec() {
        // 创建编码器
        try {
            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            //
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, size.width, size.height);
            // 设置比特率
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, size.width * size.height);
            // 设置帧数
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
            // I帧间隔
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, iFrameInterval);
            // 解码输出NV12
            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible);
            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mediaCodec.start();
        } catch (IOException e) {
            Log.e(TAG, String.format("initMediaCodec    IOException = %s", e.getMessage()));
        }
    }

    /**
     * @param nv21Bytes NV21格式数据 只有Android支持NV21
     * @param camera
     */
    @Override
    public void onPreviewFrame(byte[] nv21Bytes, Camera camera) {
        Log.e(TAG, String.format("onPreviewFrame    data = %s", nv21Bytes.length));
        if (mediaCodec == null || !record) {
            return;
        }
        encode(nv21Bytes);
        //
        mCamera.addCallbackBuffer(nv21Bytes);
    }

    /**
     * 编码
     * NV21转NV12与画面旋转
     * https://blog.csdn.net/chailongger/article/details/84675574
     *
     * @param nv21Bytes
     */
    private void encode(byte[] nv21Bytes) {
        if (mediaCodec == null) {
            return;
        }

        // NV21 ----> NV12(YUV420)
        nv12 = nv21toNV12(nv21Bytes, nv12);
        // 旋转
        yuv = portraitData2Raw(nv12, yuv, size.width, size.height);
        // 输入
        int dequeueInputBufferIndex = mediaCodec.dequeueInputBuffer(100 * 1000);
        if (dequeueInputBufferIndex < 0) {
            return;
        }
        ByteBuffer inputByteBuffer = mediaCodec.getInputBuffer(dequeueInputBufferIndex);
        inputByteBuffer.clear();
        inputByteBuffer.put(yuv);
        int pts = 1000000 / frameRate * index;
        mediaCodec.queueInputBuffer(dequeueInputBufferIndex, 0, yuv.length, pts, 0);

        // 输出
        //int dequeueOutputBufferIndex = mediaCodec.dequeueOutputBuffer(info, 100 * 1000);
        //if (dequeueOutputBufferIndex >= 0) {
        for (int dequeueOutputBufferIndex = mediaCodec.dequeueOutputBuffer(info, 100 * 1000); dequeueOutputBufferIndex >= 0; dequeueOutputBufferIndex = mediaCodec.dequeueOutputBuffer(info, 100 * 1000)) {
            ByteBuffer outputByteBuffer = mediaCodec.getOutputBuffer(dequeueOutputBufferIndex);
            if (outputBytes == null || outputBytes.length != info.size) {
                outputBytes = new byte[info.size];
            }
            outputByteBuffer.get(outputBytes);
            // 保存数据到本地
            FileSupport.writeBytes(path, true, outputBytes);
            mediaCodec.releaseOutputBuffer(dequeueOutputBufferIndex, false);
        }
    }

    // 开始录制
    public void startRecording() {
        if (record) {
            return;
        }
        record = true;
        path = String.format("%scamera_%s.h264", AppPath.getAppCache(), System.currentTimeMillis());
        index = 0;
        // xxx
        mCamera.addCallbackBuffer(new byte[size.width * size.height * 3 / 2]);
    }

    public String stopRecord() {
        record = false;
        return path;
    }

    // NV21转NV12
    public static byte[] nv21toNV12(byte[] nv21, byte[] nv12) {
        if (nv12 == null || nv12.length != nv21.length) {
            nv12 = new byte[nv21.length];
        }
        int len = nv21.length * 2 / 3;
        System.arraycopy(nv21, 0, nv12, 0, len);
        int i = len;
        while (i < nv21.length - 1) {
            nv12[i] = nv21[i + 1];
            nv12[i + 1] = nv21[i];
            i += 2;
        }
        return nv12;
    }

    //暂时理解到这里
    public static byte[] portraitData2Raw(byte[] data, byte[] output, int width, int height) {
        int bufferLength = width * height * 3 / 2;
        if (output == null || output.length != bufferLength) {
            output = new byte[bufferLength];
        }
        int y_len = width * height;
        int uvHeight = height >> 1; // uv数据高为y数据高的一半
        int k = 0;
        for (int j = 0; j < width; j++) {
            for (int i = height - 1; i >= 0; i--) {
                output[k++] = data[width * i + j];
            }
        }
        for (int j = 0; j < width; j += 2) {
            for (int i = uvHeight - 1; i >= 0; i--) {
                output[k++] = data[y_len + width * i + j];
                output[k++] = data[y_len + width * i + j + 1];
            }
        }
        return output;
    }


}
